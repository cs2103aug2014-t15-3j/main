//@author A0111942N

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogicMain {

	// Constant variables
	private final static String LOG_NAME = "LogicMain";

	// Flag to check if the program has initialized.
	private static boolean isInitialize = false;

	// StorageMain object
	private static StorageMain storageMain;

	// Operation object
	private Operations operations;

	// Data structures
	private static LinkedList<Task> bufferTasksList = new LinkedList<Task>();
	private static LinkedList<Label> bufferLabelsList = new LinkedList<Label>();
	private static LinkedList<Task> undoTasks = new LinkedList<Task>();
	private static LinkedList<Item> tempList = new LinkedList<Item>();
	private LinkedList<LogicInputPair> inputList;
	private String[] inputArray;

	// Logger: Use to troubleshoot problems
	private Logger logger = Logger.getLogger(LOG_NAME);
	
	// Flag to check if the current operation is a logic operation
	private boolean isLabel;

	
	public LogicMain() {
		initialize();
	}

	
	//@author A0111942N
	/**
	 * Checks if the component has been initialized.
	 * It will initialize the component if it is not.
	 */
	private void initialize() {

		if (!isInitialize) {
			operations = new Operations();
			storageMain = new StorageMain();

			retrieveFromStorage();

			undoTasks = new LinkedList<Task>(bufferTasksList);

			isInitialize = true;

			logger.log(Level.INFO, "Initializing is complete");
		} else {
			logger.log(Level.INFO, "LogicMain has already been initiated");
		}
	}

	
	//@author A0111942N
	/**
	 * Retrieve tasks from Storage
	 */
	private void retrieveFromStorage() {
		
		Object retrievedTasks = storageMain.retrieveObject(StorageMain.OBJ_TYPES.TYPE_TASK);

		if (retrievedTasks instanceof LinkedList<?>) {
			bufferTasksList = (LinkedList<Task>) retrievedTasks;
		} else {
			bufferTasksList = new LinkedList<Task>();
			logger.log(Level.WARNING,
					"Unable to retrieve tasks from storage");
		}
		
		Object retrievedLabels = storageMain.retrieveObject(StorageMain.OBJ_TYPES.TYPE_LABEL);

		if (retrievedLabels instanceof LinkedList<?>) {
			bufferLabelsList = (LinkedList<Label>) retrievedLabels;
		} else {
			bufferLabelsList = new LinkedList<Label>();
			logger.log(Level.WARNING,
					"Unable to retrieve labels from storage");
		}
	}

	
	//@author A0111942N
	/**
	 * This method will process the user's input and perform either
	 * add, edit, view, delete or save based on the input.
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
			isLabel = true;
			
		}
		
		if (Operations.addOperations.contains(mainOperation)) {
			
			undoTasks = new LinkedList<Task>(bufferTasksList);
			returnTasks = processAdd();
			
		} else if (Operations.editOperations.contains(mainOperation)) {
			
			undoTasks = new LinkedList<Task>(bufferTasksList);
			returnTasks = processEdit();
			
		} else if (Operations.viewOperations.contains(mainOperation)) {

			returnTasks = processView();
			
		} else if (Operations.findOperations.contains(mainOperation)) {

			returnTasks = processFind();
			
		} else if (Operations.undoOperations.contains(mainOperation)) {

			returnTasks = processUndo();
			
		} else if (Operations.deleteOperations.contains(mainOperation)) {
			
			undoTasks = new LinkedList<Task>(bufferTasksList);
			returnTasks = processDelete();
			
		} else if (Operations.saveOperations.contains(mainOperation)) {
			
			undoTasks = new LinkedList<Task>(bufferTasksList);
			returnTasks = processSave();
			
		} else {

			returnTasks = new LinkedList<Item>();
			
		}
		
		return returnTasks;
	}

	//@author A0111942N
	/**
	 * This method cleans up the input string by removing
	 * empty spaces and breaks at the front and back of it.
	 */
	private static String cleanUpInput(String input) {
		return input.trim();
	}


	//@author A0111942N
	/**
	 * Pre-process the user's input and categories
	 * them into a list.
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

					LogicInputPair previousOperation
					= new LogicInputPair(operation, content);

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
					inputList.add(new LogicInputPair(operation, content));
				}
			}
		}
	}

	
	
	
	
	/**
	 * ================================================================================
	 * =======  PROCESSING ADD ========================================================
	 * ================================================================================
	 */
	
	
	//@author A0111942N
	/**
	 * Process post-adding operation to return to GUI
	 * 
	 * @return	List containing the added task
	 * 			with the "add" state
	 */
	private LinkedList<Item> processAdd() {

		LinkedList<Item> returningItem = new LinkedList<Item>();
		
		Item addTask = executeAdd();

		if (addTask != null) {
			
			Item returnItem;
			
			if(!isLabel) {
				returnItem = new Task( (Task) addTask );
				returnItem.editState(Operations.ADD_OPERATION);
			} else {
				returnItem = new Label( (Label) addTask );
				returnItem.editState(Operations.ADD_OPERATION);
			}
			
			returningItem.add(returnItem);
		}

		logger.log(Level.INFO, "Add operation completed");

		return returningItem;
	}

	//@author A0111942N
	/**
	 * This method executes the "add" functionality of the program.
	 * 
	 * Return newly added task.
	 * If the task's name is not provided, it will return null.
	 * 
	 * @return	Newly added task
	 */
	private Item executeAdd() {

		// Method variables
		String name = "";
		String description = "";
		long deadline = -1;
		String color = "";
		long labelID = -1;

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
				
			} else if (Operations.deadlineOperations.contains(operation)) {

				try {
					
					String dateInput = inputList.get(i).getContent();
					deadline = getDeadline(dateInput);
					
				} catch (ParseException e) {
					
					logger.log(Level.WARNING, "Wrong date format!");
					
				}
				
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

			if(deadline == -1) {
				deadline = getEndOfToday();
				newTask.editDeadline(deadline);
			}

			if(labelID != -1) {
				newTask.editLabel(labelID);
			}

			newItem = newTask;

			logger.log(Level.INFO, "New task added to bufferTasksList");
			
		} else {

			Label newLabel = new Label(name);
			newLabel.editState(Operations.ADD_LABEL_OPERATION);
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
	 * ================================================================================
	 * =======  PROCESSING EDIT =======================================================
	 * ================================================================================
	 */
	
	
	//@author A0111942N
	/**
	 * Process post-editing operation to return to GUI
	 * 
	 * @return	List containing the edited task
	 * 			with the "edit" state
	 */
	private LinkedList<Item> processEdit() {

		LinkedList<Item> returningTasks = new LinkedList<Item>();

		Item returnTask = executeEdit();

		if(returnTask == null) {

			return returningTasks;

		}

		returnTask.editState(Operations.EDIT_OPERATION);
		returningTasks.add(returnTask);

		logger.log(Level.INFO, "Edit operation completed");

		return returningTasks;
	}
	

	//@author A0111942N
	/**
	 * This method executes the "edit" functionality of the program.
	 * 
	 * Return edited task.
	 * If the task's name or id is not provided, it will return null.
	 * 
	 * @return	Edited task
	 */
	private Item executeEdit() {

		int editID = -1;
		String name = "";
		String description = "";
		String color = "";
		long labelId = -1;
		long deadline = -1;
		boolean nameEdited = false;
		boolean descriptionEdited = false;
		boolean deadlineEdited = false;
		boolean colorEdited = false;

		for (int i = 0; i < inputList.size(); i++) {

			String operation = inputList.get(i).getOperation();

			if (Operations.editOperations.contains(operation)) {

				String stringID = inputList.get(i).getContent();

				try {

					editID = Integer.parseInt(stringID);

				} catch (Exception e) {

					logger.log(Level.INFO, "Invalid ID");
					return null;

				}



				if (editID >= bufferTasksList.size()) {
					
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
				
			} else if (Operations.deadlineOperations.contains(operation)) {

				try {
					
					String dateInput = inputList.get(i).getContent();
					deadline = getDeadline(dateInput);

					deadlineEdited = true;
					
				} catch (ParseException e) {
					
					logger.log(Level.WARNING, "Wrong date format!");
					
				}
				
			} else if (Operations.colorOperations.contains(operation)) {

				color = inputList.get(i).getContent();

				if (color.isEmpty()) {

					logger.log(Level.INFO, "Color operation: Invalid color");
					return null;
					
				}

				colorEdited = true;
				
			} else if (Operations.labelOperations.contains(operation) && i > 0) {

				String label = inputList.get(i).getContent();
				labelId = getLabelId(label);

				if (labelId == -1) {

					logger.log(Level.INFO, "Edit label operation: Invalid label");
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
			if (deadlineEdited) {
				
				newTask.editDeadline(deadline);
				
			}
			
			// Check if label has been edited
			if (labelId != -1) {
				
				newTask.editLabel(labelId);
				
			}

			bufferTasksList.add(newTask);
			editItem = newTask;

		} else {
			
			// PROCESSING FOR LABEL
			
			Label editLabel = bufferLabelsList.get(editID);

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
	 * ================================================================================
	 * =======  PROCESSING VIEW =======================================================
	 * ================================================================================
	 */
	
	
	//@author A0111942N
	/**
	 * Process post-view operation to return to GUI
	 * 
	 * @return	List containing all the tasks
	 * 			(First task will have the "view" state)
	 */
	private LinkedList<Item> processView() {

		LinkedList<Item> returningItems = new LinkedList<Item>();
		Task returnTask;

		LogicInputPair viewOperation = inputList.get(0);

		if (!isLabel) {

			if (bufferTasksList.size() != 0 && viewOperation.getContent() == "") {

				Collections.sort(bufferTasksList);
				returningItems = new LinkedList<Item>(bufferTasksList);

			} else if (bufferTasksList.size() != 0) {

				try {

					int amountDisplayTasks = Integer.parseInt(viewOperation
							.getContent());

					amountDisplayTasks = Math.min(bufferTasksList.size(),
							amountDisplayTasks);

					for (int i = 0; i < amountDisplayTasks; i++) {
						Task tempTask = bufferTasksList.get(i);
						returningItems.add(tempTask);
					}

				} catch (NumberFormatException e) {

					// For label

				}

			} else {

				returnTask = new Task(Operations.EMPTY_MESSAGE);
				returnTask.editState(Operations.VIEW_OPERATION);
				returningItems.add(returnTask);

				return returningItems;

			}

			returnTask = new Task( (Task) returningItems.get(0));
			returnTask.editState(Operations.VIEW_OPERATION);
			returningItems.set(0, returnTask);

			tempList = new LinkedList<Item>();

		} else {
			Label returnLabel;

			if (bufferLabelsList.size() > 0) {

				returningItems = new LinkedList<Item>(bufferLabelsList);
				Label tempLabel = new Label( (Label) returningItems.get(0) );
				tempLabel.editState(Operations.VIEW_OPERATION);

				returningItems.set(0, tempLabel);

			} else {

				returnLabel = new Label(Operations.EMPTY_MESSAGE);
				returnLabel.editState(Operations.VIEW_OPERATION);
				returningItems.add(returnLabel);

				return returningItems;
			}
		}

		return returningItems;
	}
	
	
	
	
	
	/**
	 * ================================================================================
	 * =======  PROCESSING DELETE =====================================================
	 * ================================================================================
	 */
	
	
	//@author A0111942N
	/**
	 * Process post-delete operation to return to GUI
	 * 
	 * @return	List containing the deleted task with
	 * 			the "delete" state
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
	 * @return	Deleted task
	 */
	private Task executeDelete() {

		int deleteID = 0;
		String content = inputList.get(0).getContent();

		if (!content.isEmpty()) {
			deleteID = Integer.parseInt(inputList.get(0).getContent());
		}

		if (deleteID < bufferTasksList.size()) {
			Task deleteTask;

			if (!tempList.isEmpty()) {
				deleteTask = (Task) tempList.get(deleteID);
				tempList = new LinkedList<Item>();
			} else {
				deleteTask = bufferTasksList.get(deleteID);
			}

			bufferTasksList.remove(deleteTask);

			logger.log(Level.INFO, "Task deleted");
			return deleteTask;
		} else {
			Task deleteTask = new Task(Operations.EMPTY_MESSAGE);
			deleteTask.editState(Operations.DELETE_OPERATION);

			logger.log(Level.INFO, "Invalid task to be deleted");
			return deleteTask;
		}
	}
	
	
	
	
		
	/**
	 * ================================================================================
	 * =======  PROCESSING FIND =======================================================
	 * ================================================================================
	 */

	//@author A0111942N
	/**
	 * Process post-view operation to return to GUI
	 * 
	 * @return	List containing all the tasks
	 * 			(First task will have the "view" state)
	 */
	private LinkedList<Item> processFind() {

		LinkedList<Item> returningTasks = new LinkedList<Item>();
		String keyword = inputList.get(0).getContent();
		keyword = keyword.toLowerCase();

		Task returnTask;

		for (int i = 0; i < bufferTasksList.size(); i++) {

			Task tempTask = bufferTasksList.get(i);
			if (tempTask.contains(keyword)) {
				tempTask.editState(Operations.FIND_OPERATION);
				returningTasks.add(tempTask);
			}
		}

		if (returningTasks.isEmpty()) {

			returnTask = new Task(Operations.EMPTY_MESSAGE);
			returningTasks.add(returnTask);
		}

		tempList = new LinkedList<Item>(returningTasks);

		return returningTasks;

	}	
	
	
	
	
	
	/**
	 * ================================================================================
	 * =======  PROCESSING UNDO =======================================================
	 * ================================================================================
	 */
	
	
	//@author A0111942N
	/**
	 * Process post-undo operation to return to GUI
	 * 
	 * @return	List containing the deleted task with
	 * 			the "undo" state
	 */
	private LinkedList<Item> processUndo() {

		LinkedList<Task> tempTasks = new LinkedList<Task>(bufferTasksList);
		bufferTasksList = undoTasks;
		undoTasks = tempTasks;

		LinkedList<Item> returningTasks = new LinkedList<Item>();
		Task tempTask = new Task(Operations.NOT_EMPTY_MESSAGE);
		tempTask.editState(Operations.UNDO_OPERATION);

		returningTasks.add(tempTask);

		logger.log(Level.INFO, "Undo operation completed ");

		return returningTasks;
	}
	
	
	
	
	
	/**
	 * ================================================================================
	 * =======  PROCESSING SAVE =======================================================
	 * ================================================================================
	 */
	
	
	//@author A0111942N
	/**
	 * Process post-saving operation to return to GUI
	 * 
	 * @return	List containing an empty task
	 * 			with the "save" state
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
	 * This method will contact StorageMain to export bufferTasksList and bufferLabelsList
	 * into a text file.
	 */
	private void commitToStorage() {

		storageMain.storeObject(StorageMain.OBJ_TYPES.TYPE_TASK,bufferTasksList);
		storageMain.storeObject(StorageMain.OBJ_TYPES.TYPE_LABEL,bufferLabelsList);
	}
	
	
	
	
	
	/**
	 * ================================================================================
	 * =======  MISC. =================================================================
	 * ================================================================================
	 */
	
	
	//@author A0111942N
	/**
	 * Gets the time stamp (ID) of the label
	 * 
	 * @param	Name of label
	 * @return	Label time stamp (ID)
	 */
	private long getLabelId(String label) {

		long labelID = -1;

		for(int j = 0; j < bufferLabelsList.size(); j++) {

			if(bufferLabelsList.get(j).isLabel(label)) {
				Label tempLabel = bufferLabelsList.get(j);
				labelID = tempLabel.getTimeStamp();
			}
		}
		System.out.println(labelID);
		return labelID;
	}

	
	//@author A0111942N
	/**
	 * @return	Today 23:59 in millisecond
	 */
	private long getEndOfToday() {
		Calendar endOfToday = Calendar.getInstance();
		endOfToday.set(Calendar.HOUR_OF_DAY, 23);
		endOfToday.set(Calendar.MINUTE, 59);
		endOfToday.set(Calendar.SECOND, 59);
		endOfToday.set(Calendar.MILLISECOND, 999);

		return endOfToday.getTimeInMillis();
	}

	
	//@author A0111942N
	/**
	 * Convert string to milliseconds and return
	 * milliseconds.
	 * 
	 * @return	Date in milliseconds
	 */
	private long getDeadline(String dateInput) throws ParseException {

		long deadline;
		dateInput = dateInput.toUpperCase();

		SimpleDateFormat format = new SimpleDateFormat(
				Operations.DATE_INPUT_FORMAT);

		Date date = format.parse(dateInput);
		deadline = date.getTime();

		return deadline;
	}

	
	//@author A0111942N
	/**
	 * This method checks if the input word is an operation.
	 * 
	 * @return	Whether a string is an operation
	 */
	private boolean isOperation(String word) {

		return word.startsWith(Operations.OPERATION);
	}

	
	//@author A0111942N
	/**
	 * @return	All the labels in LinkedList
	 */
	public static LinkedList<Label> getAllLabels() {
		return bufferLabelsList;
	}
	
	
	//@author A0111942N
	/**
	 * This method returns the list of all the tasks.
	 * 
	 * @return All tasks in LinkedList
	 */
	public static LinkedList<Task> getAllTasks() {
		return bufferTasksList;
	}
}