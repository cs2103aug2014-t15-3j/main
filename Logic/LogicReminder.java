import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LogicReminder {
	
	//Objects
	static LinkedList<ReminderTask> taskToBeReminded =  new LinkedList<ReminderTask>();
	private static LogicReminder logicReminderInstance;
	
	//Variables
	private final static String LOG_NAME = "Logic Reminder Logger";
	private static boolean isInitiated = false;

	// Logger: Use to troubleshoot problems
	private static Logger logger = Logger.getLogger(LOG_NAME);
	
	/**************************************************************************/
	/**************************************************************************/
	/**************************      APIs      ********************************/
	/**************************************************************************/
	/**************************************************************************/
	
	//@author A0112898U
	/**
	 * Constructor for Logic Reminder Singleton, should only have one instance!
	 * Impt note - Only generate reminder for daily task - Only add today's task!	
	 * 
	 * @param tasks The list of tasks to initiate the system with.
	 */
	private LogicReminder(LinkedList<Task> tasks) {		
		//Loop through all tasks & add only tasks with today's reminder date
		for (Task t:tasks) {
			if (checkIsTodayTask(t)) {
				ReminderTask rTask = new ReminderTask(t, 
						new Date(t.getReminder()));
				taskToBeReminded.add(rTask);				
			}
		}		
		//Acknowledges the initiation
		isInitiated = true;
	}
	
	
	//@author A0112898U
	/**
	 * API function to initiate the Logic Reminder System's Singleton
	 * 
	 * @param tasks The list of tasks to initiate the system with.
	 * @throws ParseException
	 */
	public static void initiateSingleton (LinkedList<Task> tasks) {
		//Initiated the Singleton only if not initiated
		if (!isInitiated) {
			logicReminderInstance = new LogicReminder(tasks);
		}
	}
	
	
	//@author A0112898U
	/**
	 * Getter to get the instance for this Singleton class.
	 * 
	 * @return LogicReminder returns the Singleton's Instance
	 */
	public static LogicReminder getInstance() {
		return logicReminderInstance;
	}


	//@author A0112898U
	/**
	 * API function to refresh the Reminder System List.
	 * To Implement - Logic should call this every 0000 the tasks 
	 * to be reminded should be re-generated.
	 * 
	 * @param tasks The list of tasks to refresh the system with.
	 */
	public static void regenReminderList(LinkedList<Task> tasks) {
		//Loop through all tasks & add only tasks with today's reminder date
		for (Task t:tasks) {
			//Checks for today's task and add task.
		    if (checkIsTodayTask(t)) {
		    	//Create a reminder task and add to the list of reminders
		    	ReminderTask rTask = new ReminderTask(t, 
		    			new Date(t.getReminder()));
		    	
				//Schedule an alarm for this task.
		    	rTask.scheduleAlarm();
		    	
		    	//Add task to system's list.
		    	taskToBeReminded.add(rTask);
		    }
		}
	}

	
	//@author A0112898U
	/**
	 * Add new tasks or Updates the old task with the new task, and 
	 * re-schedules/schedules the new reminder
	 * 
	 * @param newTask The new task that is to be changed to
	 * @param oldTask The old task task that is to be changed.
	 */
	public void updateTaskTobeReminded(Task newTask, Task oldTask) {
		//Local Variable for 'updateTaskTobeReminded' function
		boolean isTaskAdded = false;
		boolean isTodayReminder = false;
		
		//check if the reminder for the new task is today
		if (checkIsTodayTask(newTask)) {
			isTodayReminder = true;
		}
		
		//Search for the old task (if any).
		for (ReminderTask tempRtask:taskToBeReminded) {
			//Update task if task exists.
			if (isTaskExist(tempRtask.getTask(), oldTask)) {
				//Stops the previous scheduled alarm.
				tempRtask.stopAlarm();
				
				if (isTodayReminder) {
					//Edit and reschedule task.
					tempRtask.editTask(newTask);
					tempRtask.scheduleAlarm();			
				} else {
					//Remove reminder if if not for today.
					taskToBeReminded.remove(oldTask);
				}
				//To indicate that task was present in the list.
				isTaskAdded = true;
				//Break from the loop if task already found.
				break;
			}
		}
		
		//If task doesn't exist in the current list.
		if (!isTaskAdded && isTodayReminder) {	
			//Add new task!
			addTaskTobeReminded(newTask);
		}
	}
	
	
	//@author A0112898U
	/**
	 * API function that is to be called when new tasks are added to
	 * the Logic System buffered tasks list. This function will then 
	 * check if the task's indicated reminder time and date is for 
	 * today before adding into the Reminder System.
	 * 
	 * @param newTask The newly added task.
	 */
	public void addTaskTobeReminded(Task newTask) {
		//Creates a new ReminderTask Object with the new Task.
		ReminderTask rTask = new ReminderTask(newTask, 
				new Date(newTask.getReminder()));
		
		//Add the new ReminderTask to the Singleton's ReminderTask List.
		taskToBeReminded.add(rTask);
		
		//Schedules an alarm for this new task.
		rTask.scheduleAlarm();
	}
	

	//A0112898U
	/**
	 * Stops the scheduled alarm for the input task
	 * 
	 * @param taskToStop Task that alarm should be stop.
	 */
	public void stopTask(Task taskToStop) {
		//Loop through all collated tasks
		for (ReminderTask rTsk:taskToBeReminded) {
			if (isTaskExist(rTsk.getTask(), taskToStop)) {
				//Stop the scheduled alarm.
				rTsk.stopAlarm();
				//break out of loop if task found.
				break;
			}
		}
	}

	/**************************************************************************/
	/**************************************************************************/
	/***********************      PRIVATEs      *******************************/
	/**************************************************************************/
	/**************************************************************************/
	
	
	//@author A0112898U
	/**
	 * Check if the reminder for the task is today's task.
	 * 
	 * @param inTask The task to be passed in.
	 * @return boolean True if Reminder is for today, false if otherwise.
	 */
	private static boolean checkIsTodayTask(Task inTask) {
		//Create java calendar instance to get today's date
		Calendar c = Calendar.getInstance();
		
		//Set all other possible time to 0, other then the day
		c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
	    
	    //Objects to help compare the dates
	    Date date1 = null;
		Date date2 = null;
		Date today = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

		//Format today's date and the task's reminder's date
		try {
			date1 = sdf.parse(sdf.format(inTask.getReminder()));
			date2 = sdf.parse(sdf.format(today));
		} catch (ParseException e) {
			e.printStackTrace();
			logger.log(Level.WARNING, "Unable to Parse the dates");
		}
		
	    //Compare the dates, if same return true.
	    if (date1.getTime() == date2.getTime()) {
	    	return true;
	    }
	    return false;
	}
	

	
	
	//@author A0112898U
	/**
	 * Checks if the task to be added is same as the existed task.
	 * 
	 * @param existedTask The task to check with.
	 * @param newTask Task that is to be added to check if it exists.
	 * @return boolean True if task is same as existed Task, false otherwise.
	 */
	private boolean isTaskExist(Task existedTask, Task newTask) {
		if (existedTask.getName().equals(newTask.getName()) 
				&& existedTask.getDescription().equals(newTask.getDescription())
				&& (existedTask.getTimeStamp() == newTask.getTimeStamp())
				&& (existedTask.getLabel() == newTask.getLabel())
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


