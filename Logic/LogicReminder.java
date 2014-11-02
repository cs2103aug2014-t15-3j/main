import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;




public class LogicReminder {

	
	
	
	LinkedList<ReminderTask> taskToBeReminded;
	
	
	//Constructor
	//Only generate reminder for daily task
	LogicReminder(LinkedList<Task> tasks){
	
		for(Task t:tasks){
			
		}
	}
	
	
	//every 0000 the task to be reminded should be re-generated
	private void regenReminderList(LinkedList<Task> tasks){
		
	}
	
	
	//Tasks should be sort accordingly to their time
	private void sortRTask(){
		
	}
	
	//Add new task
	//ask sam to check if reminder is for today, then add a reminder
	public void addTaskTobeReminded(Task t){
		
	}
	
	
	public static void main(String[] args) throws ParseException {
		
		LogicMain logicMain = new LogicMain();
		logicMain.processInput(";add Samuel is awesome ;deadline 3 nov ;remind 2 21");
		

		Task t = logicMain.getAllTasks().get(0);
		
		System.out.println(t.getName());
		System.out.println(t.getDeadline());
		System.out.println(t.getReminder());		
		
		ReminderTask rTask = new ReminderTask(t);
		rTask.scheduleAlarm();
		
	}

}

class ReminderTask extends TimerTask{
	
	Task taskToRemind;
	Timer taskAlarm;
	Date alarmTime;
	
	public ReminderTask(Task t) throws ParseException{
		taskToRemind = t;
		
		SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		//isoFormat.setTimeZone(TimeZone.getTimeZone("UTC+08:00"));
		Date date = isoFormat.parse("2014-11-03T02:53:40");
		
		alarmTime = date;
		//alarmTime.setTime(time);
	}
	
	public void run() {
	    
    	System.out.println("Here's your reminder for : " 
    			+ taskToRemind.getName()+ "\n" 
    			+ taskToRemind.getDescription());
    
    	//taskAlarm.cancel();
	}
	
	public void scheduleAlarm () throws ParseException{
		taskAlarm = new Timer();
		//taskAlarm.schedule(new ReminderTask(t),  new Date(t.getReminder()));
		taskAlarm.schedule(new ReminderTask(taskToRemind), alarmTime);
	}
}
