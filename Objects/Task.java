import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

//@author A0111942N

public class Task implements Item, java.io.Serializable, Comparable<Task> {

	private final static String TO_DO_LABEL = "To-Do";

	// Class variable
	private String name;
	private String description;
	private long label;
	private long timeStamp;
	private long deadline;
	private String state;
	
	//@author A0111942N
	/**
	 * Constructor:
	 * When specified with task's name only
	 * 
	 * @param name	Task's name
	 */
	public Task(String name) {
		this(name, "", -1, -1);
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
		this(name, description, -1, -1);
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
		this.deadline = oldTask.getDeadline();
		this.state = "";
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
	public Task(String name, String description, long label, long deadline) {

		this.name = name;
		this.description = description;
		this.label = label;
		this.timeStamp = System.currentTimeMillis();
		this.deadline = deadline;
		this.state = "";
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

	public long getDeadline() {
		return deadline;
	}

	public String getState() {
		return state;
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

	public long editDeadline(long deadline) {
		this.deadline = deadline;
		return deadline;
	}
	
	public long editTimestamp(String timestamp) {
		this.timeStamp = timeStamp;
		return timeStamp;
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
	
	@Override
	public boolean equals(Object object) {

		if (object instanceof Task) {
			Task task = (Task) object;
			return timeStamp == task.getTimeStamp();
		} else {
			return false;
		}
	}

	@Override
	public String toString() {

		Date date = new Date(deadline);
		SimpleDateFormat format = new SimpleDateFormat(
				Operations.DATE_OUTPUT_FORMAT);
		String dateOutput = format.format(date);

		return "Task: " + name + "\nDescription: " + description
				+ "\nDeadline: " + dateOutput 
				+ "\nLabel: " + getLabelName(label) + "\n\n";
	}

	@Override
	public int compareTo(Task compareTask) {
		
		long compareDeadline = compareTask.getDeadline();
		
		if (deadline == compareDeadline) {
			return (int) ( timeStamp - compareTask.getTimeStamp() );
		} else if (deadline > compareDeadline) {
			return 1;
		} else {
			return -1;
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