import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;


public class LogicTest {
	
	private LogicMain logic;
	private LinkedList<Task> tasks;
	private LinkedList<Label> labels;

	@Before
	public void setUp() throws Exception {
		
		// Initialize
		logic = new LogicMain();
		
		// Get tasks and labels
		tasks = new LinkedList<Task> ( LogicMain.getAllTasks() );
		labels = new LinkedList<Label> ( LogicMain.getAllLabels() );
	}
	
	/**
	 * TEST CASES: TASK ADD OPERATIONS
	 */
	
	// Corner case 1: User types ";add" only
	@Test
	public void addTaskEmpty() {
		
		LinkedList<Item> returnTasks;
		returnTasks = logic.processInput(";add");
		
		assertTrue( returnTasks.size() == 0 );
	}
	
	// Corner case 2: User types ";add <Task Name>" only
	@Test
	public void addTaskName() {

		LinkedList<Item> returnTasks;
		String taskName = "Bring Amanda home!";
		returnTasks = logic.processInput(";add " + taskName);
		
		Task addedTask = (Task) returnTasks.get(0);
		
		System.out.println(addedTask.toString());
		
		assertTrue( addedTask.getName().contains(taskName) );
	}
	
	// Corner case 3: User types ";add <Task Name>" only
	@Test
	public void addTaskNameAndDescription() {

		LinkedList<Item> returnTasks;
		String taskName = "Bring Amanda home!";
		String taskDescription = "Cause she is forever lost...";
		returnTasks = logic.processInput(";add " + taskName + " ;i " + taskDescription );

		Task addedTask = (Task) returnTasks.get(0);

		System.out.println(addedTask.toString());

		assertTrue( addedTask.getName().contains(taskName)
					&& addedTask.getDescription().contains(taskDescription));
	}
}
