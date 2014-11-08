import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;


public class LogicReminderTest {

	//Variables
	private final static String LOG_NAME = "Logic Reminder Test Logger";
	
	// Logger: Use to troubleshoot problems
	private static Logger logger = Logger.getLogger(LOG_NAME);
	
	//@author A0112898U
	/**
	 * Test cases for Remembra's Reminder System
	 */	
	@Test
	public void testExecuteCommand() throws IOException {
	
		LinkedList<Task> tasks = new LinkedList<Task>();
		LogicReminder.initiateSingleton(tasks);
		
		testCase_testRegenFunc();
	}
	
	//@author A0112898U
	/**
	 * Test Cases to see if the regeneration function for reminder system works
	 * This also test if the dates are added correctly when initialize function
	 * is called as they used similar methods to add the tasks.
	 * This test cases also test for 'checkIsTodayTask' function
	 */
	public static void testCase_testRegenFunc() {
		
		SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date date1 = null;
		Date date2 = null;
		Date date3 = null;
		Date date4 = null;
		
		try {
			//Test dates
			date1 = isoFormat.parse("2013-11-03T16:13:30");
			date2 = isoFormat.parse("2015-11-04T02:53:00");
			date3 = isoFormat.parse("2014-11-03T15:08:00");
			date4 = isoFormat.parse("2014-12-03T15:08:00");
		} catch (ParseException e) {
			e.printStackTrace();
			logger.log(Level.WARNING, "Unable to Parse the dates");
		}
		
		//Test tasks
		Task t1 = new Task("hi 1", "lalal", 123123, date1.getTime() ,date1.getTime());
		t1.editReminder(date1.getTime());
		Task t2 = new Task("hi 2", "lalal", 123123, date2.getTime() ,date2.getTime());
		t2.editReminder(date2.getTime());
		Task t3 = new Task("hi 3", "lalal", 123123, date3.getTime() ,date3.getTime());
		t3.editReminder(date3.getTime());
		Task t4 = new Task("hi 4", "lalal", 123123, date4.getTime() ,date4.getTime());
		t4.editReminder(date4.getTime());
		
		//Create java calendar instance to get today's date
		Calendar calendar = Calendar.getInstance();
		Date date5 = calendar.getTime();
		Task t5 = new Task("hi 5", "lalal", 123123, date5.getTime() ,date5.getTime());		
		t5.editReminder(date5.getTime());
		
		
		LinkedList<Task> tasks = new LinkedList<Task>();
		tasks.add(t1);
		tasks.add(t2);
		tasks.add(t3);
		tasks.add(t4);
		tasks.add(t5);
		
		LogicReminder.getInstance().regenReminderList(tasks);
		
		//Check for boundary case - last year's date
		assertEquals("Test 1 - 'LogicReminder - testRegenListFunc'", false, checkTaskAdded(t1));
		
		//Check for boundary case - next year's date
		assertEquals("Test 2 - 'LogicReminder - testRegenListFunc'", false, checkTaskAdded(t2));
		
		//Check for boundary case - date that had already passed but is this year
		assertEquals("Test 3 - 'LogicReminder - testRegenListFunc'", false, checkTaskAdded(t3));
		
		//Check for boundary case - date that haven't passed but is this year
		assertEquals("Test 4 - 'LogicReminder - testRegenListFunc'", false, checkTaskAdded(t4));
	
		//Check for boundary case - today's date via the system's date
		assertEquals("Test 5 - 'LogicReminder - testRegenListFunc'", true, checkTaskAdded(t5));
	}
	
	
	//@author A0112898U
	/**
	 * Check if task with invalid date is added.
	 * Invalid Date such as task with reminder set to tomorrow etc. 
	 * 
	 * @return boolean True if task is present, false if task is not
	 */
	public static boolean checkTaskAdded(Task inTask) {
		//Create a new list from the reminder system's list
		LinkedList<ReminderTask> rTasks = 
				new LinkedList<ReminderTask>(
						LogicReminder.getInstance().getReminderList());
		
		for (ReminderTask rTsk:rTasks) {
			if (rTsk.getTask().equals(inTask)) {
				return true;
			}
		}
		return false;
	}
}

