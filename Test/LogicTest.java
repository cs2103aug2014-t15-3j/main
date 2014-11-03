import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class LogicTest {

	private LogicMain logic;
	private LinkedList<Task> tasks;
	private LinkedList<Label> labels;
	private LinkedList<Item> returnTasks;
	
	private final String TASK_NAME = "Bring Amanda home!";
	private final String TASK_NAME_2 = "Live for cash";
	private final String TASK_DESCRIPTION = "Cause she is forever lost...";
	private final String TASK_DESCRIPTION_2 = "Money is like water, it will never be enough.";
	private final String TASK_DATE = "25 Dec 2015 9 34 am";
	private final String EXPECTED_DATE = "25 December 2015 9:34 AM";
	private final String TASK_DATE_2 = "14 Mar 2016 2 12 pm";
	private final String EXPECTED_DATE_2 = "14 March 2016 2:12 PM";
	
	
	@Before
	public void setUp() throws Exception {

		// Initialize
		logic = new LogicMain();

		// Get tasks and labels
		tasks = new LinkedList<Task>(LogicMain.getAllTasks());
		labels = new LinkedList<Label>(LogicMain.getAllLabels());
	}
	

	/**
	 * TEST CASES: TASK ADD OPERATIONS
	 */

	// Corner case 1: User types invalid input
	@Test
	public void addTaskInvalid() {

		returnTasks = new LinkedList<Item>();
		returnTasks.addAll( logic.processInput(";add") );
		returnTasks.addAll( logic.processInput(";add ;description") );
		returnTasks.addAll( logic.processInput(";add ;deadline") );
		returnTasks.addAll( logic.processInput(";add ;label") );
		returnTasks.addAll( logic.processInput(";add ;someInvalidOperation") );
		
		assertTrue(returnTasks.size() == 0);
		
		// 2z: Invalid ";description" ";deadline" ";label" & some invalid operation
		returnTasks = logic.processInput(";add " + TASK_NAME + " ;description ;deadline ;label ;someInvalidOperation");
		Task addedTask = (Task) returnTasks.get(0);
		
		System.out.println(addedTask.getDeadline());
		
		assertTrue(addedTask.getName().equals(TASK_NAME)
				&& addedTask.getDescription().isEmpty()
				&& addedTask.getDeadline() == LogicMain.getEndOfToday()
				&& addedTask.getLabel() == -1 );
	}

	// Corner case 2: User types ";add <Task Name>" only
	@Test
	public void addTasks() {

		returnTasks = new LinkedList<Item>();
		
		// 2a: ";add <Task Name>" only
		returnTasks = logic.processInput(";add " + TASK_NAME);
		Task addedTask = (Task) returnTasks.get(0);
		assertTrue(addedTask.getName().contains(TASK_NAME));
		
		// 2b: Task with description
		returnTasks = logic.processInput(";add " + TASK_NAME + " ;description " + TASK_DESCRIPTION);
		addedTask = (Task) returnTasks.get(0);
		
		assertTrue(addedTask.getName().equals(TASK_NAME)
					&& addedTask.getDescription().equals(TASK_DESCRIPTION));
		
		// 2b: Task with deadline
		returnTasks = logic.processInput(";add " + TASK_NAME + " ;on " + TASK_DATE);
		addedTask = (Task) returnTasks.get(0);
		
		assertTrue(addedTask.getName().equals(TASK_NAME)
				&& addedTask.getFormattedDeadline().contains(EXPECTED_DATE));
		
		// 2b: Task with description and deadline
		returnTasks = logic.processInput(";add " + TASK_NAME + " ;description " + TASK_DESCRIPTION + " ;on " + TASK_DATE);
		addedTask = (Task) returnTasks.get(0);

		assertTrue(addedTask.getName().equals(TASK_NAME)
				&& addedTask.getDescription().equals(TASK_DESCRIPTION)
				&& addedTask.getFormattedDeadline().equals(EXPECTED_DATE) );
	}
	
	
	
	
	/**
	 * TEST CASES: TASK EDIT OPERATIONS
	 */

	// Corner case 1: User types invalid input
	@Test
	public void editTaskInvalid() {

		returnTasks = new LinkedList<Item>();
		
		returnTasks.addAll( logic.processInput(";edit") );
		returnTasks.addAll( logic.processInput(";edit ;description") );
		returnTasks.addAll( logic.processInput(";edit ;deadline") );
		returnTasks.addAll( logic.processInput(";edit ;label") );
		returnTasks.addAll( logic.processInput(";edit ;someInvalidOperation") );
		
		assertTrue(returnTasks.size() == 0);
		
		// 1b: Invalid ";description" ";deadline" ";label" & some invalid operation
		
		if (LogicMain.getAllTasks().size() == 0) {

			logic.processInput(";add " + TASK_NAME);
		}
		
		returnTasks = logic.processInput(";edit 1 ;description ;deadline ;label ;someInvalidOperation");
		
		assertTrue(returnTasks.size() == 0);
	}

	// Corner case 2: User types ";add <Task Name>" only
	@Test
	public void editTasks() {

		returnTasks = new LinkedList<Item>();
		
		if ( LogicMain.getAllTasks().size() == 0 ) {
			logic.processInput(";add Mock task");
		}
		
		System.out.println(LogicMain.getAllTasks());
		
		Task task = LogicMain.getAllTasks().get(0);
		
		// 2a: ";edit <Task Name>" only
		returnTasks = logic.processInput(";edit 1 ;name " + TASK_NAME);
		Task editedTask = (Task) returnTasks.get(0);
		assertTrue(editedTask.getName().equals(TASK_NAME));
		
		// 2b: Task with description
		returnTasks = logic.processInput(";edit 1 ;description " + TASK_DESCRIPTION);
		editedTask = (Task) returnTasks.get(0);
		
		assertTrue(editedTask.getDescription().equals(TASK_DESCRIPTION));
		
		// 2c: Task with deadline
		returnTasks = logic.processInput(";edit 1 ;on " + TASK_DATE);
		editedTask = (Task) returnTasks.get(0);
		
		assertTrue(editedTask.getFormattedDeadline().contains(EXPECTED_DATE));
		
		// 2d: Task with reminder
		returnTasks = logic.processInput(";edit 1 ;remind " + TASK_DATE);
		editedTask = (Task) returnTasks.get(0);

		assertTrue(editedTask.getFormattedReminder().contains(EXPECTED_DATE));
		
		// 2b: Task with name, description and deadline
		returnTasks = logic.processInput(";edit 1 ;name " + TASK_NAME_2
						+ " ;description " + TASK_DESCRIPTION_2
						+ " ;on " + TASK_DATE_2
						+ " ;remind " + TASK_DATE_2 );
		editedTask = (Task) returnTasks.get(0);

		assertTrue(editedTask.getName().equals(TASK_NAME_2)
				&& editedTask.getDescription().equals(TASK_DESCRIPTION_2)
				&& editedTask.getFormattedDeadline().equals(EXPECTED_DATE_2)
				&& editedTask.getFormattedReminder().equals(EXPECTED_DATE_2) );
	}
	
	
	
	/**
	 * TEST CASES: TASK VIEW OPERATIONS
	 */

	@Test
	public void viewTask() {
		
		LinkedList<Task> allTasks = LogicMain.getAllTasks();
		
		// a: view all tasks		
		returnTasks = logic.processInput(";view");
		assertEquals(allTasks.size(), returnTasks.size());
		
		// b: view invalid command
		returnTasks = logic.processInput(";view Something Invalid");
		assertEquals(allTasks.size(), returnTasks.size());
		
		// c: view 10 tasks
		returnTasks = logic.processInput(";view 10");
		assertEquals( Math.min(allTasks.size(),10),
				Math.min(allTasks.size(), returnTasks.size()) );
	}
	
	
	
	/**
	 * TEST CASES: TASK VIEW OPERATIONS
	 */

	@Test
	public void deleteTask() {

		LinkedList<Task> allTasks = LogicMain.getAllTasks();
		returnTasks = new LinkedList<Item>();
		
		Task task;
		Task deletedTask;

		// a: Delete with by task id
		task = allTasks.get(1);
		returnTasks = logic.processInput(";delete 2");
		deletedTask = (Task) returnTasks.get(0);
		
		assertEquals(task.getName()+task.getDescription()+task.getDeadline()+task.getReminder(),
				deletedTask.getName()+deletedTask.getDescription()+deletedTask.getDeadline()+deletedTask.getReminder());
		
		// b: Delete without specifying task id
		task = allTasks.get(0);
		returnTasks = logic.processInput(";delete");
		deletedTask = (Task) returnTasks.get(0);
		
		assertEquals(task.getName()+task.getDescription()+task.getDeadline()+task.getReminder(),
				deletedTask.getName()+deletedTask.getDescription()+deletedTask.getDeadline()+deletedTask.getReminder());
	}
}
