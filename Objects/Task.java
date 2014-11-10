import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

//@author A0111942N

public class Task implements Item, java.io.Serializable, Comparable<Task> {

	private final static String TO_DO_LABEL = "To-Do";
	private final static int NOT_VALID = -1;
	private final static long DAY_MILLISECOND = 86400000;

	private final static int NAME_MAX_LENGTH = 30;
	private final static int DESCRIPTION_MAX_LENGTH = 80;
	
	private final static SimpleDateFormat dateFormat
				= new SimpleDateFormat(Operations.DATE_OUTPUT_FORMAT);

	// Class variable
	private String name;
	private String description;
	private long label;
	private long timeStamp;
	private long start;
	private long end;
	private long deadline;
	private long reminder;
	private String state;
	private boolean hasReminder = false;
	private boolean isReminded = false; 
	private boolean isOverdue = false;
	private boolean isDone = false;
	
	//@author A0111942N
	/**
	 * Constructor:
	 * When specified with task's name only
	 * 
	 * @param name	Task's name
	 */
	public Task(String name) {
		this(name, "", NOT_VALID, NOT_VALID, NOT_VALID);
	}

	//@author A0111942N
	/**
	 * Constructor:
	 * When specified with task's name and description only
	 * 
	 * @param name			Task's name
	 * @param description	Task's description
	 */
	public Task(String name, String description) {
		this(name, description, NOT_VALID, NOT_VALID, NOT_VALID);
	}

	//@author A0111942N
	/**
	 * Constructor:
	 * When specified with another task object
	 * 
	 * @param oldTask	Another task
	 */
	public Task(Task oldTask) {

		this.name = oldTask.getName();
		this.description = oldTask.getDescription();
		this.label = oldTask.getLabel();
		this.timeStamp = oldTask.getTimeStamp();
		this.start = oldTask.getStart();
		this.end = oldTask.getEnd();
		this.deadline = oldTask.getDeadline();
		this.reminder = oldTask.getReminder();
		this.state = "";
		this.isDone = oldTask.getDone();
	}
	
	//@author A0111942N
	/**
	 * Constructor:
	 * When specified with task's name and description only
	 * 
	 * @param name			Task's name
	 * @param description	Task's description
	 * @param label			Task's label
	 * @param deadline		Task's deadline
	 */
	public Task(String name, String description, long label, long deadline, long reminder) {

		this.name = shortenText(name,NAME_MAX_LENGTH);
		this.description = shortenText(description,DESCRIPTION_MAX_LENGTH);
		this.label = label;
		this.timeStamp = System.currentTimeMillis();
		this.deadline = deadline;
		this.reminder = reminder;
		this.state = "";
		this.isDone = false;
	}
	
	//@author A0111942N
	/**
	 * Accessor: Get task's name
	 * 
	 * @return	Task's name
	 */
	public String getName() {
		return name;
	}
	
	//@author A0111942N
	/**
	 * Accessor: Get task's description
	 * 
	 * @return	Task's description
	 */
	public String getDescription() {
		return description;
	}
	
	//@author A0111942N
	/**
	 * Accessor: Get task's label
	 * 
	 * @return	Task's label
	 */
	public long getLabel() {
		return label;
	}
	
	//@author A0111942N
	/**
	 * Accessor: Get task's time stamp (ID)
	 * 
	 * @return	Task's time stamp (ID)
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	//@author A0111942N
	/**
	 * Accessor: Get task's state
	 * 
	 * @return	Task's state
	 */
	public String getState() {
		return state;
	}
	
	//@author A0111942N
	/**
	 * Accessor: Get task's label name
	 * 
	 * @return	Task's reminder (millisecond)
	 */
	public String getLabelName() {

		String labelName = getLabelName(label);

		return labelName;
	}

	//@author A0111942N
	/**
	 * Get label name by supplying its label's id
	 * 
	 * @return	Label name
	 */
	private String getLabelName(long _label) {
		LinkedList<Label> bufferLabelsList = LogicMain.getAllLabels();

		for (int j = 0; j < bufferLabelsList.size(); j++) {

			if (bufferLabelsList.get(j).getTimeStamp() == _label) {
				Label tempLabel = bufferLabelsList.get(j);
				return tempLabel.getName();
			}
		}

		return Operations.EMPTY_MESSAGE;
	}
	
	//@author A0111942N
	/**
	 * Accessor: Get task's done status
	 * 
	 * @return	Task's done status
	 */
	public boolean getDone() {
		return isDone;
	}

	
	//@author A0111942N
	/**
	 * Accessor: Get task's start time (millisecond)
	 * 
	 * @return	Task's start time (millisecond)
	 */
	public long getStart() {
		return start;
	}
	
