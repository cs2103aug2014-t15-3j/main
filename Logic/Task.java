//@author Samuel Lim Yi Jie

public class Task implements java.io.Serializable{
	
	private final static String TO_DO_LABEL = "To-Do";
	
	private String name;
	private String description;
	private String label;
	private long timeStamp;
	private long deadline;
	
	public Task(String name) {
		this(name, "", TO_DO_LABEL, -1);
	}
	
	public Task(String name, String description) {
		this(name, description, TO_DO_LABEL, -1);
	}
	
	public Task(String name, String description, String label, long deadline) {
		
		this.name = name;
		this.description = description;
		this.label = label;
		this.timeStamp = System.currentTimeMillis();
		this.deadline = deadline;
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
		
		return "{Name: \""+name+"\", Description: \""+description+"\"}";
	}
}
