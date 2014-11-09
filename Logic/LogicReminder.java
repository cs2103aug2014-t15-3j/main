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
			if (checkIsTodayTask(t) && !isReminderOver(t)) {
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
	 * Accessor for reminder tasks list
	 * 
	 * @return LinkedList The reminder tasks list
	 */
	static public LinkedList<ReminderTask> getList() {
		return taskToBeReminded;
	}
	
	//@author A0112898U
	/**
	 * Mutator for reminder tasks list
	 */
	static public void editList(LinkedList<ReminderTask> _taskToBeReminded) {
		taskToBeReminded = _taskToBeReminded;
	}


	//@author A0112898U
	/**
	 * API function to refresh the Reminder System List.
	 * To Implement - Logic should call this every 0000 the tasks 
	 * to be reminded should be re-generated.
	 * 
	 * @param tasks The list of tasks to refresh the system with.
	 */
	public void regenReminderList(LinkedList<Task> tasks) {
		//Loop through all tasks & add only tasks with today's reminder date
		for (Task t:tasks) {
			//Checks for today's task and not over task then add task.
		    if (checkIsTodayTask(t) && !(isReminderOver(t))) {
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
				
				if (isTodayReminder && !isReminderOver(newTask)) {
					//Edit and reschedule task.
					tempRtask.editTask(newTask);
					tempRtask.scheduleAlarm();			
				} else {
					//Remove reminder if if not for today or over.
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
		if (checkIsTodayTask(newTask) && !isReminderOver(newTask)) {
			//Creates a new ReminderTask Object with the new Task.
			ReminderTask rTask = new ReminderTask(newTask, 
					new Date(newTask.getReminder()));
			
			//Add the new ReminderTask to the Singleton's ReminderTask List.
			taskToBeReminded.add(rTask);
			
			//Schedules an alarm for this new task.
			rTask.scheduleAlarm();
		}
	}
	

	//A0112898U
	/**
	 * API function that is to be called when new tasks are deleted from
	 * the Logic System buffered tasks list. This function will then 
	 * stops the scheduled alarm for the input(deleted) task.
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

	
	//A0112898U
	/**
	 * Getter method to get the collated ReminderTasks
	 *  
	 * @return tempList Returns a newList popuplate with
	 * the collated tasks from the Singleton.
	 */
	public LinkedList<ReminderTask> getReminderList() {
		LinkedList<ReminderTask> tempList = 
				new LinkedList<ReminderTask>(taskToBeReminded);
		return tempList;
	}
	
	//@author A0111942N
	/**
	 * API function that stops all the existing tasks from sounding off
	 */
	public void stopAllTasksReminder() {
		for ( ReminderTask rt : taskToBeReminded ) {
			rt.stopAlarm();
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
			logger.log(Level.WARNING, 
					   "LogicReminder - Unable to Parse the dates");
		}
		
	    //Compare the dates, if same return true.
	    if (date1.getTime() == date2.getTime()) {
	    	return true;
	    }
	    return false;
	}
	
	//@author A0112898U
	/**
	 * Checks if task's reminder time is already over
	 * 
	 * @param task The task to check with
	 * @return boolean True if reminder time indication is over, False otherwise.
	 */
	private boolean isReminderOver(Task task) {
		//if reminder time is greater then current system time means not over
		if (task.getReminder() >= System.currentTimeMillis()) {
			return false;
		}
		return true;
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
				) {
			return true;
		}
		return false;
	}
}


