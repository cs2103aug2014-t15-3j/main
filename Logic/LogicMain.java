//@author A0111942N

import java.util.LinkedList;
import java.util.Scanner;

public class LogicMain {
	
	private static boolean isInitialize = false;
	
	private static StorageMain storageMain;
	
	private static LinkedList<LogicInputPair> inputList;
	private static LinkedList<Task> bufferList = new LinkedList<Task>();
	
	private static LinkedList<String> addOperations = OperationsConstant.getAddOperations();
	private static LinkedList<String> nameOperations = OperationsConstant.getNameOperations();
	private static LinkedList<String> descriptionOperations = OperationsConstant.getDescriptionOperations();
	private static LinkedList<String> editOperations = OperationsConstant.getEditOperations();
	private static LinkedList<String> viewOperations = OperationsConstant.getViewOperations();
	private static LinkedList<String> deleteOperations = OperationsConstant.getDeleteOperations();
	private static LinkedList<String> saveOperations = OperationsConstant.getSaveOperations();
	
	private static String[] inputArray;
	
	private static String MESSAGE_OPERATION_NOT_FOUND = "Remembra doesn't understand your input. Please try again..";
	
	public static LinkedList<Task> processInput(String input) {
		
		initialize();
		
		LinkedList<Task> packageTasks = new LinkedList<Task>();
		input = cleanUpInput(input);
		preProcessInput(input);
		
		String mainOperation = inputList.get(0).getOperation();
				
		if( addOperations.contains(mainOperation) ) {
			
			Task returnTask = new Task( executeAdd() );
			returnTask.editState(OperationsConstant.ADD_OPERATION);
			packageTasks.add(returnTask);
			return packageTasks;
		}
		else if( editOperations.contains(mainOperation) ) {
			
			Task returnTask = new Task( executeEdit() );
			returnTask.editState(OperationsConstant.EDIT_OPERATION);
			packageTasks.add(returnTask);
			return packageTasks;
		}
		else if( viewOperations.contains(mainOperation) ) {
			
			packageTasks = new LinkedList<Task>(bufferList);
			
			Task returnTask = new Task( packageTasks.get(0) );
			returnTask.editState(OperationsConstant.VIEW_OPERATION);
			
			packageTasks.set(0, returnTask);
			
			return packageTasks;
		}
		else if( deleteOperations.contains(mainOperation) ) {
			
			packageTasks.add(executeDelete());
			return packageTasks;
		}
		else if( saveOperations.contains(mainOperation) ) {
			
			commitToStorage();
			return bufferList;
		}
		else {
			return packageTasks;
		}
	}
	
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
	
	private static String cleanUpInput(String input) {
		return input.trim();
	}
	
	private static void preProcessInput(String input) {
		
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
	
	private static Task executeAdd() {
		
		String name = "";
		String description = "";
		
		for(int i=0; i<inputList.size(); i++) {
			
			String operation = inputList.get(i).getOperation();
			
			if( addOperations.contains(operation) ) {
				
				name = inputList.get(i).getContent();
				
				if(name.isEmpty()) {
					return null;
				}
			}
			else if( descriptionOperations.contains(operation) ) {
				
				description = inputList.get(i).getContent();
			}
		}
		
		Task newTask = new Task(name,description);
		bufferList.add(newTask);
		return newTask;
	}
	
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
			return null;
		}
	}
	
	private static Task executeEdit() {
		
		int editID = -1;
		String name = "";
		String description = "";
		boolean nameEdited = false;
		boolean descriptionEdited = false;
		
		for(int i=0; i<inputList.size(); i++) {
			
			String operation = inputList.get(i).getOperation();
			
			if( editOperations.contains(operation) ) {
				
				String stringID = inputList.get(i).getContent();
				
				if(stringID.isEmpty()) {
					return null;
				}
				
				editID = Integer.parseInt(stringID);
				
				if(editID >= bufferList.size()) {
					return null;
				}
			}
			else if( nameOperations.contains(operation) ) {
				
				name = inputList.get(i).getContent();
				
				if(name.isEmpty()) {
					return null;
				}
				
				nameEdited = true;
			}
			else if( descriptionOperations.contains(operation) ) {
				
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
	
	private static boolean isOperation(String word) {
		
		if(word.startsWith("@!")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private static boolean commitToStorage() {
		
		storageMain.storeObject(bufferList);
		return true;
	}
	
	//Mainly for testing purposes only
	public static void printBufferList() {
		System.out.println(LogicMain.bufferList.toString());
	}
	
	//Mainly for testing purposes only
	public static void main(String[] args) {
		
		System.out.print("Input: ");
		Scanner scanner = new Scanner(System.in);
		
		while(scanner.hasNextLine()) {
			
			System.out.println(LogicMain.processInput(scanner.nextLine()));
			System.out.println(LogicMain.bufferList.toString());
		}
		
		scanner.close();
	}
}
