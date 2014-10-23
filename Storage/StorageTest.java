
//@author A0112898

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class StorageTest {

	/**
	 * Test cases for Remembra's Storage
	 */	
	@Test
	public void testExecuteCommand() throws IOException{
		
		StorageMain storageMain = new StorageMain();
		
		
		//Test Cases - for storageMain init()
		assertEquals("Test - 'Remembra Storage .init()'", true,testStorageInit(storageMain));
		
		
		
		
		/* This is a boundary case for the �negative value� partition */
		
		
		
		/* This is a boundary case for the �negative value� partition */
		
	}	
	
	
	
	/**
	 * Test for storage initialization to make sure the task and label storages are initialized
	 * also to make sure that,upon initialization if the .ser files are not present, the initialization 
	 * should create those files automatically
	 * 
	 * 
	 * @param iterationSize
	 */	
	public boolean testStorageInit(StorageMain storageStub){
		
		if(storageStub.taskStorage == null){
			
			assert(storageStub.taskStorage == null) : "taskStorage is not initialized";
		
		}
		
		if(storageStub.labelStorage == null){

			assert(storageStub.labelStorage == null) : "labelStorage is not initialized";
			
		}
		
		
		//If pass the above cases, test for file creation in directories
		File testTaskStorageSer = new File(storageStub.taskStorage.getFileName());
		
		if (!testTaskStorageSer.exists()){
			
			assert(!testTaskStorageSer.exists()) : ".ser file for Task Storage is not created!";
			
			return false;
		}
		
		File testLabelStorageSer = new File(storageStub.labelStorage.getFileName());
		
		if (!testLabelStorageSer.exists()){
			
			assert(!testLabelStorageSer.exists()) : ".ser file for Label Storage is not created!";
			
			return false;
		}
		
		return true;
	}
	
	/**
	 * Test for storage add function, mainly to test for 
	 * error checking when storing files
	 * 
	 * @param iterationSize
	 */	
	public boolean testStorageStore(){
		
		return true;
	}
	
	/**
	 * Test for storage retrieve function, mainly to test for 
	 * error checking when retrieving files
	 * 
	 * @param iterationSize
	 */	
	public boolean testStorageRetrieve(){
		
		return true;
	}
	
	
}
