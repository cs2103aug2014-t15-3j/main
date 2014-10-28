//@author A0111942N

import java.util.Calendar;
import java.util.LinkedList;

public class Operations {

	// Constant Variables
	protected final static String EMPTY_MESSAGE = "<empty>";
	protected final static String NOT_EMPTY_MESSAGE = "<occupied>";
	protected final static String OPERATION = ";";
	
	// Constant Variables for Task related operations
	protected final static String ADD_OPERATION = "add";
	protected final static String EDIT_OPERATION = "edit";
	protected final static String VIEW_OPERATION = "view";
	protected final static String FIND_OPERATION = "find";
	protected final static String UNDO_OPERATION = "undo";
	protected final static String DELETE_OPERATION = "delete";
	protected final static String SAVE_OPERATION = "save";
	
	protected final static String REMOVE_INPUT = "-";
	
	// Constant Variables for Label related operations
	protected final static String ADD_LABEL_OPERATION = "add_label";
	protected final static String EDIT_LABEL_OPERATION = "edit_label";
	protected final static String VIEW_LABEL_OPERATION = "view_label";
	protected final static String DELETE_LABEL_OPERATION = "delete_label";
	
	// Constant variable for date formating
	protected final static String DATE_INPUT_FORMAT = "d/M/yyyy h:m a";
	protected final static String DATE_OUTPUT_FORMAT = "d MMMM yyyy h:mm a";

	// Lists to contain its respective keywords
	protected static LinkedList<String> labelOperations;
	protected static LinkedList<String> addOperations;
	protected static LinkedList<String> editOperations;
	protected static LinkedList<String> viewOperations;
	protected static LinkedList<String> findOperations;
	protected static LinkedList<String> undoOperations;
	protected static LinkedList<String> deleteOperations;
	protected static LinkedList<String> saveOperations;
	protected static LinkedList<String> nameOperations;
	protected static LinkedList<String> reminderOperations;
	protected static LinkedList<String> colorOperations;
	protected static LinkedList<String> descriptionOperations;
	protected static LinkedList<String> deadlineOperations;

	protected static LinkedList<String> january;
	protected static LinkedList<String> febuary;
	protected static LinkedList<String> march;
	protected static LinkedList<String> april;
	protected static LinkedList<String> may;
	protected static LinkedList<String> june;
	protected static LinkedList<String> july;
	protected static LinkedList<String> august;
	protected static LinkedList<String> september;
	protected static LinkedList<String> october;
	protected static LinkedList<String> november;
	protected static LinkedList<String> december;
	
	// Flag to check if it has been initialized
	protected static boolean isInitialize = false;

	//@author A0111942N
	/**
	 * Constructor:
	 * Populates operation lists with its respective keywords
	 */
	public Operations() {

		if(!isInitialize) {
			labelOperations = populateLabel();
			addOperations = populateAdd();
			editOperations = populateEdit();
			viewOperations = populateView();
			findOperations = populateFind();
			undoOperations = populateUndo();
			deleteOperations = populateDelete();
			saveOperations = populateSave();
			nameOperations = populateName();
			reminderOperations = populateReminder();
			colorOperations = populateColor();
			descriptionOperations = populateDescription();
			deadlineOperations = populateDeadline();
			
			populateMonths();
			
			isInitialize = true;
		}
	}

	//@author A0111942N
	/**
	 * Populate label list with its keywords
	 *
	 * @return List of label keywords
	 */
	private LinkedList<String> populateLabel() {

		LinkedList<String> operations = new LinkedList<>();
		operations.add(";label");
		operations.add(";lb");

		return operations;
	}
	
	//@author A0111942N
	/**
	 * Populate add list with its keywords
	 *
	 * @return List of add keywords
	 */
	private LinkedList<String> populateAdd() {

		LinkedList<String> operations = new LinkedList<>();
		operations.add(";add");
		operations.add(";+");
		operations.add(";insert");

		return operations;
	}
	
	//@author A0111942N
	/**
	 * Populate name list with its keywords
	 *
	 * @return List of name keywords
	 */
	private LinkedList<String> populateName() {

		LinkedList<String> operations = new LinkedList<>();
		operations.add(";name");
		operations.add(";title");

		return operations;
	}

	//@author A0111942N
	/**
	 * Populate label list with its keywords
	 *
	 * @return List of label keywords
	 */
	private LinkedList<String> populateReminder() {

		LinkedList<String> operations = new LinkedList<>();
		operations.add(";remind");
		operations.add(";reminder");
		operations.add(";notify");
		
		return operations;
	}
	
	//@author A0111942N
	/**
	 * Populate color list with its keywords
	 *
	 * @return List of color keywords
	 */
	private LinkedList<String> populateColor() {

		LinkedList<String> operations = new LinkedList<>();
		operations.add(";color");

		return operations;
	}
	
