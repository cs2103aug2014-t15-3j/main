
//@author A0112898

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class LogicSearchTest {

	//@author A0112898U
	/**
	 * Test cases for Remembra's LogicSearch
	 */	
	@Test
	public void testExecuteCommand() throws IOException{
		
		//Test Cases - for LogicSearch
		//assertEquals("Test - 'Remembra Storage .init()'", true,testStorageInit(storageMain)); //keep as sample
			
		testCases_startLetterSearch();
	
	}	
	

	//@author A0112898U
	/**
	 * Test cases/senarios for 'startLetterSearch' function
	 */	
	public void testCases_startLetterSearch(){
	
		//Init Test Type 1 - all test cases are init with the same datas except their indexes
		LinkedList<Task> testType1 = new LinkedList<Task>();
		
		for (int i = 0; i < 4; i++){
			testType1.add(new Task("t" + i , "task" + i + " lalala test"));
		}
		
		//Convert to set to get the same format
		Set<Task> testSet = new HashSet<Task>(testType1);
		
		//predicted test results for testType 1
		LinkedList<Task> predictedResult_1_1 = new LinkedList<Task>(testSet);
		LinkedList<Task> predictedResult_1_2 = new LinkedList<Task>();

		/********************************************************************************************/
		/********************** START TEST TYPE 1 - TEST CASES **************************************/
		
		//Test for all applicable cases in 1st and 2nd token of the description respectively
		assertEquals("Test 1 - 'LogicSearch - startLetterSearch'", true, 
					testStartLetterSearch("t",testType1,predictedResult_1_1));
		
		assertEquals("Test 2 - 'LogicSearch - startLetterSearch'", true, 
					testStartLetterSearch("l",testType1,predictedResult_1_1));

		//Test for any applicable cases with many input token - only chars input should be checked
		assertEquals("Test 3 - 'LogicSearch - startLetterSearch'", true, 
					testStartLetterSearch("9 think l",testType1,predictedResult_1_1));

		//Test for all NOT - applicable cases
		assertEquals("Test 4 - 'LogicSearch - startLetterSearch'", true, 
					testStartLetterSearch("0",testType1,predictedResult_1_2));
		
		/* Equivalence Partition Test - Test for boundary case - no 'single character' in the search String */
		assertEquals("Test 5 - 'LogicSearch - startLetterSearch'", true, 
					testStartLetterSearch("test lalala",testType1,predictedResult_1_2));
		
		/************************ END TEST TYPE 1 - TEST CASES **************************************/
		/********************************************************************************************/

		/********************************************************************************************/
		/********************** START TEST TYPE 2 - TEST CASES **************************************/
		
		//Init Test Type 2 - alternate test cases are init with the same datas except their indexes
		LinkedList<Task> testType2 = new LinkedList<Task>();
		
		//Predicted test results for testType2
		LinkedList<Task> predictedResult_2_1 = new LinkedList<Task>();
		LinkedList<Task> predictedResult_2_2 = new LinkedList<Task>();
		LinkedList<Task> predictedResult_2_3 = new LinkedList<Task>();
		
		for (int i = 4; i < 8; i++){
			
			if (i%2 == 0){
				Task t = new Task("t" + i , "task" + i + " lalala test");
				testType2.add(t);
				predictedResult_2_2.add(t);
				
			}else {
				Task t = new Task("t" + i , "task" + i + " bababa test");
				testType2.add(t);			
				predictedResult_2_3.add(t);
			}
		}

		predictedResult_2_1 = testType2;
		
		
		//Test for all applicable cases in 1st and 2nd token of the description respectively
		assertEquals("Test 6 - 'LogicSearch - startLetterSearch'", true, 
					testStartLetterSearch("t",testType2,predictedResult_2_1));
		
		assertEquals("Test 7 - 'LogicSearch - startLetterSearch'", true, 
				testStartLetterSearch("l",testType2,predictedResult_2_2));

		assertEquals("Test 8 - 'LogicSearch - startLetterSearch'", true, 
				testStartLetterSearch("b",testType2,predictedResult_2_2));
		
		/************************ END TEST TYPE 2 - TEST CASES **************************************/
		/********************************************************************************************/
	}
	
	
	//@author A0112898U
	/**
	 * Test for 'startLetterSearch' function
	 */	
	public static boolean testStartLetterSearch(String searchLine, LinkedList<Task> storedTasks, LinkedList<Task> predictedResult) {
		
		LinkedList<Task> collatedTask = new LinkedList<Task>();
		
		collatedTask = LogicSearch.smartSearch(searchLine, storedTasks, LogicSearch.SEARCH_TYPES.TYPE_ALL);
		
		if(collatedTask.equals(predictedResult)){
			return true;
		}
		
		//For debugging purposes
		for(Task t : collatedTask){
			System.out.println(t.getName());
		}
		
		System.out.println("I came here");
		
		return false;
	}
	
	//Used for debuggin purpsoe
	public static void main (String[] args){
		
		LinkedList<Task> testTasks = new LinkedList<Task>();
		
		for(int i = 0; i < 4; i++){
			Task tempTask = new Task("t" + i , "task" + i + " lalala test");
			testTasks.add(tempTask);
		}
		
		//predicted test results
		LinkedList<Task> predictedResult = testTasks;
		testStartLetterSearch("1",testTasks,predictedResult); //keep as sample
		
		//System.out.println("hi");
		
	}
	
	
}
