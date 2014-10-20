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
	
	//@author A0112898U
	/**
	 * Enum for search methods 
	 * 
	 */
	public static enum SEARCH_TYPES {
		
		TYPE_NAME,
		TYPE_LABEL,
		TYPE_DESCRIPTION,
		TYPE_TIME_ADDED,
		TYPE_DEADLINE
		
	}

	// Flag to check if the program has initialized.
	private static boolean isInitialize = false;

	// StorageMain object
	private static StorageMain storageMain;

	// Operation object
	private Operations operations;

	// Data structures
	private static LinkedList<Task> bufferList = new LinkedList<Task>();
	private static LinkedList<Item> tempList = new LinkedList<Item>();
	private LinkedList<LogicInputPair> inputList;
	private String[] inputArray;

	// Logger: Use to troubleshoot problems
	private Logger logger = Logger.getLogger(LOG_NAME);

	public LogicMain() {
		initialize();
	}

	//@author A0111942N
	// API: This method will process the user's input and perform either
	// add, edit, view, delete or save based on the input.
	public LinkedList<Item> processInput(String input) {

		input = cleanUpInput(input);
		preProcessInput(input);

		String mainOperation = inputList.get(0).getOperation();

		if (Operations.addOperations.contains(mainOperation)) {

			return postAdd();
		} else if (Operations.editOperations.contains(mainOperation)) {

			return postEdit();
		} else if (Operations.viewOperations.contains(mainOperation)) {

			return postView();
		} else if (Operations.findOperations.contains(mainOperation)) {
			
			tempList = new LinkedList<Item>( postFind() );
			return postFind();
		} else if (Operations.deleteOperations.contains(mainOperation)) {

			return postDelete();
		} else if (Operations.saveOperations.contains(mainOperation)) {

			return postSave();
		} else {

			return new LinkedList<Item>();
		}
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

			retrieveTasks();

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
	private void retrieveTasks() {
		Object retrievedObject = storageMain.retrieveObject(StorageMain.OBJ_TYPES.TYPE_TASK);

		if (retrievedObject instanceof LinkedList<?>) {
			bufferList = (LinkedList<Task>) retrievedObject;
		} else {
			bufferList = new LinkedList<Task>();
			logger.log(Level.WARNING,
					"Unable to retrieve tasks from storage");
		}
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
	
	//@author A0111942N
	/**
	 * This method executes the "add" functionality of the program.
	 * 
	 * Return newly added task.
	 * If the task's name is not provided, it will return null.
	 * 
	 * @return	Newly added task
	 */
	private Task executeAdd() {
		
		// Method variables
		String name = "";
		String description = "";
		long deadline = -1;

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
			}
		}

		Task newTask = new Task(name, description);

		bufferList.add(newTask);
		
		if(deadline == -1) {
			deadline = getEndOfToday();
		}
		
		newTask.editDeadline(deadline);

		logger.log(Level.INFO, "New task added to bufferlist");
		return newTask;
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

		if (deleteID < bufferList.size()) {
			Task deleteTask;
			
			if (!tempList.isEmpty()) {
				deleteTask = (Task) tempList.get(deleteID);
				tempList = new LinkedList<Item>();
			} else {
				deleteTask = bufferList.get(deleteID);
			}
			
			bufferList.remove(deleteTask);

			logger.log(Level.INFO, "Task deleted");
			return deleteTask;
		} else {
			Task deleteTask = new Task(Operations.EMPTY_MESSAGE);
			deleteTask.editState(Operations.DELETE_OPERATION);

			logger.log(Level.INFO, "Invalid task to be deleted");
			return deleteTask;
		}
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
	private Task executeEdit() {

		int editID = -1;
		String name = "";
		String description = "";
		long deadline = -1;
		boolean nameEdited = false;
		boolean descriptionEdited = false;
		boolean deadlineEdited = false;

		for (int i = 0; i < inputList.size(); i++) {

			String operation = inputList.get(i).getOperation();

			if (Operations.editOperations.contains(operation)) {

				String stringID = inputList.get(i).getContent();

				if (stringID.isEmpty()) {
					return null;
				}

				editID = Integer.parseInt(stringID);

				if (editID >= bufferList.size()) {
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
			}
		}

		Task editTask;
		if (!tempList.isEmpty()) {
			editTask = (Task) tempList.get(editID);
			tempList = new LinkedList<Item>();
		} else {
			editTask = bufferList.get(editID);
		}

		if (nameEdited) {
			editTask.editName(name);
		}
		if (descriptionEdited) {
			editTask.editDescription(description);
		}
		if (deadlineEdited) {
			editTask.editDeadline(deadline);
		}

		logger.log(Level.INFO, "Task edited");
		return editTask;
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
	 * This method will contact StorageMain to export the bufferList
	 * into a text file.
	 */
	private void commitToStorage() {

		storageMain.storeObject(StorageMain.OBJ_TYPES.TYPE_TASK,bufferList);
	}
	
	
	//@author A0111942N
	/**
	 * This method returns the list of all the tasks.
	 */
	public LinkedList<Task> getAllTasks() {
		return bufferList;
	}
	
	//@author A0111942N
	/**
	 * Process post-adding operation to return to GUI
	 * 
	 * @return	List containing the added task
	 * 			with the "add" state
	 */
	private LinkedList<Item> postAdd() {

		LinkedList<Item> returningTasks = new LinkedList<Item>();

		Task addTask = executeAdd();

		if (addTask != null) {
			Task returnTask = new Task(addTask);
			returnTask.editState(Operations.ADD_OPERATION);
			returningTasks.add(returnTask);
		}

		logger.log(Level.INFO, "Add operation completed");

		return returningTasks;
	}

	//@author A0111942N
	/**
	 * Process post-editing operation to return to GUI
	 * 
	 * @return	List containing the edited task
	 * 			with the "edit" state
	 */
	private LinkedList<Item> postEdit() {

		LinkedList<Item> returningTasks = new LinkedList<Item>();
		
		Task returnTask = new Task(executeEdit());
		returnTask.editState(Operations.EDIT_OPERATION);
		returningTasks.add(returnTask);

		logger.log(Level.INFO, "Edit operation completed");

		return returningTasks;
	}
	
	//@author A0111942N
	/**
	 * Process post-view operation to return to GUI
	 * 
	 * @return	List containing all the tasks
	 * 			(First task will have the "view" state)
	 */
	private LinkedList<Item> postView() {
		
		LinkedList<Item> returningTasks = new LinkedList<Item>();
		Task returnTask;
		
		LogicInputPair viewOperation = inputList.get(0);
				
		if (bufferList.size() != 0 && viewOperation.getContent() == "") {

			Collections.sort(bufferList);
			returningTasks = new LinkedList<Item>(bufferList);

		} else if (bufferList.size() != 0) {
			
			try {
				
				int amountDisplayTasks = Integer.parseInt(viewOperation
						.getContent());
				
				amountDisplayTasks = Math.min(bufferList.size(),
												amountDisplayTasks);

				for (int i = 0; i < amountDisplayTasks; i++) {
					Task tempTask = bufferList.get(i);
					returningTasks.add(tempTask);
				}
				
			} catch (NumberFormatException e) {
				
				// For label
				
			}
			
		} else {
			
			returnTask = new Task(Operations.EMPTY_MESSAGE);
			returnTask.editState(Operations.VIEW_OPERATION);
			returningTasks.add(returnTask);
			
			return returningTasks;
			
		}
		
		returnTask = new Task( (Task) returningTasks.get(0));
		returnTask.editState(Operations.VIEW_OPERATION);
		returningTasks.set(0, returnTask);
		
		return returningTasks;
	}
	
	//@author A0111942N
	/**
	 * Process post-view operation to return to GUI
	 * 
	 * @return	List containing all the tasks
	 * 			(First task will have the "view" state)
	 */
	private LinkedList<Item> postFind() {
		
		LinkedList<Item> returningTasks = new LinkedList<Item>();
		String keyword = inputList.get(0).getContent();
		keyword = keyword.toLowerCase();
		
		Task returnTask;

		for (int i = 0; i < bufferList.size(); i++) {

			Task tempTask = bufferList.get(i);
			if (tempTask.contains(keyword)) {
				tempTask.editState(Operations.FIND_OPERATION);
				returningTasks.add(tempTask);
			}
		}

		if (returningTasks.isEmpty()) {

			returnTask = new Task(Operations.EMPTY_MESSAGE);
			returnTask.editState(Operations.FIND_OPERATION);
			returningTasks.add(returnTask);
		}
		
		return returningTasks;

	}
	
	//@author A0111942N
	/**
	 * Process post-delete operation to return to GUI
	 * 
	 * @return	List containing the deleted task with
	 * 			the "delete" state
	 */
	private LinkedList<Item> postDelete() {

		LinkedList<Item> returningTasks = new LinkedList<Item>();
		returningTasks.add(executeDelete());

		logger.log(Level.INFO, "Delete operation completed");

		return returningTasks;
	}
	
	//@author A0111942N
	/**
	 * Process post-saving operation to return to GUI
	 * 
	 * @return	List containing an empty task
	 * 			with the "save" state
	 */
	private LinkedList<Item> postSave() {

		LinkedList<Item> returningTasks = new LinkedList<Item>();
		commitToStorage();
		returningTasks = new LinkedList<Item>();
		Task saveTask = new Task(Operations.EMPTY_MESSAGE);
		saveTask.editState(Operations.SAVE_OPERATION);
		returningTasks.add(saveTask);

		logger.log(Level.INFO, "Save operation completed");

		return returningTasks;
	}
	
	//@author A0112898U
	/**
	 * public overloaded method searchTasks() for 'Long' datatype query
	 * for outside call to retrieve Stored Task 
	 * 
	 * @param searchType - the type of query type i.e. TYPE_DEADLINE/TYPE_TIME_ADDED
	 * @param queryParam - query of 'Long' datatype variable is to input 
	 * 
	 * @return queryTasks returns a LinkedList with all task categorized with the 
	 * same 'searchType' & 'Long' datatype variable
	 * 
	 */
	public LinkedList<Task> searchTasks(SEARCH_TYPES searchType, long queryParam){
		
		LinkedList<Task> storedTasks = bufferList;
		LinkedList<Task> queryTasks = new LinkedList<Task>();
		
		switch(searchType){
		
			case TYPE_DEADLINE:
				
				for(Task t:storedTasks){
					
					if(t.getDeadline() == queryParam){
						queryTasks.add(t);
					}
				}
				
				break;
				
		
			case TYPE_TIME_ADDED:
				
				for(Task t:storedTasks){
					
					if(t.getTimeStamp() == queryParam){
						queryTasks.add(t);
					}
				}
				
				break;
				
			default:
				System.out.println("Type not supported");
				return null;
		}
		
		
		
		return queryTasks;
	}
	
	//@author A0112898U
	/**
	 * public overloaded method searchTasks() for 'String' datatype query
	 * for outside call to retrieve Stored Task 
	 * 
	 * @param searchType - the type of query type i.e. TYPE_NAME/TYPE_LABEL/TYPE_DESCRIPTION
	 * @param queryParam - query of 'String' datatype variable is to input 
	 * 
	 * @return queryTasks returns a LinkedList with all task categorized with the 
	 * same 'searchType' & 'String' datatype variable
	 * 
	 */
	public LinkedList<Task> searchTasks(SEARCH_TYPES searchType, String queryParam){
		
		LinkedList<Task> storedTasks = bufferList;
		LinkedList<Task> queryTasks = new LinkedList<Task>();
		
		switch(searchType){
		
			case TYPE_NAME:
				
				for(Task t:storedTasks){
					
					if(t.getName() == queryParam){
						queryTasks.add(t);
					}
				}
				
				break;
		
			case TYPE_LABEL:
				
				for(Task t:storedTasks){
					
					if(t.getLabel() == queryParam){
						queryTasks.add(t);
					}
				}
				
				break;
				
			case TYPE_DESCRIPTION:
				
				for(Task t:storedTasks){
					
					if(t.getDescription().contains(queryParam)){
						queryTasks.add(t);
					}
				}
				
				break;	
				
			default:
				System.out.println("Type not supported");
				return null;
		}
		
		return queryTasks;
	}

	//@author A0111942N
	/**
	 * Mainly for testing purpose.
	 * 
	 * @return	All the tasks
	 */
	public void printBufferList() {
		System.out.println(bufferList.toString());
	}

	//@author A0111942N
	/**
	 * Mainly for testing purpose.
	 */
	public static void main(String[] args) {

		System.out.print("Input: ");
		Scanner scanner = new Scanner(System.in);

		while (scanner.hasNextLine()) {
			Logger logger = Logger.getLogger(LOG_NAME);
			String testInput = scanner.nextLine();

			LogicMain logic = new LogicMain();

			logger.log(Level.INFO, logic.processInput(testInput).toString());
			logger.log(Level.INFO, logic.getAllTasks().toString());
		}

		scanner.close();
	}
}