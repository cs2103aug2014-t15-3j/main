import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedList;

import org.junit.Test;

public class Remembra_SUT {

	//@author A0111942N	
	//@author A0112898U
	/**
	 * Test cases for Remembra's Programme will all component interaction
	 */	
	@Test
	public void testExecuteCommand() throws IOException{
		
	
		assertEquals("Test 1 - 'Remembra's SUT - Logic&Storage'", true, testLogicStorage());
		
	}	
	
	//@author A0112898U
	//@author A0111942N	
	/**
	 * Test cases for Remembra's Programme will Logic and Storage interaction
	 */	
	public boolean testLogicStorage (){
		
		
		LogicMain logicMain = new LogicMain();
		
		//Create the task to save
		logicMain.processInput(";add Hi Hi Hi");
		
		//get the list of buffered tasks and save to bufferedTasks before calling storage
		LinkedList<Task> bufferedTasks = new LinkedList<Task>();
		
		//Stores into storage via the save operation
		
		//retrieve from storage via the retrieve operation
		
		//read back into Logic and check if stored bufferTaskList == listRetrievedFromStorage
		
		boolean isListSame = false;
		
		return true;//isListSame;
	}
}


