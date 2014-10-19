//@author A0112898U

import java.io.IOException;
import java.util.LinkedList;

/*********************************************************************/
/******************* QA I - Refactor Code I***************************/
/*********************************************************************/
// @ChuanWei - StorageTask.java
//
// 1. 
//
//
//
//
//
// @ChuanWei - StorageTask.java
/*********************************************************************/
/*********************************************************************/

public class StorageTask extends StorageBase{
	
	//Constants
	private static final String STORAGE_TASK_FILENAME = "StorageTask";
	
	
	
	//@author A0112898U
	/**
	 * overrides base class storeObject() to implement
	 * calls a must method from the super class - serializeObject for the object store
	 * 
	 * @param obj - object to be stored into the storage
	 * 
	 */
	@Override
	void storeObject(Object obj){
		
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
		
		
		try {
			
			storedTasks = (LinkedList<Task>) deSerializeObject(STORAGE_TASK_FILENAME);
		
		} catch (ClassNotFoundException | IOException e) {
		
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/* FOR TESTING OBJECTS STORED*/
		/*
		for(Task task:storedTasks){
			
			System.out.println(task.getName());			
			System.out.println(task.getDescription());	
		}
		*/

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
	
	
	
	//@author A0112898U
	/**
	 * public overloaded method retrieveStoredTasks() 
	 * for outside call to retrieve Stored Task specified by the 
	 * queried date input from the storage
	 * 
	 * @param queryDate 'retrieval date' of tasks to be queried
	 * 
	 * @return queryTasks returns a LinkedList with all task categorized with the 
	 * same date as the queried date
	 * 
	 */
	public LinkedList<Task> retrieveStoredTasks(long queryDate){
		
		LinkedList<Task> storedTasks = retrieveObject();
		LinkedList<Task> queryTasks = new LinkedList<Task>();
		
		for(Task t:storedTasks){
			
			if(t.getDeadline() == queryDate){
				queryTasks.add(t);
			}
		}
		
		return queryTasks;
	}
	
	
	
	//@author A0112898U
	/**
	 * public overloaded method retrieveStoredTasks() 
	 * for outside call to retrieve Stored Task specified by the 
	 * queried Label input from the storage
	 * 
	 * @param queryLabel specifies the 'query label' that the tasks 
	 * are labeled under that user want to retrieve by
	 * 
	 * @return queryTasks returns a LinkedList with all task categorized under the 
	 * same label
	 * 
	 */
	public LinkedList<Task> retrieveStoredTasks(String queryLabel){
		
		LinkedList<Task> storedTasks = retrieveObject();
		LinkedList<Task> queryTasks = new LinkedList<Task>();
		
		for(Task t:storedTasks){
			
			if(t.getLabel().equals(queryLabel)){
				queryTasks.add(t);
			}
		}
		
		return queryTasks;
	}
	
}
