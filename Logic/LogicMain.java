//@author A0111942N

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeMap;

public class LogicMain {
	
	private static LinkedList<LogicInputPair> inputList;
	private static LinkedList<Task> bufferList = new LinkedList<Task>();
	
	private static LinkedList<String> addOperations = OperationsConstant.getAddOperations();
	private static LinkedList<String> descriptionOperations = OperationsConstant.getDescriptionOperations();
	private static LinkedList<String> editOperations = OperationsConstant.getEditOperations();
	private static LinkedList<String> viewOperations = OperationsConstant.getViewOperations();
	private static LinkedList<String> deleteOperations = OperationsConstant.getDeleteOperations();
	private static LinkedList<String> saveOperations = OperationsConstant.getSaveOperations();
	
	private static String[] inputArray;
	
	private static String MESSAGE_OPERATION_NOT_FOUND = "Remembra doesn't understand your input. Please try again..";

	public static String processInput(String input) {
		
		input = cleanUpInput(input);
		preProcessInput(input);
		
		String mainOperation = inputList.get(0).getOperation();
		
		if( addOperations.contains(mainOperation) ) {
			
			if(executeAdd()) {
				return OperationsConstant.ADD_OPERATION;
			}
			else {
				return MESSAGE_OPERATION_NOT_FOUND;
			}
			
		}
		else if( editOperations.contains(mainOperation) ) {
			
			return OperationsConstant.EDIT_OPERATION;
		}
		else if( viewOperations.contains(mainOperation) ) {
			
			return OperationsConstant.VIEW_OPERATION;
		}
		else if( deleteOperations.contains(mainOperation) ) {
			
			return OperationsConstant.DELETE_OPERATION;
		}
		else if( saveOperations.contains(mainOperation) ) {
			
			commitToStorage();
			return OperationsConstant.SAVE_OPERATION;
		}
		else {
			return MESSAGE_OPERATION_NOT_FOUND;
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
	
	private static boolean executeAdd() {
		
		String name = "";
		String description = "";
		
		for(int i=0; i<inputList.size(); i++) {
			
			String operation = inputList.get(i).getOperation();
			
			if( addOperations.contains(operation) ) {
				
				name = inputList.get(i).getContent();
				
				if(name.isEmpty()) {
					return false;
				}
			}
			else if( descriptionOperations.contains(operation) ) {
				
				description = inputList.get(i).getContent();
			}
		}
		
		bufferList.add( new Task(name,description) );
		return true;
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
		
		StorageMain storageMain = new StorageMain();
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
