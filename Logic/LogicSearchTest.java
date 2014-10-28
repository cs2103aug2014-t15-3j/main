
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
		testCases_matchWordSearch();
		testCases_CombineSearch();
	}	
	

	//@author A0112898U
	/**
	 * Test cases/senarios for 'startLetterSearch' function
	 */	
	public void testCases_startLetterSearch(){
	
		LogicSearch.SEARCH_TYPES searchAlgoType = LogicSearch.SEARCH_TYPES.SEARCH_START_LETTER;
		
		//Init Test Type 1 - all test cases are init with the same datas except their indexes
		LinkedList<Task> testType1 = new LinkedList<Task>();
		
		for (int i = 0; i < 4; i++){
			testType1.add(new Task("t" + i , "task" + i + " lalala test"));
		}
		
		//predicted test results for testType 1
		LinkedList<Task> predictedResult_1_1 = new LinkedList<Task>(testType1);
		LinkedList<Task> predictedResult_1_2 = new LinkedList<Task>();

		/********************************************************************************************/
		/********************** START TEST TYPE 1 - TEST CASES **************************************/
		
		//Test for all applicable cases in 1st and 2nd token of the description respectively
		assertEquals("Test 1 - 'LogicSearch - startLetterSearch'", true, 
				testSearchAlgo("t",testType1,predictedResult_1_1,searchAlgoType));
		
		assertEquals("Test 2 - 'LogicSearch - startLetterSearch'", true, 
				testSearchAlgo("l",testType1,predictedResult_1_1,searchAlgoType));

		//Test for any applicable cases with many input token - only chars input should be checked
		assertEquals("Test 3 - 'LogicSearch - startLetterSearch'", true, 
				testSearchAlgo("9 think l",testType1,predictedResult_1_1,searchAlgoType));

		//Test for all NOT - applicable cases
		assertEquals("Test 4 - 'LogicSearch - startLetterSearch'", true, 
				testSearchAlgo("0",testType1,predictedResult_1_2,searchAlgoType));
		
		/* Equivalence Partition Test - Test for boundary case - no 'single character' in the search String */
		assertEquals("Test 5 - 'LogicSearch - startLetterSearch'", true, 
				testSearchAlgo("test lalala",testType1,predictedResult_1_2,searchAlgoType));
		
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
				testSearchAlgo("t",testType2,predictedResult_2_1,searchAlgoType));
		
		assertEquals("Test 7 - 'LogicSearch - startLetterSearch'", true, 
				testSearchAlgo("l",testType2,predictedResult_2_2,searchAlgoType));

		assertEquals("Test 8 - 'LogicSearch - startLetterSearch'", true, 
				testSearchAlgo("b",testType2,predictedResult_2_3,searchAlgoType));
		
		/************************ END TEST TYPE 2 - TEST CASES **************************************/
		/********************************************************************************************/
	}
	
	
	//@author A0112898U
	/**
	 * Test cases/senarios for 'matchWordSearch' function
	 */	
	public void testCases_matchWordSearch(){
	
		LogicSearch.SEARCH_TYPES searchAlgoType = LogicSearch.SEARCH_TYPES.SEARCH_MATCH_WORD;
		
		//Init Test Type 1 - all test cases are init with the same datas except their indexes
		LinkedList<Task> testType1 = new LinkedList<Task>();
		
		for (int i = 0; i < 4; i++){
			
			testType1.add(new Task("t" + i , "task" + i + " matchWord test"));
		}
		
		//predicted test results for testType 1
		LinkedList<Task> predictedResult_1_1 = new LinkedList<Task>(testType1);
		LinkedList<Task> predictedResult_1_2 = new LinkedList<Task>();

		/********************************************************************************************/
		/********************** START TEST TYPE 1 - TEST CASES **************************************/
		
		/* Equivalence Partition Test - Test for boundary case - 'single character' in the search String */
		//Test for 1 chacracter search input but match word doesn't search 1 char
		//assume users uses 1 char as the heading of the word to be search
		assertEquals("Test 1 - 'LogicSearch - testCases_matchWordSearch'", true, 
				testSearchAlgo("t",testType1,predictedResult_1_2,searchAlgoType));
		
		//Test for 2 chacracter search input but task doesn't exist in the list
		assertEquals("Test 2 - 'LogicSearch - testCases_matchWordSearch'", true, 
				testSearchAlgo("tt",testType1,predictedResult_1_2,searchAlgoType));

		//Test for any applicable cases with many input token - only chars input should be checked
		assertEquals("Test 3 - 'LogicSearch - testCases_matchWordSearch'", true, 
				testSearchAlgo("task",testType1,predictedResult_1_1,searchAlgoType));

		//Test for any duplicate result
		assertEquals("Test 4 - 'LogicSearch - testCases_matchWordSearch'", true, 
				testSearchAlgo("task match",testType1,predictedResult_1_1,searchAlgoType));
		
		/************************ END TEST TYPE 1 - TEST CASES **************************************/
		/********************************************************************************************/
	}
	
	
	//@author A0112898U
		/**
		 * Test cases/senarios for 'matchWordSearch' function
		 */	
		public void testCases_CombineSearch(){
		
			LogicSearch.SEARCH_TYPES searchAlgoType = LogicSearch.SEARCH_TYPES.TYPE_ALL;
			
			//Init Test Type 1 - all test cases are init with the same datas except their indexes
			LinkedList<Task> testType1 = new LinkedList<Task>();
			
			for (int i = 0; i < 4; i++){
				
				testType1.add(new Task("t" + i , "task" + i + " matchWord test"));
			}
			
			//predicted test results for testType 1
			LinkedList<Task> predictedResult_1_1 = new LinkedList<Task>(testType1);
			LinkedList<Task> predictedResult_1_2 = new LinkedList<Task>();

			/********************************************************************************************/
			/********************** START TEST TYPE 1 - TEST CASES **************************************/
			
			/* Equivalence Partition Test - Test for boundary case - 'single character' in the search String */
			//Test for 1 chacracter search input but match word doesn't search 1 char
			//assume users uses 1 char as the heading of the word to be search
			assertEquals("Test 1 - 'LogicSearch - testCases_CombineSearch'", true, 
					testSearchAlgo("t",testType1,predictedResult_1_1,searchAlgoType));
			
			//Test for combine search input but task doesn't exist in the list
			assertEquals("Test 2 - 'LogicSearch - testCases_CombineSearch'", true, 
					testSearchAlgo("tt e",testType1,predictedResult_1_2,searchAlgoType));

			//Test for any applicable cases with many input token - only chars input should be checked
			assertEquals("Test 3 - 'LogicSearch - testCases_CombineSearch'", true, 
					testSearchAlgo("task",testType1,predictedResult_1_1,searchAlgoType));

			//Test for any duplicate result
			assertEquals("Test 4 - 'LogicSearch - testCases_CombineSearch'", true, 
					testSearchAlgo("task match",testType1,predictedResult_1_1,searchAlgoType));

			//Test for combine search type in a String
			assertEquals("Test 5 - 'LogicSearch - testCases_CombineSearch'", true, 
					testSearchAlgo("t task match",testType1,predictedResult_1_1,searchAlgoType));			

			/************************ END TEST TYPE 1 - TEST CASES **************************************/
			/********************************************************************************************/
		}
		
	
	//@author A0112898U
	/**
	 * Test for 'startLetterSearch' function | 'matchWordSearch' function | all Type function combined
	 */	
	public static boolean testSearchAlgo(String searchLine, LinkedList<Task> storedTasks, LinkedList<Task> predictedResult, LogicSearch.SEARCH_TYPES searchAlgoType) {
		
		LinkedList<Task> collatedTask = new LinkedList<Task>();
		
		collatedTask = LogicSearch.smartSearch(searchLine, storedTasks, LogicSearch.SEARCH_TYPES.TYPE_ALL, searchAlgoType);
		
		
		//For debugging purposes
		for(Task t : collatedTask){
			System.out.println(t.getName());
		}
		
		System.out.println("hi");
		
		if(collatedTask.equals(predictedResult)){
			return true;
		}
		

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
		//testSearchAlgo("t",testTasks,predictedResult,LogicSearch.SEARCH_TYPES.SEARCH_START_LETTER); //keep as sample
		testSearchAlgo("s t match task",testTasks,predictedResult,LogicSearch.SEARCH_TYPES.TYPE_ALL); //keep as sample
		
				
		System.out.println("hi");
		
	}
	
	
}
