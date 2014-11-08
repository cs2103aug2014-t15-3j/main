import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

class ReminderTask extends TimerTask {
	
	//Class Objects
	Task taskToRemind;
	Timer taskAlarm;
	Date alarmTime;
	
	
	//@author A0112898U
	/**
	 * Constructor for Reminder Task.
	 * 
	 * @param inTask The task to use to construct a ReminderTask Object
	 * @param inDate The reminder date of the task
	 */
	public ReminderTask(Task inTask, Date inDate) {
		taskToRemind = inTask;
		alarmTime = inDate;
	}
	
	
	//@author A0112898U
	/**
	 * Getter function to get the defined task.
	 * 
	 * @return the task for this ReminderTask
	 */
	public Task getTask() {
		return taskToRemind;
	}
	
	
	//@author A0112898U
	/**
	 * API Function to edit a task
	 * 
	 * @param editedTask The edited task to be updated to
	 */
	public void editTask(Task editedTask) {
		taskToRemind = new Task(editedTask);
		alarmTime = new Date(editedTask.getReminder());
	}
		
	
	//@author A0112898U
	/**
	 * Indicate the action to do when the alarm goes off 
	 * for the scheduled reminder for this task
	 * 
	 * * This is a default function to have in order to be 
	 * * scheduled into timer.schedule.
	 */
	public void run() {		
    	new Notification("Here's your reminder for : \n" + taskToRemind.getName(),
    			taskToRemind.getDescription(), 
    			"Due on " + taskToRemind.getFormattedDeadline(), "", "").display();
	}
	
	
	//@author A0112898U
	/**
	 * Stops the alarm that have been scheduled.
	 */
	public void stopAlarm () {
		taskAlarm.cancel();
	}
	
	
	//@author A0112898U
	/**
	 * Schedules the alarm time for the task.
	 */
	public void scheduleAlarm() {
		taskAlarm = new Timer();
		taskAlarm.schedule(new ReminderTask(taskToRemind, alarmTime), alarmTime);
	}
}