	//@author A0111942N
	/**
	 * Accessor: Get task's end time (millisecond)
	 * 
	 * @return	Task's end time (millisecond)
	 */
	public long getEnd() {
		return end;
	}
	
	//@author A0111942N
	/**
	 * Accessor: Get task's deadline (millisecond)
	 * 
	 * @return	Task's deadline (millisecond)
	 */
	public long getDeadline() {
		return deadline;
	}
	
	//@author A0111942N
	/**
	 * Accessor: Get task's reminder (millisecond)
	 * 
	 * @return	Task's reminder (millisecond)
	 */
	public long getReminder() {
		return reminder;
	}
	
	//@author A0111942N
	/**
	 * Accessor: Get task's start time (date format)
	 * 
	 * @return	Task's start time (date format)
	 */
	public String getFormattedStart() {

		return getFormattedDate(start);
	}
	
	//@author A0111942N
	/**
	 * Accessor: Get task's end time (date format)
	 * 
	 * @return	Task's end time (date format)
	 */
	public String getFormattedEnd() {

		return getFormattedDate(end);
	}
	
	//@author A0111942N
	/**
	 * Accessor: Get task's deadline (date format)
	 * 
	 * @return	Task's deadline (date format)
	 */
	public String getFormattedDeadline() {
		
		String dateString = getFormattedDate(deadline);
		
		if (deadline != NOT_VALID) {
			dateString += getOverdue(deadline);
		}
		
		return dateString;
	}
	
	//@author A0111942N
	/**
	 * Accessor: Get task's reminder (date format)
	 * 
	 * @return	Task's reminder (date format)
	 */
	public String getFormattedReminder() {

		String dateString = getFormattedDate(reminder);

		if (reminder != NOT_VALID) {
			dateString += getOverdue(reminder);
		}

		return dateString;
	}
	
	//@author A0111942N
	/**
	 * Accessor: Converts millisecond to date format
	 * 
	 * @return	Date format
	 */
	public String getFormattedDate(long dateMs) {
		
		String dateOutput = "";
		
		if (dateMs > 0) {
			
			dateFormat.applyLocalizedPattern(Operations.YEAR_FORMAT);
			String year = dateFormat.format(dateMs);
			String thisYear = dateFormat.format( System.currentTimeMillis() );
			
			if ( year.equals(thisYear) ) {
				dateFormat.applyLocalizedPattern(Operations.DATE_OUTPUT_NO_YEAR_FORMAT);
			} else {
				dateFormat.applyLocalizedPattern(Operations.DATE_OUTPUT_FORMAT);
			}

			dateOutput = dateFormat.format(dateMs);
		}
		
		return dateOutput;
	}
	
	//@author A0111942N
	/**
	 * Get the number of days the task is overdue.
	 * 
	 * @return	(String) Number of days the task is overdue
	 */
	public String getOverdue(long dateMs) {
		
		long nowMs = System.currentTimeMillis();
		
		if (dateMs < nowMs) {
			
			long overdue = nowMs - dateMs;
			int daysOverdue = (int) (overdue / DAY_MILLISECOND);
			
			if (daysOverdue == 0) {
				return "\n[Just overdue]\n"; 
			} else if (daysOverdue  == 1) {
				return "\n["+daysOverdue+" day overdue]\n";
			} else {
				return "\n["+daysOverdue+" day(s) overdue]\n";
			}
			
		}
		
		return "";
	}
	
	//@author A0111942N
	/**
	 * Mutator: Edit task's name
	 * 
	 * @return	Task's name
	 */
	public String editName(String name) {
		this.name = shortenText(name,NAME_MAX_LENGTH);
		return name;
	}
	
	//@author A0111942N
	/**
	 * Mutator: Edit task's description
	 * 
	 * @return	Task's description
	 */
	public String editDescription(String description) {
		this.description = shortenText(description,DESCRIPTION_MAX_LENGTH);
		return description;
	}
	
	//@author A0111942N
	/**
	 * Mutator: Edit task's state
	 * 
	 * @return	Task's state
	 */
	public String editState(String state) {
		this.state = state;
		return state;
	}

	//@author A0111942N
	/**
	 * Mutator: Edit task's label
	 * 
	 * @return	Task's label
	 */
	public long editLabel(long label) {
		this.label = label;
		return label;
	}

	//@author A0111942N
	/**
	 * Mutator: Edit task's label
	 * 
	 * @return	Task's label
	 */
	public long editStart(long start) {
		this.start = start;
		return start;
	}