	//@author A0111942N
	/**
	 * Populate description list with its keywords
	 *
	 * @return List of description keywords
	 */
	private LinkedList<String> populateDescription() {

		LinkedList<String> operations = new LinkedList<>();
		operations.add(";description");
		operations.add(";info");
		operations.add(";information");
		operations.add(";i");

		return operations;
	}

	//@author A0111942N
	/**
	 * Populate edit list with its keywords
	 *
	 * @return List of edit keywords
	 */
	private LinkedList<String> populateEdit() {

		LinkedList<String> operations = new LinkedList<>();
		operations.add(";edit");
		operations.add(";update");
		operations.add(";change");
		operations.add(";~");

		return operations;
	}

	//@author A0111942N
	/**
	 * Populate view list with its keywords
	 *
	 * @return List of view keywords
	 */
	private LinkedList<String> populateView() {

		LinkedList<String> operations = new LinkedList<>();
		operations.add(";view");
		operations.add(";v");
		operations.add(";display");
		operations.add(";");	

		return operations;
	}

	//@author A0111942N
	/**
	 * Populate find list with its keywords
	 *
	 * @return List of find keywords
	 */
	private LinkedList<String> populateFind() {

		LinkedList<String> operations = new LinkedList<>();
		operations.add(";find");
		operations.add(";search");
		operations.add(";look");	

		return operations;
	}
	
	//@author A0111942N
	/**
	 * Populate undo list with its keywords
	 *
	 * @return List of undo keywords
	 */
	private LinkedList<String> populateUndo() {

		LinkedList<String> operations = new LinkedList<>();
		operations.add(";undo");
		operations.add(";back");

		return operations;
	}
	
	//@author A0111942N
	/**
	 * Populate delete list with its keywords
	 *
	 * @return List of delete keywords
	 */
	private LinkedList<String> populateDelete() {

		LinkedList<String> operations = new LinkedList<>();
		operations.add(";delete");
		operations.add(";remove");
		operations.add("rm");
		operations.add(";-");		

		return operations;
	}

	//@author A0111942N
	/**
	 * Populate save list with its keywords
	 *
	 * @return List of save keywords
	 */
	private LinkedList<String> populateSave() {

		LinkedList<String> operations = new LinkedList<>();
		operations.add(";save");
		operations.add(";commit");

		return operations;
	}
	
	//@author A0111942N
	/**
	 * Populate deadline list with its keywords
	 *
	 * @return List of deadline keywords
	 */
	private LinkedList<String> populateDeadline() {

		LinkedList<String> operations = new LinkedList<>();
		operations.add(";by");
		operations.add(";on");
		operations.add(";when");
		operations.add(";date");

		return operations;
	}
	

	
	private void populateMonths() {

		january = new LinkedList<String>();
		febuary = new LinkedList<String>();
		march = new LinkedList<String>();
		april = new LinkedList<String>();
		may = new LinkedList<String>();
		june = new LinkedList<String>();
		july = new LinkedList<String>();
		august = new LinkedList<String>();
		september = new LinkedList<String>();
		october = new LinkedList<String>();
		november = new LinkedList<String>();
		december = new LinkedList<String>();
		
		january.add("january");
		january.add("jan");
		january.add("1");
		
		febuary.add("febuary");
		febuary.add("feb");
		febuary.add("2");
		
		march.add("march");
		march.add("mar");
		march.add("3");

		april.add("april");
		april.add("apr");
		april.add("4");
		
		may.add("may");
		may.add("5");
		
		june.add("june");
		june.add("jun");
		june.add("6");
		
		july.add("july");
		july.add("jul");
		july.add("7");
		
		august.add("august");
		august.add("aug");
		august.add("8");
		
		september.add("september");
		september.add("sep");
		september.add("9");
		
		october.add("october");
		october.add("oct");
		october.add("10");
		
		november.add("november");
		november.add("nov");
		november.add("11");
		
		december.add("december");
		december.add("dec");
		december.add("12");
	}
	
	public static int returnMonth(String month) {
		
		month = month.trim().toLowerCase(); // clean up string
		
		if (january.contains(month)) {
			return 1;
		} else if (febuary.contains(month)) {
			return 2;
		} else if (march.contains(month)) {
			return 3;
		} else if (april.contains(month)) {
			return 4;
		} else if (may.contains(month)) {
			return 5;
		} else if (june.contains(month)) {
			return 6;
		} else if (july.contains(month)) {
			return 7;
		} else if (august.contains(month)) {
			return 8;
		} else if (september.contains(month)) {
			return 9;
		} else if (october.contains(month)) {
			return 10;
		} else if (november.contains(month)) {
			return 11;
		} else if (december.contains(month)) {
			return 12;
		} else {
			Calendar tempCal = Calendar.getInstance();
			return tempCal.get(Calendar.MONTH+1);
		}
		
	}
}
