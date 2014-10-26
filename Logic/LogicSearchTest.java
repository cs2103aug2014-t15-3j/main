
//@author A0112898

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.LinkedList;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class LogicSearchTest {

	/**
	 * Test cases for Remembra's LogicSearch
	 */	
	@Test
	public void testExecuteCommand() throws IOException{
		
		//Test Cases - for LogicSearch
		//assertEquals("Test - 'Remembra Storage .init()'", true,testStorageInit(storageMain)); //keep as sample
		
		LinkedList<Task> testTasks = new LinkedList<Task>();
		
		for(int i = 0; i < 4; i++){
			//Task tempTask = new Task("t" + i , "task" + i);
			testTasks.add(new Task("t" + i , "task" + i));
		}
		
		//predicted test results
		LinkedList<Task> predictedResult = testTasks;
		assertEquals("Test - 'LogicSearch - startLetterSearch'", true, testStartLetterSearch("t",testTasks,predictedResult)); //keep as sample
		
		
	}	
	
	//@author A0112898U
	/**
	 * 
	 *
	 */	
	public static boolean testStartLetterSearch(String searchLine, LinkedList<Task> storedTasks, LinkedList<Task> predictedResult) {
		
		LinkedList<Task> collatedTask = new LinkedList<Task>();
		
		collatedTask = LogicSearch.smartSearch(searchLine, storedTasks, LogicSearch.SEARCH_TYPES.SEARCH_START_LETTER);
		
		if(collatedTask.equals(predictedResult)){
			return true;
		}
		
		for(Task t : collatedTask){
			System.out.println(t.getName());
		}
		
		return false;
	}
	
	public static void main (String[] args){
		
		LinkedList<Task> testTasks = new LinkedList<Task>();
		
		for(int i = 0; i < 4; i++){
			Task tempTask = new Task("t" + i , "task" + i + " lalala test");
			testTasks.add(tempTask);
		}
		
		//predicted test results
		LinkedList<Task> predictedResult = testTasks;
		testStartLetterSearch("t",testTasks,predictedResult); //keep as sample
		
		//System.out.println("hi");
		
	}
	
}
