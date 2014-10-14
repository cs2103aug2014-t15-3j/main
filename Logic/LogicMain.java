//@author A0111942N

import java.util.LinkedList;
import java.util.Scanner;

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
// 3. Do check code structure for if/else,
//
//	  Example of correct implementation:
//		
//		if(){                  if(){
//		}else if(){     or     }else{ 
//		}                      }
//	
// @Sam - LogicMain.java
/*********************************************************************/
/*********************************************************************/


public class LogicMain {
	
	//Use to check if the program has initialized.
	private static boolean isInitialize = false;
	
	Operations operations;
	
	//Use to connect with StorageMain
	private static StorageMain storageMain;
	
	//Data structures
	private static LinkedList<LogicInputPair> inputList;
	private static LinkedList<Task> bufferList = new LinkedList<Task>();
	private static String[] inputArray;
	
	public LogicMain() {
		
		operations = new Operations();
	}
	
	//@author A0111942N
	//API: This method will process the user's input and perform either
	//add, edit, view, delete or save based on the input.
	public LinkedList<Task> processInput(String input) {
		
		initialize();
		
		LinkedList<Task> packageTasks = new LinkedList<Task>();
		input = cleanUpInput(input);
		preProcessInput(input);
		
		String mainOperation = inputList.get(0).getOperation();
				
		if( operations.addOperations.contains(mainOperation) ) {
			
			Task returnTask = new Task( executeAdd() );
			returnTask.editState(Operations.ADD_OPERATION);
			packageTasks.add(returnTask);
			return packageTasks;
		}
		else if( operations.editOperations.contains(mainOperation) ) {
			
			Task returnTask = new Task( executeEdit() );
			returnTask.editState(Operations.EDIT_OPERATION);
			packageTasks.add(returnTask);
			return packageTasks;
		}
		else if( operations.viewOperations.contains(mainOperation) ) {
			
			packageTasks = new LinkedList<Task>(bufferList);
			
			Task returnTask;
			
			if(bufferList.size() != 0) {
				returnTask = new Task( packageTasks.get(0) );
				returnTask.editState(Operations.VIEW_OPERATION);
				packageTasks.set(0, returnTask);
			}
			else
			{
				returnTask = new Task(Operations.EMPTY_MESSAGE);
				returnTask.editState(Operations.VIEW_OPERATION);
				packageTasks.add(returnTask);
			}
			
			return packageTasks;
		}
		else if( operations.deleteOperations.contains(mainOperation) ) {
			
			packageTasks.add(executeDelete());
			
			return packageTasks;
		}
		else if( operations.saveOperations.contains(mainOperation) ) {
			
			commitToStorage();
			packageTasks = new LinkedList<Task>();
			Task saveTask = new Task(Operations.EMPTY_MESSAGE);
			saveTask.editState(Operations.SAVE_OPERATION);
			packageTasks.add(saveTask);
			return packageTasks;
		}
		else {
			return packageTasks;
		}
	}
	
	//@author A0111942N
	//This method checks if the component has been initialized.
	//It will initialize the component if it is not. 
	private static void initialize() {
		
		if(!isInitialize) {
			storageMain = new StorageMain();
			
			Object retrievedObject = storageMain.retrieveObject(bufferList);
			
			if( retrievedObject instanceof LinkedList<?> ) {
				bufferList = (LinkedList<Task>) retrievedObject;
			}
			else {
				bufferList = new LinkedList<Task>();
			}
			
			isInitialize = true;
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
			}
			else {
				
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
		
		for(int i=0; i<inputList.size(); i++) {
			
			String operation = inputList.get(i).getOperation();
			
			if( operations.addOperations.contains(operation) ) {
				
				name = inputList.get(i).getContent();
				
				if(name.isEmpty()) {
					return null;
				}
			}
			else if( operations.descriptionOperations.contains(operation) ) {
				
				description = inputList.get(i).getContent();
			}
		}
		
		Task newTask = new Task(name,description);
		bufferList.add(newTask);
		return newTask;
	}
	
	//@author A0111942N
	//This method executes the "delete" functionality of the program
	private static Task executeDelete() {
		
		int deleteID = 0;
		String content = inputList.get(0).getContent();
		
		if(!content.isEmpty()) {
			deleteID = Integer.parseInt(inputList.get(0).getContent());
		}
		
		if(deleteID < bufferList.size()) {
			Task deleteTask = bufferList.get(deleteID);
			bufferList.remove(deleteID);
			return deleteTask;
		}
		else {
			Task deleteTask = new Task(Operations.EMPTY_MESSAGE);
			deleteTask.editState(Operations.DELETE_OPERATION);
			return deleteTask;
		}
	}
	
	//@author A0111942N
	//This method executes the "edit" functionality of the program
	private Task executeEdit() {
		
		int editID = -1;
		String name = "";
		String description = "";
		boolean nameEdited = false;
		boolean descriptionEdited = false;
		
		for(int i=0; i<inputList.size(); i++) {
			
			String operation = inputList.get(i).getOperation();
			
			if( operations.editOperations.contains(operation) ) {
				
				String stringID = inputList.get(i).getContent();
				
				if(stringID.isEmpty()) {
					return null;
				}
				
				editID = Integer.parseInt(stringID);
				
				if(editID >= bufferList.size()) {
					return null;
				}
			}
			else if( operations.nameOperations.contains(operation) ) {
				
				name = inputList.get(i).getContent();
				
				if(name.isEmpty()) {
					return null;
				}
				
				nameEdited = true;
			}
			else if( operations.descriptionOperations.contains(operation) ) {
				
				description = inputList.get(i).getContent();
				descriptionEdited = true;
			}
		}
		
		Task editTask = bufferList.get(editID);
		
		if(nameEdited) {
			editTask.editName(name);
		}
		if(descriptionEdited) {
			editTask.editDescription(description);
		}
		return editTask;
	}
	
	//@author A0111942N
	//This method checks if the input word is an operation.
	private boolean isOperation(String word) {
		
		if(word.startsWith(Operations.OPERATION)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//@author A0111942N
	//This method will contact StorageMain to export the bufferList
	//into a text file.
	private boolean commitToStorage() {
		
		storageMain.storeObject(bufferList);
		return true;
	}
	
	//@author A0111942N
	//API: This method returns the list of all the tasks.
	public LinkedList<Task> getAllTasks() {
		return bufferList;
	}
	
	//Mainly for testing purposes only
	public void printBufferList() {
		System.out.println(LogicMain.bufferList.toString());
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
