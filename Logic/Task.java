import java.text.SimpleDateFormat;
import java.util.Date;

//@author Samuel Lim Yi Jie

/*********************************************************************/
/******************* QA I - Refactor Code I***************************/
/*********************************************************************/
// @Sam - Task.java
//
// 1. Would it be possible if you could alter your comments of the
//    functions to the java comments structure given in the java
//	  coding standards?
// 2. Would it be possible to complete the function comments for the 
//	  rest of the functions
//
// @Sam - Task.java
/*********************************************************************/
/*********************************************************************/

public class Task implements java.io.Serializable {
	
	private final static String TO_DO_LABEL = "To-Do";
	
	private String name;
	private String description;
	private String label;
	private long timeStamp;
	private long deadline;
	private String state;
	
	//Constructor (When provided with task's name only)
	public Task(String name) {
		this(name, "", TO_DO_LABEL, -1);
	}
	
	//Constructor (When provided with task's name and description only)
	public Task(String name, String description) {
		this(name, description, TO_DO_LABEL, -1);
	}
	
	//Constructor (When provided with another task object)
	public Task(Task oldTask) {
		
		this.name = oldTask.getName();
		this.description = oldTask.getDescription();
		this.label = oldTask.getLabel();
		this.timeStamp = oldTask.getTimeStamp();
		this.deadline = oldTask.getDeadline();
		this.state = "";
	}
	
	//Constructor (When provided with task's name, description, label and deadline)
	public Task(String name, String description, String label, long deadline) {
		
		this.name = name;
		this.description = description;
		this.label = label;
		this.timeStamp = System.currentTimeMillis();
		this.deadline = deadline;
		this.state = "";
	}
	
	//Accessor: Return name
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getLabel() {
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
	
	public long editDeadline(long deadline) {
		this.deadline = deadline;
		return deadline;
	}
	
	@Override
	public boolean equals(Object object) {

		if(object instanceof Task) {
			Task task = (Task) object;
			return timeStamp==task.getTimeStamp();
		}
		else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		
		Date date = new Date(deadline);
		SimpleDateFormat format = new SimpleDateFormat(Operations.DATE_OUTPUT_FORMAT);
		String dateOutput = format.format(date);
		
		return "Task: "+name+"\nDescription: "+description+"\nDeadline: "+dateOutput+"\n\n";
	}
}
