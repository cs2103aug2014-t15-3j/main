import java.util.LinkedList;

//@author A0111942N

public class OperationsConstant {
	
	public static String ADD_OPERATION = "add";
	public static String EDIT_OPERATION = "edit";
	public static String VIEW_OPERATION = "view";
	public static String DELETE_OPERATION = "delete";
	
	public static LinkedList<String> getAddOperations() {
		
		LinkedList<String> addOperations = new LinkedList<>();
		addOperations.add("@add");
		addOperations.add("@+");
		addOperations.add("@insert");
		
		return addOperations;
	}
	
	public static LinkedList<String> getEditOperations() {
		
		LinkedList<String> editOperations = new LinkedList<>();
		editOperations.add("@edit");
		editOperations.add("@update");		
		
		return editOperations;
	}
	
	public static LinkedList<String> getViewOperations() {
		
		LinkedList<String> viewOperations = new LinkedList<>();
		viewOperations.add("@view");
		viewOperations.add("@display");		
		
		return viewOperations;
	}
	
	public static LinkedList<String> getDeleteOperations() {
		
		LinkedList<String> deleteOperations = new LinkedList<>();
		deleteOperations.add("@delete");
		deleteOperations.add("@remove");		
		
		return deleteOperations;
	}
	
	
}
