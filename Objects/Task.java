import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

//@author A0111942N

public class Task implements Item, java.io.Serializable, Comparable<Task> {

	private final static String TO_DO_LABEL = "To-Do";
	private final static int NOT_VALID = -1;
	private final static long DAY_MILLISECOND = 86400000;
	
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

		this.name = name;
		this.description = description;
		this.label = label;
		this.timeStamp = System.currentTimeMillis();
		this.deadline = deadline;
		this.reminder = reminder;
		this.state = "";
		this.isDone = false;
	}

	// Accessor: Return name
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public long getLabel() {
		return label;
	}

	public long getTimeStamp() {
		return timeStamp;
	}
	
	public long getStart() {
		return start;
	}
	
	public long getEnd() {
		return end;
	}

	public long getDeadline() {
		return deadline;
	}

	public long getReminder() {
		return reminder;
	}
	
	public String getFormattedStart() {

		return getFormattedDate(start);
	}
	
	public String getFormattedEnd() {

		return getFormattedDate(end);
	}
	
	public String getFormattedDeadline() {
		
		return getFormattedDate(deadline);
	}
	
	public String getFormattedReminder() {

		return getFormattedDate(reminder);
	}
	
	public String getFormattedDate(long dateMs) {
		
		String dateOutput = "";
		
		if (dateMs > 0) {
			
			dateFormat.applyLocalizedPattern(Operations.YEAR_FORMAT);
			String year = dateFormat.format(dateMs);
			String thisYear = dateFormat.format( System.currentTimeMillis() );
			
			if( year.equals(thisYear) ) {
				dateFormat.applyLocalizedPattern(Operations.DATE_OUTPUT_NO_YEAR_FORMAT);
			} else {
				dateFormat.applyLocalizedPattern(Operations.DATE_OUTPUT_FORMAT);
			}

			dateOutput = dateFormat.format(dateMs);
		}
		
		return dateOutput;
	}

	public String getState() {
		return state;
	}
	
	public boolean getDone() {
		return isDone;
	}

	public String editName(String name) {
		this.name = name;
		return name;
	}

	public String editDescription(String description) {
		this.description = description;
		return description;
	}

	public String editState(String state) {
		this.state = state;
		return state;
	}
	
	public long editLabel(long label) {
		this.label = label;
		return label;
	}

	public long editStart(long start) {
		this.start = start;
		return start;
	}

	public long editEnd(long end) {
		this.end = end;
		return end;
	}

	public long editDeadline(long deadline) {
		this.deadline = deadline;
		return deadline;
	}
	
	public long editReminder(long reminder) {
		this.reminder = reminder;
		return reminder;
	}
	
	public long editTimestamp(long timestamp) {
		this.timeStamp = timestamp;
		return timeStamp;
	}
	
	public void toggleDone(boolean _isDone) {
		isDone = _isDone;
	}
	
	public String getLabelName() {
		
		String labelName = getLabelName(label);
		
		return labelName;
	}

	private String getLabelName(long _label) {
		LinkedList<Label> bufferLabelsList = LogicMain.getAllLabels();
		
		for(int j = 0; j < bufferLabelsList.size(); j++) {

			if(bufferLabelsList.get(j).getTimeStamp() == _label) {
				Label tempLabel = bufferLabelsList.get(j);
				return tempLabel.getName();
			}
		}
		
		return Operations.EMPTY_MESSAGE;
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

	@Override
	public String toString() {
		
		String message = "";
		
		// Include name into message
		message += "Task: " + name;
		
		// Include done or not
		if (isDone) {
			message += " [DONE]\n";
		} else {
			message += "\n";
		}
		
		// Include description into message
		if (!description.isEmpty()) {
			message += "Description: " + description + "\n";
		}
		
		// Include start and end into message
		if ( !getFormattedStart().isEmpty() ) {
			message += "Start: " + getFormattedStart() + "\n";
		}
		if ( !getFormattedEnd().isEmpty() ) {
			message += "End: " + getFormattedEnd() + "\n";
		}

		// Include deadline into message
		message += "Deadline: " + getFormattedDeadline() + "\n";
		
		// Include reminder into message
		if(reminder != NOT_VALID) {
			
			message += "Remind at: " + getFormattedReminder();
			
			if(reminder >= System.currentTimeMillis()) {
				message += "\n";
			} else {
				
				long overdue = System.currentTimeMillis() - reminder;
				int daysOverdue = (int) (overdue / DAY_MILLISECOND);
				
				message += " ["+daysOverdue+" day(s) overdue]\n";
			}
			
		}

		// Include label into message
		String labelName = getLabelName();
		
		if(!labelName.equals(Operations.EMPTY_MESSAGE)) {
			message += "Label: " + labelName + "\n";
		}

		return message + "\n";
	}

	@Override
	public int compareTo(Task compareTask) {
		
		long compareDeadline = compareTask.getDeadline();
		
		if (deadline == compareDeadline) {
			return (int) ( timeStamp - compareTask.getTimeStamp() );
		} else if (deadline > compareDeadline) {
			return 1;
		} else {
			return NOT_VALID;
		}
	}
	
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