//@author Samuel Lim Yi Jie

public class Task implements java.io.Serializable {
	
	private final static String TO_DO_LABEL = "To-Do";
	
	private String name;
	private String description;
	private String label;
	private long timeStamp;
	private long deadline;
	private String state;
	
	public Task(String name) {
		this(name, "", TO_DO_LABEL, -1);
	}
	
	public Task(String name, String description) {
		this(name, description, TO_DO_LABEL, -1);
	}
	
	public Task(Task oldTask) {
		
		this.name = oldTask.getName();
		this.description = oldTask.getDescription();
		this.label = oldTask.getLabel();
		this.timeStamp = oldTask.getTimeStamp();
		this.deadline = oldTask.getDeadline();
		this.state = "";
	}
	
	public Task(String name, String description, String label, long deadline) {
		
		this.name = name;
		this.description = description;
		this.label = label;
		this.timeStamp = System.currentTimeMillis();
		this.deadline = deadline;
		this.state = "";
	}
	
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
		
		return "{Name: \""+name+"\", Description: \""+description+"\", \""+state+"\"}";
	}
}
