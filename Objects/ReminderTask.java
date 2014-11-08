import java.text.ParseException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

class ReminderTask extends TimerTask{
	
	Task taskToRemind;
	Timer taskAlarm;
	Date alarmTime;
	

	
	//@author A0112898U
	/**
	 * Constructor for Reminder Task
	 * @param t
	 * @param date
	 * @throws ParseException
	 */
	public ReminderTask(Task t, Date date) throws ParseException{

		taskToRemind = t;
		alarmTime = date;
	}
	
	
	
	//@author A0112898U
	/**
	 * 
	 * @return the task for this ReminderTask
	 */
	public Task getTask(){
		return taskToRemind;
	}
	
	
	
	//@author A0112898U
	/**
	 * 
	 * @return the task for this ReminderTask
	 */
	public void editTask(Task t){
	
		taskToRemind = new Task(t);
		alarmTime = new Date(t.getReminder());
	}
		
	
	
	//@author A0112898U
	/**
	 * Indicate the thing to do when the alarm goes off
	 */
	public void run() {
	    
    	/*System.out.println("Here's your reminder for : " 
    			+ taskToRemind.getName()+ "\n" 
    			+ taskToRemind.getDescription());
    	*/
		
    	new Notification("Here's your reminder for : \n" + taskToRemind.getName()
    			, taskToRemind.getDescription(), "Due on " + taskToRemind.getFormattedDeadline(), "", "").display();
	}
	
	
	
	//@author A0112898U
	/**
	 * Stops the alarm that have been scheduled
	 */
	public void stopAlarm (){
		
		taskAlarm.cancel();
	}
	
	
	
	/**
	 * Schedules the alarm time for the task
	 * @throws ParseException
	 */
	public void scheduleAlarm () throws ParseException{
		taskAlarm = new Timer();
		//taskAlarm.schedule(new ReminderTask(t),  new Date(t.getReminder()));
		taskAlarm.schedule(new ReminderTask(taskToRemind,alarmTime), alarmTime);
	}
}