//@author A0111942N

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
}
