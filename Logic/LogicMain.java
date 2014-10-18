//@author A0111942N

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*********************************************************************/
/******************* QA I - Refactor Code I***************************/
/*********************************************************************/
// @Sam - LogicMain.java
//
// 1. Would it be possible if you could alter your comments of the
//    functions to the java comments structure given in the java
//	  coding standards?
//
// 2. Do edit your code structure for certain areas as mentioned
//    in the coding standards, to group related codes together
//
//	  For example:
//		
//		//Object creation (this heading is for explanation not part of the code)
//		LinkedList<Task> packageTasks = new LinkedList<Task>();
//		
//		//function calls (this heading is for explanation not part of the code)		//
//		input = cleanUpInput(input);
//		preProcessInput(input);
//
//    Another example: //Seperate by Object Creation, function call, return type
//
//		Task returnTask = new Task( executeEdit() );
//
//		returnTask.editState(Operations.EDIT_OPERATION);
//		packageTasks.add(returnTask);
//		
//		return packageTasks;
//	
// @Sam - LogicMain.java
/*********************************************************************/
/*********************************************************************/


public class LogicMain {

	//Constant
	private final static String LOG_NAME = "LogicMain";

	//Use to check if the program has initialized.
	private static boolean isInitialize = false;

	//Operation Object
	private Operations operations;

	//Use to connect with StorageMain
	private static StorageMain storageMain;

	//Data structures
	private LinkedList<LogicInputPair> inputList;
	private static LinkedList<Task> bufferList = new LinkedList<Task>();
	private String[] inputArray;

	//Logger: Use to troubleshoot problems
	private Logger logger = Logger.getLogger(LOG_NAME);

	public LogicMain() {

		initialize();
	}

	//@author A0111942N
	//API: This method will process the user's input and perform either
	//add, edit, view, delete or save based on the input.
	public LinkedList<Task> processInput(String input) {

		input = cleanUpInput(input);
		preProcessInput(input);

		String mainOperation = inputList.get(0).getOperation();

		if( Operations.addOperations.contains(mainOperation) ) {

			return postAdd();
		} else if( Operations.editOperations.contains(mainOperation) ) {

			return postEdit();
		} else if( Operations.viewOperations.contains(mainOperation) ) {

			return postView();
		} else if( Operations.deleteOperations.contains(mainOperation) ) {

			return postDelete();
		} else if( Operations.saveOperations.contains(mainOperation) ) {

			return postSave();
		} else {

			return new LinkedList<Task>();
		}
	}

	//@author A0111942N
	//This method checks if the component has been initialized.
	//It will initialize the component if it is not. 
	private void initialize() {

		if(!isInitialize) {
			operations = new Operations();
			storageMain = new StorageMain();

			Object retrievedObject = storageMain.retrieveObject(StorageMain.OBJ_TYPES.TYPE_TASK); //candiie:changes

			if( retrievedObject instanceof LinkedList<?> ) {
				bufferList = (LinkedList<Task>) retrievedObject;
			} else {
				bufferList = new LinkedList<Task>();
				logger.log(Level.WARNING, "Unable to retrieve tasks from storage");
			}

			isInitialize = true;

			logger.log(Level.INFO, "Initializing is complete");
		} else {
			logger.log(Level.INFO, "LogicMain has already been initiated");
		}
	}

	//@author A0111942N
	//This method cleans up the input string by removing
	//empty spaces and breaks at the front and back of it.
	private static String cleanUpInput(String input) {
		return input.trim();
	}

	//@author A0111942N
	//This method will pre-process the user's input and categories
	//them into a list.
	private void preProcessInput(String input) {

		inputList = new LinkedList<LogicInputPair>();

		String operation = "";
		String content = "";

		inputArray = input.split(" ");

		for(int i=0; i<inputArray.length; i++) {

			if(isOperation(inputArray[i])) {

				if(!operation.isEmpty()) {
					inputList.add( new LogicInputPair(operation, content) );
				}

				if(i==inputArray.length-1) {
					inputList.add( new LogicInputPair(inputArray[i],"") );
				}
				operation = inputArray[i].toLowerCase();
				content = "";
			} else {

				if(!content.isEmpty()) {
					content += " ";
				}
				content += inputArray[i];

				if(i==inputArray.length-1) {
					inputList.add( new LogicInputPair(operation, content) );
				}
			}
		}
	}

	//@author A0111942N
	//This method executes the "add" functionality of the program
	private Task executeAdd() {

		String name = "";
		String description = "";
		long deadline = 0;

		for(int i=0; i<inputList.size(); i++) {

			String operation = inputList.get(i).getOperation();

			if( Operations.addOperations.contains(operation) ) {

				name = inputList.get(i).getContent();

				if(name.isEmpty()) {

					logger.log(Level.INFO, "Add operation: Name unidentified");
					return null;
				}
			} else if( Operations.descriptionOperations.contains(operation) ) {

				description = inputList.get(i).getContent();
			} else if( Operations.deadlineOperations.contains(operation) ) {
								
				try {
					String dateInput = inputList.get(i).getContent();
					dateInput = dateInput.toUpperCase();
					
					SimpleDateFormat format
						= new SimpleDateFormat(Operations.DATE_INPUT_FORMAT);
					
					Date date = format.parse(dateInput);
					deadline = date.getTime();
				} catch (ParseException e) {
					logger.log(Level.WARNING, "Wrong date format!");
				}
			}
		}

		Task newTask = new Task(name,description);
		
		if(deadline!=-1) {
			newTask.editDeadline(deadline);
		}
		
		bufferList.add(newTask);

		logger.log(Level.INFO, "New task added to bufferlist");
		return newTask;
	}

