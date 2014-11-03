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
	//@author A0112898U
	/**
	 * 
	 * @param tasks
	 * @throws ParseException
	 */
	LogicReminder(LinkedList<Task> tasks) throws ParseException{
	
		for(Task t:tasks){
			
			ReminderTask rTask = new ReminderTask(t,new Date(t.getReminder()));
			taskToBeReminded.add(rTask);
		}
	}
	
	
	
	//Logic should call this every 0000 the task to be reminded should be re-generated
	//@author A0112898U
	/**
	 * 
	 * @param tasks
	 * @throws ParseException
	 */
	private static void regenReminderList(LinkedList<Task> tasks) throws ParseException{
		
		for(Task t:tasks){
		
			//Checks for today's task and add reminder.
			//Only set alarm for today!
		    if (checkIsTodayTask(t)){
		    	
		    	//Create a reminder task and add to the list of reminders

		    	ReminderTask rTask = new ReminderTask(t,new Date(t.getReminder()));
				rTask.scheduleAlarm();
		    	taskToBeReminded.add(rTask);
		    }
		}
		
		//For testing purposes
		for(ReminderTask rTsk:taskToBeReminded){
			System.out.println(rTsk.getTask().getName());	
		}	
	}
	
	
	
	//Add new task
	//ask sam to check if reminder is for today, then add a reminder
	//@author A0112898U
	/**
	 * 
	 * @param t
	 * @throws ParseException
	 */
	public void addTaskTobeReminded(Task t) throws ParseException{
		
		ReminderTask rTask = new ReminderTask(t,new Date(t.getReminder()));
		taskToBeReminded.add(rTask);
		rTask.scheduleAlarm();
	}
	
	
	
	//@author A0112898U
	/**
	 * 
	 * @param t
	 * @return
	 * @throws ParseException
	 */
	private static boolean checkIsTodayTask(Task t) throws ParseException{
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
	    
	    Date today = c.getTime();
	    
	    
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

		
		Date date1 = sdf.parse(sdf.format(t.getReminder()));
		Date date2 = sdf.parse(sdf.format(today));
		
		//Testing Purposes
		System.out.println("today " + (date1.getTime() == date2.getTime()));		
		System.out.println("today " + date2.toString());
	    System.out.println("remind " + date1.toString());
	    System.out.println();
	    
	    //Only set alarm for today!
	    if (date1.getTime() == date2.getTime()){
	    	return true;
	    }
	    
	    return false;
	}

	//A0112898U
	/**
	 * 
	 * @param taskToStop
	 */
	public void stopTask(Task taskToStop){
		
		for(ReminderTask rTsk:taskToBeReminded){
			if(isTaskExist(rTsk.getTask(),taskToStop))
			{
				rTsk.stopAlarm();
				break;
			}
		}
				
	}
	
	
	
	//@author A0112898U
	/**
	 * Checks if the task to be added is already in the collated list of matched task
	 * 
	 * Reason why not to use the '.contains' but to create and use this function is because the 
	 * '.equals' in task was override by the original creator for other important checking purpose 
	 * thus the .contain doesn't work like the intended original purpose, which would work for my code
	 *
	 * @param currentCollatedTasks the added list of task to check with
	 * @param tobeAddedTask task that is to be added to check if it already existed
	 * 
	 * @return returns true if task is already present in the collated task, and returns
	 * 				   false if task hasn't been found in the collated task
	 */
	public static boolean isTaskExist(Task taskToCheck, Task tobeAddedTask){
		
		//for(Task t:currentCollatedTasks){
			
			if(taskToCheck.getName().equals(tobeAddedTask.getName()) 
					&& taskToCheck.getDescription().equals(tobeAddedTask.getDescription())
					&& (taskToCheck.getTimeStamp() == tobeAddedTask.getTimeStamp())
					&& (taskToCheck.getLabel() == tobeAddedTask.getLabel())
					){
				
				return true;
			}
		//}
		
		return false;
	}
	
	
	
	
	public static void main(String[] args) throws ParseException {
		
		//LogicMain logicMain = new LogicMain();
		//logicMain.processInput(";add Samuel is awesome ;i lalalalal ;deadline 3 nov ;remind 03 11 2014 02 38");
		

		//Task t = logicMain.getAllTasks().get(0);
																		//2359 format
		SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date date1 = isoFormat.parse("2014-11-03T16:13:30");
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


