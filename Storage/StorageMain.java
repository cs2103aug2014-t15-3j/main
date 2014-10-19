//@author A0112898U
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

/*********************************************************************/
/******************* QA I - Refactor Code I***************************/
/*********************************************************************/
// @ChuanWei - StorageMain.java
//
// 1. 
//
//
//
//
//
// @ChuanWei - StorageMain.java
/*********************************************************************/
/*********************************************************************/

public class StorageMain {
	
	//Constants
	public static final String FORMAT_NOT_SUPPORTED = "format currently not supported :D please wait for the next version";
	public static final String TASKS_STORED = "Tasks Stored";
	public static final String LABELS_STORED = "Labels Stored";
	public static final String OBJECT_RETRIEVED = "Object Retrieved";
	
	
	
	//Storage Objects
	StorageTask taskStorage;
	StorageLabel labelStorage;
	

	//@author A0112898U
	/**
	 * Enum for retrival and storing methods 
	 * 
	 */
	public static enum OBJ_TYPES {
		
		TYPE_TASK,
		TYPE_LABEL
		
	}
	
	
	
	//@author A0112898U
	/**
	 * StorageMain's constructor, initiates storage object types
	 * 
	 */
	StorageMain(){
		
		taskStorage = new StorageTask();
		labelStorage = new StorageLabel();
	}
	
	
	
	//@author A0112898U
	/**
	 * methodCall - storeObject() for storing object into the storage
	 * 
	 * @param objType - the type of object that is to be stored 
	 * @param obj - object to be stored into the storage
	 * 
	 */
	String storeObject(OBJ_TYPES objType, Object obj){
		
		switch (objType) {
		
			case TYPE_TASK:
				
				taskStorage.storeObject(obj);
				
				return TASKS_STORED;	
				
			case TYPE_LABEL:
				
				labelStorage.storeObject(obj);
				
				return LABELS_STORED;
				
			default:
				
				System.out.print(FORMAT_NOT_SUPPORTED);
	
				return FORMAT_NOT_SUPPORTED;
		
		}
	}
	
	
	
	//@author A0112898U
	/**
	 * methodCall - retrieveObject() for stored object from the storage
	 * 
	 * @param objType - the type of object that is to be retrieved 
	 *  
	 */
	Object retrieveObject(OBJ_TYPES objType){
		
		switch (objType) {
		
			case TYPE_TASK:
				
				return taskStorage.retrieveStoredTasks();	
				
			case TYPE_LABEL:
				
				return labelStorage.retrieveStoredLabels();
				
			default:
				
				return null;
		}
		
	}
	
	
	
	//@author A0112898U
	/**
	 * methodCall - searchTask() for searching task by Date or by Label
	 * 
	 * @param byDate - indicate true if is retrieving task via dates 
	 * @param byLabel - indicate true if is retrieving task via labels
	 * @param queryParams - the date or label queried
	 * 
	 * @return the list of task/label with accordance to the query parameter
	 * 		   i.e. if queried for "test" labeled task, a linkedlist of task
	 * 		   that is labeled under "test" will be included in the linkedlist
	 */
	LinkedList<Task> searchTask(boolean byDate, boolean byLabel, String... queryParams){
		
		if(byDate){
			
			return taskStorage.retrieveStoredTasks(Long.parseLong(queryParams[0]));			
		
		}else if(byLabel){
		
			return taskStorage.retrieveStoredTasks(queryParams[0]);
		
		}
		
		return null;

	}
}
