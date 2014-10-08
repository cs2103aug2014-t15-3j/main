import java.util.LinkedList;

//@!author A0111942N

public class OperationsConstant {
	
	public static String ADD_OPERATION = "add";
	public static String EDIT_OPERATION = "edit";
	public static String VIEW_OPERATION = "view";
	public static String DELETE_OPERATION = "delete";
	public static String SAVE_OPERATION = "save";
	public static String EMPTY_MESSAGE = "<empty>";
	
	public static LinkedList<String> getAddOperations() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add("@!add");
		operations.add("@!+");
		operations.add("@!insert");
		
		return operations;
	}
	
	public static LinkedList<String> getNameOperations() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add("@!name");
		operations.add("@!title");
		
		return operations;
	}
	
	public static LinkedList<String> getDescriptionOperations() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add("@!description");
		operations.add("@!info");
		operations.add("@!information");
		operations.add("@!i");
		
		return operations;
	}
	
	public static LinkedList<String> getEditOperations() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add("@!edit");
		operations.add("@!update");		
		
		return operations;
	}
	
	public static LinkedList<String> getViewOperations() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add("@!view");
		operations.add("@!display");		
		
		return operations;
	}
	
	public static LinkedList<String> getDeleteOperations() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add("@!delete");
		operations.add("@!remove");	
		operations.add("@!-");		
		
		return operations;
	}
	
	public static LinkedList<String> getSaveOperations() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add("@!save");	
		
		return operations;
	}
}
