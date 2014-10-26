//@author A0112898U

import java.io.IOException;
import java.util.LinkedList;

/*********************************************************************/
/******************* QA I - Refactor Code I***************************/
/*********************************************************************/
// @ChuanWei - StorageTask.java
//
// 1. Positioning of {
//    
//    The correct way is to leave a space between function name and {
//    Example: function() {
//
//
// @ChuanWei - StorageTask.java
/*********************************************************************/
/*********************************************************************/

public class StorageTask extends StorageBase{
	
	//Constants
	private static final String STORAGE_TASK_FILENAME = "StorageTask";
	
	
	StorageTask(){
		createNewFile(STORAGE_TASK_FILENAME);
	}
	
	
	//@author A0112898U
	/**
	 * Returns the filename of the .ser file
	 * 
	 * @return the allocated filename of the .ser file
	 * 
	 */
	@Override
	public String getFileName(){
		
		return STORAGE_TASK_FILENAME + FILENAME_EXTENSION;
	}
	
	
	
	//@author A0112898U
	/**
	 * overrides base class storeObject() to implement
	 * calls a must method from the super class - serializeObject for the object store
	 * 
	 * @param obj - object to be stored into the storage
	 * 
	 */
	@Override
	public void storeObject(Object obj){
		
		this.serializeObject(STORAGE_TASK_FILENAME, obj);
	
	}
	
	
	
	//@author A0112898U
	/**
	 * overrides base class protected method retrieveObject() to implement
	 * calls a method from the super class - deSerializeObject for the object retrieval
	 * 
	 * @return LinkedList with all the Stored Task from the storage
	 * 
	 */
	@Override
	protected LinkedList<Task> retrieveObject(){
		
		LinkedList<Task> storedTasks = new LinkedList<Task>();
		
		
		storedTasks = (LinkedList<Task>) deSerializeObject(STORAGE_TASK_FILENAME);

		return storedTasks;
	}
	
	
	
	//@author A0112898U
	/**
	 * public method retrieveStoredTasks() 
	 * for outside call to retrieve 'ALL' Stored Task from the storage
	 * 
	 * @return LinkedList with all the Stored Task from the storage
	 * 
	 */
	public LinkedList<Task> retrieveStoredTasks(){
		
		return retrieveObject();
	}	
}
