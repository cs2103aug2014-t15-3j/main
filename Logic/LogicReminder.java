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
	private static LogicReminder logicReminderSingleton;
	private static boolean isInitiated = false;
	
	
	/**************************************************************************/
	/**************************************************************************/
	/**************************      APIs      ********************************/
	/**************************************************************************/
	/**************************************************************************/
	
	//@author A0112898U
	/**
	 * //Constructor fo Logic Reminder Object, should only have one instance!
	 * Only generate reminder for daily task - Only add today's task!	
	 * 
	 * @param tasks
	 * @throws ParseException
	 */
	private LogicReminder(LinkedList<Task> tasks) throws ParseException{
		
		for(Task t:tasks){
			
			if (checkIsTodayTask(t)){
				ReminderTask rTask = new ReminderTask(t,new Date(t.getReminder()));
				taskToBeReminded.add(rTask);				
			}

		}
		
		isInitiated = true;
	}
	
	
	
	//@author A0112898U
	/**
	 * @param tasks
	 * @throws ParseException
	 */
	public static void initiateSingleton (LinkedList<Task> tasks) throws ParseException{
		
		if (!isInitiated){
			logicReminderSingleton = new LogicReminder(tasks);
		}
	}
	
	
	//@author A0112898U
	/**
	 * 	The getInstance getter for this Singleton class!
	 * @return the Singleton object LogicReminder
	 */
	static public LogicReminder getInstance(){
		return logicReminderSingleton;
	}
	
	

	//@author A0112898U
	/**
	 * Logic should call this every 0000 the task to be reminded should be re-generated
	 * 
	 * @param tasks
	 * @throws ParseException
	 */
	public static void regenReminderList(LinkedList<Task> tasks) throws ParseException{
		
		for(Task t:tasks){
		
			//Checks for today's task and add reminder.
			//Only set alarm for today! For efficiency
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
	 * Add new tasks or Updates the old task with the new task, and 
	 * re-schedules/schedules the new reminder
	 * 
	 * @param t
	 * @throws ParseException
	 */
	public void updateTaskTobeReminded(Task newTask, Task oldTask) throws ParseException{
		
		
		boolean isTaskAdded = false;
		boolean isTodayReminder = false;
		
		//check if the reminder for the new task is today
		if (checkIsTodayTask(newTask)){
			
			isTodayReminder = true;
		}
		
		
		//search for the old task
		for (ReminderTask tempRtask:taskToBeReminded){
				
			//Update task if task exists
			if (isTaskExist(tempRtask.getTask(),oldTask)){
				
				tempRtask.stopAlarm();
				
				if (isTodayReminder){
					
					//Edit and reschedule task
					tempRtask.editTask(newTask);
					tempRtask.scheduleAlarm();
				
				}else {
					
					taskToBeReminded.remove(oldTask);
					
				}
				
				isTaskAdded = true;
				
				break;
			}
		}
		
		//If task doesn't exist in the current list
		if (!isTaskAdded && isTodayReminder){
			
			addTaskTobeReminded(newTask);
		}
	}
	
	
	

	/**************************************************************************/
	/**************************************************************************/
	/***********************      PRIVATEs      *******************************/
	/**************************************************************************/
	/**************************************************************************/
	
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
	 * Check if the reminder for the task is today's task
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
	 * **
	 * @param currentCollatedTasks the added list of task to check with
	 * @param tobeAddedTask task that is to be added to check if it already existed
	 * 
	 * @return returns true if task is already present in the collated task, and returns
	 * 				   false if task hasn't been found in the collated task
	 */
	private boolean isTaskExist(Task taskToCheck, Task tobeAddedTask){
		
			if(taskToCheck.getName().equals(tobeAddedTask.getName()) 
					&& taskToCheck.getDescription().equals(tobeAddedTask.getDescription())
					&& (taskToCheck.getTimeStamp() == tobeAddedTask.getTimeStamp())
					&& (taskToCheck.getLabel() == tobeAddedTask.getLabel())
					){
				
				return true;
			}
		return false;
	}
	
	
	
	public static void main(String[] args) throws ParseException {
	
		/*																//2359 format
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
		
		regenReminderList(isTasks);
		*/
		
		//START ADDING HERE!
		LogicMain logicMain = new LogicMain();
		logicMain.processInput(";add Samuel is awesome ;i lalalalal ;deadline 4 nov ;remind 04 11 2014 7 11 pm");
		
		regenReminderList(logicMain.getAllTasks());
		
		LinkedList<Task> listTasks = new LinkedList<Task>(logicMain.getAllTasks());
		
		for(Task t:listTasks){

			
			Date d = new Date(t.getReminder());
			
			System.out.println(d.toString());
		}
		
	}

}


