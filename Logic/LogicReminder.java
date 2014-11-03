import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;




public class LogicReminder {

	
	
	
	static LinkedList<ReminderTask> taskToBeReminded =  new LinkedList<ReminderTask>();
	
	
	//Constructor
	//Only generate reminder for daily task
	LogicReminder(LinkedList<Task> tasks) throws ParseException{
	
		for(Task t:tasks){
			
			ReminderTask rTask = new ReminderTask(t,new Date(t.getReminder()));
			taskToBeReminded.add(rTask);
		}
	}
	
	
	//every 0000 the task to be reminded should be re-generated
	private static void regenReminderList(LinkedList<Task> tasks) throws ParseException{
		
		for(Task t:tasks){
		
			
			//Checks for today's task and add reminder.
			
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 0);
		    c.set(Calendar.MINUTE, 0);
		    c.set(Calendar.SECOND, 0);
		    c.set(Calendar.MILLISECOND, 0);
		    
		    Date today = c.getTime();
		    
		    
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

			
			Date date1 = sdf.parse(sdf.format(t.getReminder()));
			Date date2 = sdf.parse(sdf.format(today));
			
		    //System.out.println("today " + c.equals(today));
		    
			
			System.out.println("today " + (date1.getTime() == date2.getTime()));		
			System.out.println("today " + date2.toString());
		    System.out.println("remind " + date1.toString());
		    System.out.println();
		    
		    
		    if(date1.getTime() == date2.getTime()){
		    	
		    	//Create a reminder task and add to the list of reminders

		    	ReminderTask rTask = new ReminderTask(t,new Date(t.getReminder()));
				rTask.scheduleAlarm();
		    	taskToBeReminded.add(rTask);
		    }
		}
		
		
		for(ReminderTask rTsk:taskToBeReminded){
			System.out.println(rTsk.getTask().getName());	
		}	
	}
	
	
	//Tasks should be sort accordingly to their reminder time
	private void sortRTask(){
		
	}
	
	//Add new task
	//ask sam to check if reminder is for today, then add a reminder
	public void addTaskTobeReminded(Task t) throws ParseException{
		
		ReminderTask rTask = new ReminderTask(t,new Date(t.getReminder()));
		taskToBeReminded.add(rTask);
		rTask.scheduleAlarm();
	}
	
	
	public static void main(String[] args) throws ParseException {
		
		//LogicMain logicMain = new LogicMain();
		//logicMain.processInput(";add Samuel is awesome ;i lalalalal ;deadline 3 nov ;remind 03 11 2014 02 38");
		

		//Task t = logicMain.getAllTasks().get(0);
																		//2359 format
		SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date date1 = isoFormat.parse("2014-11-03T15:08:00");
		Task t1 = new Task("hi 1", "lalal", 123123, date1.getTime() ,date1.getTime());
		
		Date date2 = isoFormat.parse("2014-11-04T02:53:00");
		Task t2 = new Task("hi 2", "lalal", 123123, date2.getTime() ,date2.getTime());

		Date date3 = isoFormat.parse("2014-11-03T15:08:00");
		Task t3 = new Task("hi 3", "lalal", 123123, date3.getTime() ,date3.getTime());
		
		Date date4 = isoFormat.parse("2014-11-04T03:33:40");
		Task t4 = new Task("hi 4", "lalal", 123123, date4.getTime() ,date4.getTime());		
			
		
		LinkedList<Task> isTasks = new LinkedList<Task>();
		isTasks.add(t1);
		isTasks.add(t2);
		isTasks.add(t3);
		isTasks.add(t4);
		
		//ReminderTask rTask = new ReminderTask(t,date);
		//rTask.scheduleAlarm();
		
		regenReminderList(isTasks);
		//regenReminderList(logicMain.getAllTasks());
	}

}

class ReminderTask extends TimerTask{
	
	Task taskToRemind;
	Timer taskAlarm;
	Date alarmTime;
	

	
	
	public ReminderTask(Task t, Date date) throws ParseException{
		taskToRemind = t;
		
		/*
		SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		//isoFormat.setTimeZone(TimeZone.getTimeZone("UTC+08:00"));
		//Date 
		
		date = isoFormat.parse("2014-11-03T02:53:40");
		*/
		alarmTime = date;
		//alarmTime.setTime(time);
	}
	
	public ReminderTask(){
		//this.ReminderTask(taskToRemind,alarmTime);
	}
	
	public Task getTask(){
		return taskToRemind;
	}
	
	public void run() {
	    
    	System.out.println("Here's your reminder for : " 
    			+ taskToRemind.getName()+ "\n" 
    			+ taskToRemind.getDescription());
	}
	
	public void stopAlarm (){
		
		taskAlarm.cancel();
	}
	
	public void scheduleAlarm () throws ParseException{
		taskAlarm = new Timer();
		//taskAlarm.schedule(new ReminderTask(t),  new Date(t.getReminder()));
		taskAlarm.schedule(new ReminderTask(taskToRemind,alarmTime), alarmTime);
	}
}
