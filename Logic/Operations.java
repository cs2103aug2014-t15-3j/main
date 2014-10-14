import java.util.LinkedList;

//;author A0111942N

public class Operations {
	
	protected static String OPERATION = ";";
	protected static String ADD_OPERATION = "add";
	protected static String EDIT_OPERATION = "edit";
	protected static String VIEW_OPERATION = "view";
	protected static String DELETE_OPERATION = "delete";
	protected static String SAVE_OPERATION = "save";
	protected static String EMPTY_MESSAGE = "<empty>";
	
	protected LinkedList<String> addOperations;
	protected LinkedList<String> editOperations;
	protected LinkedList<String> viewOperations;
	protected LinkedList<String> deleteOperations;
	protected LinkedList<String> saveOperations;
	protected LinkedList<String> nameOperations;
	protected LinkedList<String> descriptionOperations;
	
	public Operations() {
		addOperations = populateAdd();
		editOperations = populateEdit();
		viewOperations = populateView();
		deleteOperations = populateDelete();
		saveOperations = populateSave();
		nameOperations = populateName();
		descriptionOperations = populateDescription();
	}
	
	public static LinkedList<String> populateAdd() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add(";add");
		operations.add(";+");
		operations.add(";insert");
		
		return operations;
	}
	
	public static LinkedList<String> populateName() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add(";name");
		operations.add(";title");
		
		return operations;
	}
	
	public static LinkedList<String> populateDescription() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add(";description");
		operations.add(";info");
		operations.add(";information");
		operations.add(";i");
		
		return operations;
	}
	
	public static LinkedList<String> populateEdit() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add(";edit");
		operations.add(";update");		
		
		return operations;
	}
	
	public static LinkedList<String> populateView() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add(";view");
		operations.add(";display");		
		
		return operations;
	}
	
	public static LinkedList<String> populateDelete() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add(";delete");
		operations.add(";remove");	
		operations.add(";-");		
		
		return operations;
	}
	
	public static LinkedList<String> populateSave() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add(";save");	
		
		return operations;
	}
}