	//@author A0111942N
	/**
	 * Mutator: Edit task's end
	 * 
	 * @return	Task's end (millisecond)
	 */
	public long editEnd(long end) {
		this.end = end;
		return end;
	}

	//@author A0111942N
	/**
	 * Mutator: Edit task's deadline
	 * 
	 * @return	Task's deadline (millisecond)
	 */
	public long editDeadline(long deadline) {
		this.deadline = deadline;
		return deadline;
	}
	
	//@author A0111942N
	/**
	 * Mutator: Edit task's reminder
	 * 
	 * @return	Task's reminder (millisecond)
	 */
	public long editReminder(long reminder) {
		this.reminder = reminder;
		return reminder;
	}

	//@author A0111942N
	/**
	 * Mutator: Edit task's time stamp
	 * 
	 * @return	Task's time stamp (millisecond)
	 */
	public long editTimestamp(long timestamp) {
		this.timeStamp = timestamp;
		return timeStamp;
	}
	
	//@author A0111942N
	/**
	 * Toggle task's done status from true to false
	 * vice-vesa.
	 */
	public void toggleDone(boolean _isDone) {
		isDone = _isDone;
	}
	
	//@author A0112898U
	/**
	 * Getter method to see if the task have reminder
	 * 
	 * @return boolean - returns isReminded
	 */
	public boolean gethasReminder(){
		return this.hasReminder;
	}
	
	//@author A0112898U
	/**
	 * Getter method to see if the task have been reminded
	 * 
	 * @return boolean - returns isReminded
	 */
	public boolean getIsReminded(){
		return this.isReminded;
	}
	
	//@author A0112898U
	/**
	 * Setter method to set the task to being reminded
	 * 
	 * @return boolean - returns isReminded
	 */
	public void setIsReminded(){
		isReminded = true;
	}
	
	//@author A0111942N
	/**
	 * Short @param text to length of @param length
	 * 
	 * @return	(String) Shorten text
	 */
	private String shortenText(String text, int length) {
		
		int orginalLength = text.length();
		
		if (orginalLength > length) {
			text = text.substring(0, length) + "...";
		}
		
		return text;
	}

	//@author A0111942N
	/**
	 * @return	String representation of the Task object
	 */
	@Override
	public String toString() {
		
		String message = "";
		
		// Include name into message
		message += "Task:\n" + name;
		
		// Include done or not
		if (isDone) {
			message += " [Done]\n\n";
		} else {
			message += "\n\n";
		}
		
		// Include description into message
		if (!description.isEmpty()) {
			message += "Description:\n" + description + "\n\n";
		}
		
		// Include start and end into message
		if ( !getFormattedStart().isEmpty() ) {
			message += "Starting Time:\n" + getFormattedStart() + "\n\n";
		}
		if ( !getFormattedEnd().isEmpty() ) {
			message += "Ending Time:\n" + getFormattedEnd() + "\n\n";
		}

		// Include deadline into message
		if (deadline != NOT_VALID) {
			message += "Deadline:\n" + getFormattedDeadline() + "\n\n";
		}
		
		// Include reminder into message
		if (reminder != NOT_VALID) {
			message += "Reminder:\n" + getFormattedReminder() + "\n\n";
		}

		// Include label into message
		String labelName = getLabelName();
		
		if (!labelName.equals(Operations.EMPTY_MESSAGE)) {
			message += "Label:\n" + labelName + "\n";
		}

		return message + "\n";
	}

	//@author A0111942N
	/**
	 * Use to short task in a list by either it's deadline or start time
	 * 
	 * @return	(String) Shorten text
	 */
	@Override
	public int compareTo(Task compareTask) {
		
		long currentTaskTime = Math.min(start,deadline);
		long compareTaskTime = Math.min(compareTask.getStart(), compareTask.getDeadline());
				
		if (currentTaskTime == compareTaskTime) {
			return (int) ( timeStamp - compareTask.getTimeStamp() );
		} else if (currentTaskTime > compareTaskTime) {
			return 1;
		} else {
			return NOT_VALID;
		}
	}
	
	//@author A0111942N
	/**
	 * Check if keyword exist in task's
	 * name or description.
	 * 
	 * @return	(Boolean) If it exist
	 */
	public boolean contains(String keyword) {
		
		if ( name.toLowerCase().contains(keyword) ) {
			return true;
		} else if ( description.toLowerCase().contains(keyword) ) {
			return true;
		} else {
			return false;
		}
	}
}