	//@author A0111942N
	//This method executes the "delete" functionality of the program
	private Task executeDelete() {

		int deleteID = 0;
		String content = inputList.get(0).getContent();

		if(!content.isEmpty()) {
			deleteID = Integer.parseInt(inputList.get(0).getContent());
		}

		if(deleteID < bufferList.size()) {
			Task deleteTask = bufferList.get(deleteID);
			bufferList.remove(deleteID);

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
	//This method executes the "edit" functionality of the program
	private Task executeEdit() {

		int editID = -1;
		String name = "";
		String description = "";
		long deadline = -1;
		boolean nameEdited = false;
		boolean descriptionEdited = false;
		boolean deadlineEdited = false;

		for(int i=0; i<inputList.size(); i++) {

			String operation = inputList.get(i).getOperation();

			if( Operations.editOperations.contains(operation) ) {

				String stringID = inputList.get(i).getContent();

				if(stringID.isEmpty()) {
					return null;
				}

				editID = Integer.parseInt(stringID);

				if(editID >= bufferList.size()) {
					return null;
				}
			} else if( Operations.nameOperations.contains(operation) ) {

				name = inputList.get(i).getContent();

				if(name.isEmpty()) {

					logger.log(Level.INFO, "Edit operation: Invalid name");
					return null;
				}

				nameEdited = true;
			} else if( Operations.descriptionOperations.contains(operation) ) {

				description = inputList.get(i).getContent();
				descriptionEdited = true;
			} else if( Operations.deadlineOperations.contains(operation) ) {

				try {
					String dateInput = inputList.get(i).getContent();
					dateInput = dateInput.toUpperCase();

					SimpleDateFormat format
					= new SimpleDateFormat(Operations.DATE_INPUT_FORMAT);

					Date date = format.parse(dateInput);
					deadline = date.getTime();
					
					deadlineEdited = true;
				} catch (ParseException e) {
					logger.log(Level.WARNING, "Wrong date format!");
				}
			}
		}

		Task editTask = bufferList.get(editID);

		if(nameEdited) {
			editTask.editName(name);
		}
		if(descriptionEdited) {
			editTask.editDescription(description);
		}
		if(deadlineEdited) {
			editTask.editDeadline(deadline);
		}


		logger.log(Level.INFO, "Task edited");
		return editTask;
	}

	//@author A0111942N
	//This method checks if the input word is an operation.
	private boolean isOperation(String word) {

		if(word.startsWith(Operations.OPERATION)) {
			return true;
		} else {
			return false;
		}
	}

	//@author A0111942N
	//This method will contact StorageMain to export the bufferList
	//into a text file.
	private boolean commitToStorage() {

		storageMain.storeObject(StorageMain.OBJ_TYPES.TYPE_TASK,bufferList); //candiie:changes
		return true;
	}

	//@author A0111942N
	//API: This method returns the list of all the tasks.
	public LinkedList<Task> getAllTasks() {
		return bufferList;
	}

	private LinkedList<Task> postAdd() {

		LinkedList<Task> packageTasks = new LinkedList<Task>();

		Task addTask = executeAdd();

		if(addTask != null) {
			Task returnTask = new Task( addTask );
			returnTask.editState(Operations.ADD_OPERATION);
			packageTasks.add(returnTask);
		}

		logger.log(Level.INFO, "Add operation completed");

		return packageTasks;
	}

	private LinkedList<Task> postEdit() {

		LinkedList<Task> packageTasks = new LinkedList<Task>();
		Task returnTask = new Task( executeEdit() );
		returnTask.editState(Operations.EDIT_OPERATION);
		packageTasks.add(returnTask);

		logger.log(Level.INFO, "Edit operation completed");

		return packageTasks;
	}

	private LinkedList<Task> postView() {

		LinkedList<Task> packageTasks = new LinkedList<Task>();
		packageTasks = new LinkedList<Task>(bufferList);

		Task returnTask;

		if(bufferList.size() != 0) {
			returnTask = new Task( packageTasks.get(0) );
			returnTask.editState(Operations.VIEW_OPERATION);
			packageTasks.set(0, returnTask);
		} else {
			returnTask = new Task(Operations.EMPTY_MESSAGE);
			returnTask.editState(Operations.VIEW_OPERATION);
			packageTasks.add(returnTask);
		}

		logger.log(Level.INFO, "View operation completed");

		return packageTasks;
	}

	private LinkedList<Task> postDelete() {

		LinkedList<Task> packageTasks = new LinkedList<Task>();
		packageTasks.add(executeDelete());

		logger.log(Level.INFO, "Delete operation completed");

		return packageTasks;
	}

	private LinkedList<Task> postSave() {

		LinkedList<Task> packageTasks = new LinkedList<Task>();
		commitToStorage();
		packageTasks = new LinkedList<Task>();
		Task saveTask = new Task(Operations.EMPTY_MESSAGE);
		saveTask.editState(Operations.SAVE_OPERATION);
		packageTasks.add(saveTask);

		logger.log(Level.INFO, "Save operation completed");

		return packageTasks;
	}

	//Mainly for testing purposes only
	public void printBufferList() {
		System.out.println(bufferList.toString());
	}

	//Mainly for testing purposes only
	public static void main(String[] args) {

		System.out.print("Input: ");
		Scanner scanner = new Scanner(System.in);

		while(scanner.hasNextLine()) {

			String testInput = scanner.nextLine();

			LogicMain logic = new LogicMain();

			System.out.println(logic.processInput(testInput));
			System.out.println(logic.getAllTasks());
		}

		scanner.close();
	}
}
