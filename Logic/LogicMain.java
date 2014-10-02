import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author Samuel Lim Yi Jie
 *
 */

public class LogicMain {
	
	private static LinkedList<Task> bufferList = new LinkedList<Task>();
	
	private static String MESSAGE_OPERATION_NOT_FOUND = "Remembra doesn't understand.";

	public static String processInput(String input) {
		
		input = input.trim();
		String[] inputArray = input.split(" ");
		String operation = inputArray[0];
		
		LinkedList<String> addOperations = OperationsConstant.getAddOperations();
		LinkedList<String> editOperations = OperationsConstant.getEditOperations();
		LinkedList<String> viewOperations = OperationsConstant.getViewOperations();
		LinkedList<String> deleteOperations = OperationsConstant.getDeleteOperations();
		
		if( addOperations.contains(operation) ) {
			
			bufferList.add(new Task(inputArray[1],"off"));
			return OperationsConstant.ADD_OPERATION;
		}
		else if( editOperations.contains(operation) ) {
			
			commitToStorage();
			
			return OperationsConstant.EDIT_OPERATION;
		}
		else if( viewOperations.contains(operation) ) {
			
			return OperationsConstant.VIEW_OPERATION;
		}
		else if( deleteOperations.contains(operation) ) {
			
			return OperationsConstant.DELETE_OPERATION;
		}
		else {
			return MESSAGE_OPERATION_NOT_FOUND;
		}
	}
	
	private static boolean commitToStorage() {
		
		//return Storage.commit(bufferList);
		return true;
	}
	
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
