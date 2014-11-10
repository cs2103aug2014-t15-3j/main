import java.awt.Color;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogicMain {

	// Constant variables
	private final static int ONE_DAY = 1;
	private final static long DAY_MS = 86400000;
	private final static String LOG_NAME = "LogicLog";
	private final static String LOG_NAME_STORAGE_SAVE = "StorageSaveLog";

	// Flag to check if the program has initialized.
	private static boolean isInitialize = false;
	
	// Flag to check if there's any chances (For auto-save)
	private static boolean hasChanged = false;


	// Operation object
	private Operations operations;

	// Data structures
	static LinkedList<Task> bufferTasksList = new LinkedList<Task>();
	private static LinkedList<Label> bufferLabelsList = new LinkedList<Label>();
	private static LinkedList<Task> undoTasks = new LinkedList<Task>();
	private static LinkedList<ReminderTask> undoReminders = new LinkedList<ReminderTask>();
	private static LinkedList<Item> tempList = new LinkedList<Item>();
	private LinkedList<LogicInputPair> inputList;
	private String[] inputArray;

	// Logger: Use to troubleshoot problems
	private Logger logger = Logger.getLogger(LOG_NAME);
	private Logger storageLogger = Logger.getLogger(LOG_NAME_STORAGE_SAVE);
	
	// Flag to check if the current operation is a logic operation
	private boolean isLabel;

	public LogicMain() {
		initialize();
	}

	//@author A0111942N
	/**
	 * Checks if the component has been initialized. It will initialize the
	 * component if it is not.
	 */
	private void initialize() {

		if (!isInitialize) {
			operations = new Operations();

			retrieveFromStorage();

			updateUndo();
			
			initializeSaveTimer();
			intializeReminder();
			scheduleReminderSystem();


			isInitialize = true;

			logger.log(Level.INFO, "Initializing is complete");
		} else {
			
			logger.log(Level.INFO, "LogicMain has already been initiated");
		}
	}
	
	//@author A0111942N
	/**
	 * This method updates the list required for the undo.
	 */
	private void updateUndo() {
		undoTasks = new LinkedList<Task>(bufferTasksList);
		undoReminders = LogicReminder.getList();
	}

	//@author A0111942N
	/**
	 * Retrieve tasks from Storage
	 */
	private void retrieveFromStorage() {

		Object retrievedTasks = StorageMain.getInstance().
				retrieveObject(StorageMain.OBJ_TYPES.TYPE_TASK);

		if (retrievedTasks instanceof LinkedList<?>) {
			bufferTasksList = (LinkedList<Task>) retrievedTasks;
		} else {
			bufferTasksList = new LinkedList<Task>();
			logger.log(Level.WARNING, "Unable to retrieve tasks from storage");
		}

		Object retrievedLabels = StorageMain.getInstance().
				retrieveObject(StorageMain.OBJ_TYPES.TYPE_LABEL);

		if (retrievedLabels instanceof LinkedList<?>) {
			bufferLabelsList = (LinkedList<Label>) retrievedLabels;
		} else {
			bufferLabelsList = new LinkedList<Label>();
			logger.log(Level.WARNING, "Unable to retrieve labels from storage");
		}
	}
	
	//@author A0112898U
	/**
	 * This method initialize the timer for auto saving.
	 */
	private void initializeSaveTimer() {
		
		//Start Timer thread for saving file every 5 min
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(storageSaveScheduler, 0, 5, TimeUnit.MINUTES);
	}
	
	//@author A0112898U
	/**
	 * This method initialize the reminder system.
	 */
	private void intializeReminder() {
		LogicReminder.initiateSingleton(bufferTasksList);
	}
	
	//@author A0112898U
	/**
	 * This method schedules the reminderSystem to refresh daily at 0000.
	 */
	private void scheduleReminderSystem() {
	
		Calendar todayDate = Calendar.getInstance();
		todayDate.getTime();
		
		Calendar nextDayDate = Calendar.getInstance();
		nextDayDate.setTime(todayDate.getTime());
		nextDayDate.add(Calendar.DATE, ONE_DAY);
		nextDayDate.set(Calendar.HOUR_OF_DAY, 00);
		nextDayDate.set(Calendar.MINUTE, 00);
		nextDayDate.set(Calendar.SECOND, 00);
		
		long initialDelay = nextDayDate.getTimeInMillis() - todayDate.getTimeInMillis();
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(refreshReminderScheduler, initialDelay, 
				86400000, TimeUnit.MILLISECONDS);
	}
	
	
	// @author A0112898U
	/**
	 * Periodic Reminder System refresh - refresh the reminder list
	 * every day at 0000
	 */
	Runnable refreshReminderScheduler = new Runnable() {
		   
		Calendar cal = Calendar.getInstance();
		
	    public void run() {
	    	if (isInitialize) {
	    		//Refresh the reminder system
	    		LogicReminder.getInstance().regenReminderList(bufferTasksList);
	    		
	    		//Do Logging
	    		storageLogger.log(Level.INFO, "Reminder System list refreshed" + cal.getTime());
	    	}
	    }
	};
	
	// @author A0112898U
	/**
	 * Periodic Task Saving Functionality - saves the buffered input task
	 * to storage every 5min
	 */
	Runnable storageSaveScheduler = new Runnable() {
		   
		Calendar cal = Calendar.getInstance();
		
	    public void run() {
	    	
	    	if(isInitialize) {

	    		//Save the current buffered list
	    		commitToStorage();

	    		//Do Logging
	    		storageLogger.log(Level.INFO, "BufferList Stored @ " + cal.getTime());
	    	}
	    }
	    
	};

	//@author A0111942N
	/**
	 * This method will process the user's input and perform either add, edit,
	 * view, delete or save based on the input.
	 * 
	 * @return LinkedList<Item> with corresponding items to operation
	 */
	public LinkedList<Item> processInput(String input) {
		
		LinkedList<Item> returnTasks = new LinkedList<Item>();

		input = cleanUpInput(input);

		preProcessInput(input);

		String mainOperation = inputList.get(0).getOperation();

		// To determine whether it is a label operation
		isLabel = false;

		if (Operations.labelOperations.contains(mainOperation)
				&& inputList.size() > 1) {
			
			mainOperation = inputList.get(1).getOperation();
			inputList.remove();
			isLabel = true;

		}

		if (Operations.addOperations.contains(mainOperation)) {

			updateUndo();
			returnTasks = processAdd();
			
			hasChanged = true;

		} else if (Operations.editOperations.contains(mainOperation)) {

			updateUndo();
			returnTasks = processEdit();
			
			hasChanged = true;

		} else if (Operations.viewOperations.contains(mainOperation)) {

			returnTasks = processView();
			System.out.println(bufferTasksList);

		} else if (Operations.findOperations.contains(mainOperation)) {

			returnTasks = processFind();

		} else if (Operations.undoOperations.contains(mainOperation)) {

			returnTasks = processUndo();
			
			hasChanged = true;

		} else if (Operations.deleteOperations.contains(mainOperation)) {

			updateUndo();
			returnTasks = processDelete();
			
			hasChanged = true;

		} else if (Operations.saveOperations.contains(mainOperation)) {

			updateUndo();
			returnTasks = processSave();

		} else if (!isLabel && Operations.doneOperations.contains(mainOperation)) {

			updateUndo();
			returnTasks = processDone(true);
			hasChanged = true;

		} else if (!isLabel && Operations.notDoneOperations.contains(mainOperation)) {

			updateUndo();
			returnTasks = processDone(false);
			hasChanged = true;

		} else {

			returnTasks = new LinkedList<Item>();

		}

		return returnTasks;
	}

	//@author A0111942N
	/**
	 * This method cleans up the input string by removing empty spaces and
	 * breaks at the front and back of it.
	 */
	private static String cleanUpInput(String input) {
		return input.trim();
	}

	//@author A0111942N
	/**
	 * Pre-process the user's input and categories them into a list.
	 */
	private void preProcessInput(String input) {

		inputList = new LinkedList<LogicInputPair>();

		String operation = "";
		String content = "";

		inputArray = input.split(" ");

		// This will loop through every word
		for (int i = 0; i < inputArray.length; i++) {

			// Check if the word is an operation
			if (isOperation(inputArray[i])) {

				// Adds the operation and its content from the previous loop
				if (!operation.isEmpty()) {

					LogicInputPair previousOperation = new LogicInputPair(
							operation, cleanUpInput(content));

					inputList.add(previousOperation);
				}

				operation = inputArray[i].toLowerCase();
				content = "";

				// Adds an one word operation
				if (i == inputArray.length - 1) {

					inputList.add(new LogicInputPair(operation, ""));
				}

			} else {

				if (!content.isEmpty()) {
					content += " ";
				}
				content += inputArray[i];

				if (i == inputArray.length - 1) {
					inputList.add(new LogicInputPair(operation,
							cleanUpInput(content)));
				}
			}
		}
	}

	/**
	 * ========================================================================
	 * =============== PROCESSING ADD =========================================
	 * ========================================================================
	 */

	//@author A0111942N
	/**
	 * Process post-adding operation to return to GUI
	 * 
	 * @return List containing the added task with the "add" state
	 */
	private LinkedList<Item> processAdd() {

		LinkedList<Item> returningItem = new LinkedList<Item>();

		Item addTask = executeAdd();
		
		Item returnItem;

		if (addTask != null) {

			if (!isLabel) {
				returnItem = new Task((Task) addTask);
				returnItem.editState(Operations.ADD_OPERATION);
			} else {
				returnItem = new Label((Label) addTask);
				returnItem.editState(Operations.ADD_LABEL_OPERATION);
			}

			
		} else {
			
			if (!isLabel) {
				returnItem = new Task(Operations.EMPTY_MESSAGE);
				returnItem.editState(Operations.ADD_ERROR);
			} else {
				returnItem = new Label(Operations.EMPTY_MESSAGE);
				returnItem.editState(Operations.ADD_LABEL_ERROR);
			}
		}
		
		returningItem.add(returnItem);

		logger.log(Level.INFO, "Add operation completed");

		return returningItem;
	}

	//@author A0111942N
	/**
	 * This method executes the "add" functionality of the program.
	 * 
	 * Return newly added task. If the task's name is not provided, it will
	 * return null.
	 * 
	 * @return Newly added task
	 */
	private Item executeAdd() {

		// Method variables
		String name = "";
		String description = "";
		long deadline = -1;
		long reminder = -1;
		String color = "";
		long labelID = -1;
		
		long start = -1;
		long end = -1;

		for (int i = 0; i < inputList.size(); i++) {

			String operation = inputList.get(i).getOperation();

			if (Operations.addOperations.contains(operation)) {

				name = inputList.get(i).getContent();

				if (name.isEmpty()) {

					logger.log(Level.INFO, "Add operation: Name unidentified");
					return null;
				}

			} else if (Operations.descriptionOperations.contains(operation)) {

				description = inputList.get(i).getContent();

			} else if (Operations.startOperations.contains(operation)) {

				String dateInput = inputList.get(i).getContent();
				start = processDate(dateInput);

			} else if (Operations.endOperations.contains(operation)) {

				String dateInput = inputList.get(i).getContent();
				end = processDate(dateInput);

			} else if (Operations.deadlineOperations.contains(operation)) {
				
					String dateInput = inputList.get(i).getContent();
					deadline = processDate(dateInput);

			} else if (Operations.reminderOperations.contains(operation)) {
				
					String dateInput = inputList.get(i).getContent();
					reminder = processDate(dateInput);

			} else if (Operations.colorOperations.contains(operation)) {

				color = inputList.get(i).getContent();

			} else if (Operations.labelOperations.contains(operation) && i > 0) {

				String label = inputList.get(i).getContent();

				labelID = getLabelId(label);

			}

		}

		Item newItem;

		if (!isLabel) {

			Task newTask = new Task(name, description);
			newTask.editState(Operations.ADD_OPERATION);
			bufferTasksList.add(newTask);
			
			
			// Include start and end time
			newTask.editStart(start);
			newTask.editEnd(end);

			// Include deadline to new task
			newTask.editDeadline(deadline);

			// Include label to new task
			if (labelID != -1) {
				newTask.editLabel(labelID);
			}
			
			// Include reminder to new task
			if (reminder != -1) {
				newTask.editReminder(reminder);
				LogicReminder.getInstance().addTaskTobeReminded(newTask);
			}

			newItem = newTask;

			logger.log(Level.INFO, "New task added to bufferTasksList");

		} else {

			Label newLabel = new Label(name);
			newLabel.editState(Operations.EDIT_LABEL_OPERATION);
			bufferLabelsList.add(newLabel);

			if (!color.isEmpty()) {
				newLabel.editColor(color);
			}

			newItem = newLabel;

			logger.log(Level.INFO, "New label added to bufferTasksList");

		}

		return newItem;
	}

	/**
	 * ========================================================================
	 * =============== PROCESSING EDIT ========================================
	 * ========================================================================
	 */

	//@author A0111942N
	/**
	 * Process post-editing operation to return to GUI
	 * 
	 * @return List containing the edited task with the "edit" state
	 */
	private LinkedList<Item> processEdit() {

		LinkedList<Item> returningTasks = new LinkedList<Item>();

		Item returnTask = executeEdit();

		if (returnTask == null) {

			returnTask = new Task(Operations.EMPTY_MESSAGE);
			returnTask.editState(Operations.EDIT_ERROR);
			return returningTasks;

		}

		if (!isLabel) {
			
			returnTask.editState(Operations.EDIT_OPERATION);
		} else {
			
			returnTask.editState(Operations.EDIT_LABEL_OPERATION);
		}

		returningTasks.add(returnTask);

		logger.log(Level.INFO, "Edit operation completed");

		return returningTasks;
	}

	//@author A0111942N
	/**
	 * This method executes the "edit" functionality of the program.
	 * 
	 * Return edited task. If the task's name or id is not provided, it will
	 * return null.
	 * 
	 * @return Edited task
	 */
	private Item executeEdit() {

		int editID = -1;
		String name = "";
		String description = "";
		String color = "";
		long labelId = -1;
		long start = -1;
		long end = -1;
		long deadline = -1;
		long reminder = -1;
		boolean nameEdited = false;
		boolean descriptionEdited = false;
		boolean startEdited = false;
		boolean endEdited = false;
		boolean deadlineEdited = false;
		boolean reminderEdited = false;
		boolean colorEdited = false;
		
		System.out.println(inputList);

		for (int i = 0; i < inputList.size(); i++) {

			String operation = inputList.get(i).getOperation();
			
			logger.log(Level.INFO, ">>"+operation);

			if (Operations.editOperations.contains(operation)) {

				String stringID = inputList.get(i).getContent();

				try {

					editID = Integer.parseInt(stringID) - 1;

				} catch (Exception e) {

					logger.log(Level.INFO, "Invalid ID");
					return null;

				}

				if (editID < 0
						|| (!isLabel && editID >= bufferTasksList.size())
						|| (isLabel && editID >= bufferLabelsList.size()) ) {

					return null;

				}

			} else if (Operations.nameOperations.contains(operation)) {

				name = inputList.get(i).getContent();

				if (name.isEmpty()) {

					logger.log(Level.INFO, "Edit operation: Invalid name");
					return null;
				}

				nameEdited = true;

			} else if (Operations.descriptionOperations.contains(operation)) {

				description = inputList.get(i).getContent();
				descriptionEdited = true;

			} else if (Operations.startOperations.contains(operation)) {

				String dateInput = inputList.get(i).getContent();
				start = processDate(dateInput);
				
				startEdited = true;

			} else if (Operations.endOperations.contains(operation)) {

				String dateInput = inputList.get(i).getContent();
				end = processDate(dateInput);
				
				endEdited = true;

			} else if (Operations.deadlineOperations.contains(operation)) {

				String dateInput = inputList.get(i).getContent();
				deadline = processDate(dateInput);

				deadlineEdited = true;

			} else if (Operations.reminderOperations.contains(operation)) {

				String dateInput = inputList.get(i).getContent();

				if (dateInput.equals(Operations.REMOVE_INPUT)) {
					reminder = -1;
				} else {
					reminder = processDate(dateInput);
				}

				reminderEdited = true;

			} else if (Operations.colorOperations.contains(operation)) {

				color = inputList.get(i).getContent();

				if (color.isEmpty()) {

					logger.log(Level.INFO, "Color operation: Invalid color");
					return null;

				}

				colorEdited = true;

			} else if (Operations.labelOperations.contains(operation)) {

				String label = inputList.get(i).getContent();
				labelId = getLabelId(label);

				if (labelId == -1) {

					logger.log(Level.INFO,
							"Edit label operation: Invalid label");
					return null;
				}

				colorEdited = true;
			}

		}
		
		Item editItem;

		if (!isLabel) {

			// PROCESSING FOR TASK

			Task editTask;
			if (!tempList.isEmpty()) {

				editTask = (Task) tempList.get(editID);
				tempList = new LinkedList<Item>();

			} else {

				editTask = bufferTasksList.get(editID);

			}

			bufferTasksList.remove(editTask);

			Task newTask = new Task(editTask);

			// Check if name has been edited
			if (nameEdited) {

				newTask.editName(name);

			}

			// Check if description has been edited
			if (descriptionEdited) {

				newTask.editDescription(description);

			}
			
			// Check if deadline has been edited
			if (startEdited) {

				newTask.editStart(start);

			}

			// Check if deadline has been edited
			if (endEdited) {

				newTask.editEnd(end);

			}

			// Check if deadline has been edited
			if (deadlineEdited) {

				newTask.editDeadline(deadline);

			}

			// Check if label has been edited
			if (labelId != -1) {

				newTask.editLabel(labelId);

			}
			
			// Check if reminder has been edited
			if (reminderEdited) {

				newTask.editReminder(reminder);

				if (editTask.gethasReminder()
						&& reminder > System.currentTimeMillis()) {

					// Edit task in reminder
					LogicReminder.getInstance().updateTaskTobeReminded(newTask, editTask);

				} else if (!editTask.gethasReminder()
						&& reminder > System.currentTimeMillis()) {
					
					// Add task to be reminded
					LogicReminder.getInstance().addTaskTobeReminded(newTask);

				} else {

					// Remove task from reminder
					LogicReminder.getInstance().stopTask(editTask);

				}

			}

			bufferTasksList.add(newTask);
			editItem = newTask;

		} else {

			// PROCESSING FOR LABEL

			Label editLabel = bufferLabelsList.get(editID);
			
			System.out.println("Hello>>"+editLabel);

			if (nameEdited) {

				editLabel.editName(name);

			}
			if (colorEdited) {

				editLabel.editColor(color);

			}

			editLabel.editState(Operations.EDIT_OPERATION);
			editItem = editLabel;
		}

		logger.log(Level.INFO, "Task edited");
		return editItem;
	}

	/**
	 * ========================================================================
	 * =============== PROCESSING VIEW ========================================
	 * ========================================================================
	 */

	//@author A0111942N
	/**
	 * Process post-view operation to return to GUI
	 * 
	 * @return List containing all the tasks (First task will have the "view"
	 *         state)
	 */
	private LinkedList<Item> processView() {

		LinkedList<Item> returningItems = new LinkedList<Item>();
		Task returnTask;
		String state = Operations.VIEW_OPERATION;

		LogicInputPair viewOperation = inputList.get(0);

		if (!isLabel) {
			
			if (inputList.size() == 1 && bufferTasksList.size() != 0
					&& !isNumeric(viewOperation.getContent())) {

				returningItems = new LinkedList<Item>( getNotDoneTasks() );
				tempList = new LinkedList<Item>( getNotDoneTasks() );
				
				state = Operations.VIEW_OPERATION;

			} else if (inputList.size() == 1 && bufferTasksList.size() != 0) {
				
				try {
					
					int taskId = Integer.parseInt(viewOperation
							.getContent()) - 1;
					
					Task tempTask;
					
					if(tempList.size() != 0 && tempList.size() > taskId) {
						tempTask = (Task) tempList.get(taskId);
						returningItems.add(tempTask);
					} else if(bufferTasksList.size() > taskId) {
						tempTask = bufferTasksList.get(taskId);
						returningItems.add(tempTask);
					}
					
					state = Operations.VIEW_TASK_OPERATION;

				} catch (NumberFormatException e) {

					logger.log(Level.WARNING, "Invalid number");

				}

			} else if (inputList.size() == 2 && bufferTasksList.size() != 0) {
				
				LogicInputPair inputPair = inputList.get(1);
				
				if (Operations.doneOperations.contains(inputPair.getOperation())) {
					
					returningItems = new LinkedList<Item>( getDoneTasks() );
					tempList = new LinkedList<Item>( getDoneTasks() );
					
					state = Operations.VIEW_DONE_OPERATION;
					
				} else if (Operations.notDoneOperations.contains(inputPair.getOperation())) {

					returningItems = new LinkedList<Item>( getNotDoneTasks() );
					tempList = new LinkedList<Item>( getNotDoneTasks() );
					
					state = Operations.VIEW_OPERATION;

				} else if (Operations.allOperations.contains(inputPair.getOperation())) {

					returningItems = new LinkedList<Item>( getAllTasks() );
					tempList = new LinkedList<Item>( getAllTasks() );
					
					state = Operations.VIEW_ALL_OPERATION;
					
				} else if (Operations.floatOperations.contains(inputPair.getOperation())) {

					returningItems = new LinkedList<Item>( getFloatingTasks() );
					tempList = new LinkedList<Item>( getFloatingTasks() );
					
					state = Operations.VIEW_FLOAT_OPERATION;
				}

			}

			if (returningItems.isEmpty()) {

				returnTask = new Task(Operations.EMPTY_MESSAGE);
				returnTask.editState(Operations.VIEW_ERROR);
				returningItems.add(returnTask);

				return returningItems;

			}

			returnTask = new Task((Task) returningItems.get(0));
			returnTask.editState(state);
			returningItems.set(0, returnTask);

			//tempList = new LinkedList<Item>();

		} else {
			Label returnLabel;

			if (bufferLabelsList.size() > 0) {

				returningItems = new LinkedList<Item>(getAllLabels());
				Label tempLabel = new Label((Label) returningItems.get(0));
				tempLabel.editState(Operations.VIEW_LABEL_OPERATION);

				returningItems.set(0, tempLabel);

			} else {

				returnLabel = new Label(Operations.EMPTY_MESSAGE);
				returnLabel.editState(Operations.VIEW_ERROR);
				returningItems.add(returnLabel);
				
				return returningItems;
			}
		}
		
		return returningItems;
	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}

	/**
	 * ========================================================================
	 * =============== PROCESSING DELETE ======================================
	 * ========================================================================
	 */

	//@author A0111942N
	/**
	 * Process post-delete operation to return to GUI
	 * 
	 * @return List containing the deleted task with the "delete" state
	 */
	private LinkedList<Item> processDelete() {

		LinkedList<Item> returningTasks = new LinkedList<Item>();
		returningTasks.add(executeDelete());

		logger.log(Level.INFO, "Delete operation completed");

		return returningTasks;
	}

	//@author A0111942N
	/**
	 * This method executes the "delete" functionality of the program.
	 * 
	 * @return Deleted task
	 */
	private Item executeDelete() {

		int deleteID = 0;
		boolean isValid = true;
		
		String content = inputList.get(0).getContent();

		if (!content.isEmpty()) {
			try {
				deleteID = Integer.parseInt(inputList.get(0).getContent()) - 1;
			} catch (Exception e) {
				isValid = false;
			}
		}

		if (!isLabel && deleteID < bufferTasksList.size()) {
			Task deleteTask;

			if (!tempList.isEmpty()) {
				deleteTask = (Task) tempList.get(deleteID);
				tempList = new LinkedList<Item>();
			} else {
				deleteTask = bufferTasksList.get(deleteID);
			}

			bufferTasksList.remove(deleteTask);
			deleteTask.editState(Operations.DELETE_OPERATION);

			logger.log(Level.INFO, "Task deleted");
			return deleteTask;
			
		} else if (isLabel && deleteID < bufferLabelsList.size()) {
			
			Label deleteLabel;
			
			deleteLabel = bufferLabelsList.remove(deleteID);
			deleteLabel.editState(Operations.DELETE_LABEL_OPERATION);
			
			logger.log(Level.INFO, "Label deleted");
			return deleteLabel;
			
		} else {
			isValid = false;
		}
		
		if (!isValid) {
			Task deleteTask = new Task(Operations.EMPTY_MESSAGE);

			deleteTask.editState(Operations.DELETE_ERROR);

			logger.log(Level.INFO, "Invalid task to be deleted");
			return deleteTask;
		}
		
		return null;
	}

	/**
	 * ========================================================================
	 * =============== PROCESSING FIND ========================================
	 * ========================================================================
	 */

	//@author A0111942N
	/**
	 * Process post-view operation to return to GUI
	 * 
	 * @return List containing all the tasks (First task will have the "view"
	 *         state)
	 */
	private LinkedList<Item> processFind() {

		LinkedList<Item> returningTasks = new LinkedList<Item>();
		LinkedList<Task> searchTasks = new LinkedList<Task>();
		String keyword = "";
		
		// Normal Search
		if (inputList.size() == 1) {
			
			keyword = inputList.get(0).getContent();
			
			searchTasks = LogicSearch.searchTasks(keyword, bufferTasksList, 
					LogicSearch.SEARCH_TYPES.TYPE_ALL);
			
		} else {
			
			boolean isPowerSearch = false;
			LogicInputPair searchFieldPair = inputList.get(1);
			String searchField = searchFieldPair.getOperation();
			keyword = searchFieldPair.getContent();
			
			// Check if it's a power search
			
			if ( Operations.POWER_OPERATION.contains(searchField) ) {
				
				isPowerSearch = true;

				if( inputList.size() == 2 ) {
					System.out.println(">>>"+keyword);
					searchTasks = LogicSearch.searchTasks(keyword, bufferTasksList,
							LogicSearch.SEARCH_TYPES.TYPE_ALL, LogicSearch.SEARCH_TYPES.SEARCH_POWER_SEARCH);
				} else if ( inputList.size() > 2 ) {
					searchFieldPair = inputList.get(2);
					searchField = searchFieldPair.getOperation();
					keyword = searchFieldPair.getContent();
				}
			}
			
			
			if ( Operations.nameOperations.contains(searchField) ) {
				
				if (isPowerSearch) {
					searchTasks = LogicSearch.searchTasks(keyword, bufferTasksList,
							LogicSearch.SEARCH_TYPES.TYPE_NAME, LogicSearch.SEARCH_TYPES.SEARCH_POWER_SEARCH);
				} else {
					searchTasks = LogicSearch.searchTasks(keyword, bufferTasksList, 
							LogicSearch.SEARCH_TYPES.TYPE_NAME, LogicSearch.SEARCH_TYPES.TYPE_ALL);
				}
				
			} else if ( Operations.descriptionOperations.contains(searchField) ) {
				
				searchTasks = LogicSearch.searchTasks(keyword, bufferTasksList, 
						LogicSearch.SEARCH_TYPES.TYPE_DESCRIPTION, LogicSearch.SEARCH_TYPES.TYPE_ALL);
				
			} else if ( Operations.labelOperations.contains(searchField) ) {
				
				searchTasks = LogicSearch.searchTasks(keyword, bufferTasksList, 
						LogicSearch.SEARCH_TYPES.TYPE_LABEL, LogicSearch.SEARCH_TYPES.TYPE_ALL);
				
			} else if ( Operations.deadlineOperations.contains(searchField) ) {
				
				long searchDate = processDate(keyword);
				System.out.println(searchDate);
				
				searchTasks = LogicSearch.searchTasks(LogicSearch.SEARCH_TYPES.TYPE_DEADLINE,
						searchDate, bufferTasksList);
				
			}
			
		}
		
		Task returnTask;
		
		if (searchTasks.isEmpty()) {

			returnTask = new Task(Operations.EMPTY_MESSAGE);
			returnTask.editState(Operations.FIND_ERROR);
			returningTasks.add(returnTask);
			
		} else {
			
			returnTask = searchTasks.get(0);
			returnTask.editState(Operations.FIND_OPERATION);
			
		}

		tempList = new LinkedList<Item>(searchTasks);

		return new LinkedList<Item>(searchTasks);

	}

	/**
	 * ========================================================================
	 * =============== PROCESSING UNDO ========================================
	 * ========================================================================
	 */

	//@author A0111942N
	/**
	 * Process post-undo operation to return to GUI
	 * 
	 * @return List containing the deleted task with the "undo" state
	 */
	private LinkedList<Item> processUndo() {

		LinkedList<Task> tempTasks = new LinkedList<Task>(bufferTasksList);
		bufferTasksList = undoTasks;
		undoTasks = tempTasks;

		//		LinkedList<ReminderTask> tempReminders = LogicReminder.getList();
		//		LogicReminder.editList(undoReminders);
		//		undoReminders = new LinkedList<ReminderTask>( tempReminders );
		LogicReminder.getInstance().stopAllTasksReminder();
		LogicReminder.getInstance().regenReminderList(bufferTasksList);

		LinkedList<Item> returningTasks = new LinkedList<Item>();
		Task tempTask = new Task(Operations.NOT_EMPTY_MESSAGE);
		tempTask.editState(Operations.UNDO_OPERATION);

		returningTasks.add(tempTask);
		
		System.out.println(LogicReminder.getInstance());

		logger.log(Level.INFO, "Undo operation completed ");

		return returningTasks;
	}

	/**
	 * ========================================================================
	 * =============== PROCESSING SAVE ========================================
	 * ========================================================================
	 */

	//@author A0111942N
	/**
	 * Process post-saving operation to return to GUI
	 * 
	 * @return List containing an empty task with the "save" state
	 */
	private LinkedList<Item> processSave() {

		LinkedList<Item> returningTasks = new LinkedList<Item>();
		commitToStorage();
		returningTasks = new LinkedList<Item>();
		Task saveTask = new Task(Operations.EMPTY_MESSAGE);
		saveTask.editState(Operations.SAVE_OPERATION);
		returningTasks.add(saveTask);

		logger.log(Level.INFO, "Save operation completed");

		return returningTasks;
	}

	//@author A0111942N
	/**
	 * This method will contact StorageMain to export bufferTasksList and
	 * bufferLabelsList into a text file.
	 */
	private void commitToStorage() {
		
		
		if(isInitialize && hasChanged) {

			StorageMain.getInstance().storeObject(StorageMain.OBJ_TYPES.TYPE_TASK,
					bufferTasksList);
			StorageMain.getInstance().storeObject(StorageMain.OBJ_TYPES.TYPE_LABEL,
					bufferLabelsList);
			
			hasChanged = false;
		}
	}
	
	/**
	 * ========================================================================
	 * =============== PROCESSING SAVE ========================================
	 * ========================================================================
	 */

	//@author A0111942N
	/**
	 * Process post-saving operation to return to GUI
	 * 
	 * @return List containing an empty task with the "save" state
	 */
	private LinkedList<Item> processDone(boolean toggle) {

		LinkedList<Item> returningTasks = new LinkedList<Item>();
		Task returningTask;

		try {

			int doneId = Integer.parseInt( inputList.get(0).getContent() ) - 1;
			Task doneTask;

			if(tempList.size() > 0) {
				Task removed = (Task) tempList.remove(doneId);
				doneTask = new Task(removed);
				bufferTasksList.remove(removed);
				tempList.clear();
			} else {
				doneTask = new Task( bufferTasksList.remove(doneId) );
			}

			doneTask.toggleDone(toggle);
			
			bufferTasksList.add( doneTask );

			returningTask = new Task(doneTask);
			
			if(toggle) {
				returningTask.editState(Operations.DONE_OPERATION);
			} else {
				returningTask.editState(Operations.NOT_DONE_OPERATION);
			}

			returningTasks.add(returningTask);


		} catch (Exception e) {
			logger.log(Level.INFO, "DONE OPERATION: Invalid ID");

			returningTask = new Task(Operations.EMPTY_MESSAGE);
			returningTask.editState(Operations.DONE_ERROR);

			returningTasks.add(returningTask);
		}

		return returningTasks;
	}
	
	

	/**
	 * ========================================================================
	 * =============== MISC. ==================================================
	 * ========================================================================
	 */

	//@author A0111942N
	/**
	 * Gets the time stamp (ID) of the label
	 * 
	 * @param Name
	 *            of label
	 * @return Label time stamp (ID)
	 */
	private long getLabelId(String label) {

		long labelID = -1;

		for (int j = 0; j < bufferLabelsList.size(); j++) {

			if (bufferLabelsList.get(j).isLabel(label)) {
				Label tempLabel = bufferLabelsList.get(j);
				labelID = tempLabel.getTimeStamp();
			}
		}
		System.out.println(labelID);
		return labelID;
	}

	//@author A0111942N
	/**
	 * @return Today 23:59 in millisecond
	 */
	public static long getEndOfToday() {
		Calendar endOfToday = Calendar.getInstance();
		endOfToday.set(Calendar.HOUR_OF_DAY, 23);
		endOfToday.set(Calendar.MINUTE, 59);
		endOfToday.set(Calendar.SECOND, 59);
		endOfToday.set(Calendar.MILLISECOND, 999);

		return endOfToday.getTimeInMillis();
	}

	//@author A0111942N
	/**
	 * Process date and time format
	 * 
	 * @return Today 23:59 in millisecond
	 */
	public long processDate(String input) {

		int day = -1;
		int month = -1;
		int year = -1;
		int hour = -1;
		int minute = -1;
		String period = "AM";
		
		String[] inputArray = input.split(" ");
		Calendar tempCal = Calendar.getInstance();
		
		// At least need the day and month
		if (inputArray.length < 2) {
			return -1;
		}
		
		try {
			
			// Specify time only hh mm am/pm
			if ( inputArray.length == 3 && !isNumeric(inputArray[2]) ) {
				processTime(inputArray, tempCal);
				throw new Exception(LOG_NAME);
			}
			
			day = Integer.parseInt(inputArray[0]);
			month = Operations.returnMonth(inputArray[1]);

			tempCal.set(Calendar.DATE, day);
			tempCal.set(Calendar.MONTH, month - 1); // Start from index 0
			
			// Specify year too
			if ( inputArray.length >= 3 ) {
				year = Integer.parseInt(inputArray[2]);
				tempCal.set(Calendar.YEAR, year);
			}
			
			tempCal.set(Calendar.HOUR_OF_DAY, 23);
			tempCal.set(Calendar.MINUTE, 59);
			tempCal.set(Calendar.MILLISECOND, 999);
			
			// Specify time too
			if ( inputArray.length == 6 ) {
				String[] timeArray = { inputArray[3], inputArray[4], inputArray[5] };
				processTime(timeArray, tempCal);
			}
			
			

		} catch (Exception e) {
			
			if( !e.getMessage().equals(LOG_NAME) ) {
				return -1;
			}
			
		}
		

		return tempCal.getTimeInMillis();
	}

	private void processTime(String[] inputArray, Calendar tempCal) {
		int hour;
		int minute;
		String period;
		hour = Integer.parseInt(inputArray[0]);
		minute = Integer.parseInt(inputArray[1]);
		period = inputArray[2].toLowerCase();
		
		if ( period.equals("am") && hour == 12 ) {
			hour = 0;
		} else if ( period.equals("pm") && hour != 12 ) {
			hour += 12;
		}
		
		tempCal.set(Calendar.HOUR_OF_DAY, hour);
		tempCal.set(Calendar.MINUTE, minute);
		tempCal.set(Calendar.SECOND, 0);
		tempCal.set(Calendar.MILLISECOND, 0);
	}

	//@author A0111942N
	/**
	 * This method checks if the input word is an operation.
	 * 
	 * @return Whether a string is an operation
	 */
	private boolean isOperation(String word) {

		return word.startsWith(Operations.OPERATION);
	}

	//@author A0111942N
	/**
	 * @return All the labels in LinkedList
	 */
	public static LinkedList<Label> getAllLabels() {
		Collections.sort(bufferLabelsList);
		return bufferLabelsList;
	}

	//@author A0111942N
	/**
	 * This method returns the list of all the tasks.
	 * 
	 * @return All tasks in LinkedList
	 */
	public static LinkedList<Task> getAllTasks() {
		
		Collections.sort(bufferTasksList);
		return bufferTasksList;
	}
	
	//@author A0111942N
	/**
	 * This method returns the list of all uncompleted tasks.
	 * 
	 * @return All uncompleted tasks in LinkedList
	 */
	public static LinkedList<Task> getNotDoneTasks() {

		LinkedList<Task> returnTasks = new LinkedList<Task>();

		for(int i = 0; i < bufferTasksList.size(); i++) {

			Task tempTask = bufferTasksList.get(i);

			if( !tempTask.getDone() ) {
				returnTasks.add(tempTask);
			}
		}

		Collections.sort(returnTasks);
		return returnTasks;
	}
	
	//@author A0111942N
	/**
	 * This method returns the list of all done tasks.
	 * 
	 * @return All done tasks in LinkedList
	 */
	public static LinkedList<Task> getDoneTasks() {

		LinkedList<Task> returnTasks = new LinkedList<Task>();

		for(int i = 0; i < bufferTasksList.size(); i++) {

			Task tempTask = bufferTasksList.get(i);

			if( tempTask.getDone() ) {
				returnTasks.add(tempTask);
			}
		}

		Collections.sort(returnTasks);
		return returnTasks;
	}
	
	//@author A0111942N
	/**
	 * This method returns the list of all floating tasks.
	 * 
	 * @return All floating tasks in LinkedList
	 */
	public static LinkedList<Task> getFloatingTasks() {

		LinkedList<Task> returnTasks = new LinkedList<Task>();

		for(int i = 0; i < bufferTasksList.size(); i++) {

			Task tempTask = bufferTasksList.get(i);

			if( tempTask.getDeadline() == -1 ) {
				returnTasks.add(tempTask);
			}
		}

		Collections.sort(returnTasks);
		return returnTasks;
	}
	
	//@author A0111942N
	/**
	 * This method returns the list of all floating tasks.
	 * 
	 * @return All floating tasks in LinkedList
	 */
	public static Color getLabelColor(long labelId) {

		for (int i = 0; i < bufferLabelsList.size(); i++) {
			
			Label label = bufferLabelsList.get(i);
			
			if (label.getTimeStamp() == labelId) {
				
				Color color = Color.decode(label.getColor());
				return color;
			}
		}
		
		return Color.WHITE;
	}
	
	/**
	 * For testing purposes...
	 */
	public static void main(String[] arg) {
		/*
		
		LogicMain logic = new LogicMain();
		
		Label label = getAllLabels().get(0);
		System.out.println( getLabelColor(label.getTimeStamp()) );

		System.out.println( logic.processDate("12 34 am") );
		System.out.println( logic.processDate("9 12") );
		System.out.println( logic.processDate("11 12 2041") );
		*/
	}
}