import java.text.SimpleDateFormat;
import java.util.LinkedList;

//;author A0111942N

/*********************************************************************/
/******************* QA I - Refactor Code I***************************/
/*********************************************************************/
// @Sam - Operations.java
//
// 1. Would it be possible to add in some comments?
//
// @Sam - Operations.java
/*********************************************************************/
/*********************************************************************/

public class Operations {
	
	protected final static String OPERATION = ";";
	protected final static String ADD_OPERATION = "add";
	protected final static String EDIT_OPERATION = "edit";
	protected final static String VIEW_OPERATION = "view";
	protected final static String DELETE_OPERATION = "delete";
	protected final static String SAVE_OPERATION = "save";
	protected final static String EMPTY_MESSAGE = "<empty>";
	protected final static String DATE_INPUT_FORMAT = "d/M/yyyy h:m a";
	protected final static String DATE_OUTPUT_FORMAT = "d/M/yyyy h:mm a";
		
	protected static LinkedList<String> addOperations;
	protected static LinkedList<String> editOperations;
	protected static LinkedList<String> viewOperations;
	protected static LinkedList<String> deleteOperations;
	protected static LinkedList<String> saveOperations;
	protected static LinkedList<String> nameOperations;
	protected static LinkedList<String> descriptionOperations;
	protected static LinkedList<String> deadlineOperations;
	
	protected static boolean isInitialize = false;
	
	public Operations() {
		
		if(!isInitialize) {
			addOperations = populateAdd();
			editOperations = populateEdit();
			viewOperations = populateView();
			deleteOperations = populateDelete();
			saveOperations = populateSave();
			nameOperations = populateName();
			descriptionOperations = populateDescription();
			deadlineOperations = populateDeadline();
						
			isInitialize = true;
		}
	}
	
	private LinkedList<String> populateAdd() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add(";add");
		operations.add(";+");
		operations.add(";insert");
		
		return operations;
	}
	
	private LinkedList<String> populateName() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add(";name");
		operations.add(";title");
		
		return operations;
	}
	
	private LinkedList<String> populateDescription() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add(";description");
		operations.add(";info");
		operations.add(";information");
		operations.add(";i");
		
		return operations;
	}
	
	private LinkedList<String> populateEdit() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add(";edit");
		operations.add(";update");		
		
		return operations;
	}
	
	private LinkedList<String> populateView() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add(";view");
		operations.add(";display");		
		
		return operations;
	}
	
	private LinkedList<String> populateDelete() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add(";delete");
		operations.add(";remove");	
		operations.add(";-");		
		
		return operations;
	}
	
	private LinkedList<String> populateSave() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add(";save");	
		
		return operations;
	}
	
	private LinkedList<String> populateDeadline() {
		
		LinkedList<String> operations = new LinkedList<>();
		operations.add(";by");
		operations.add(";on");
		operations.add(";when");
		
		return operations;
	}
}
