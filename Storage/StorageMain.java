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
// 1. Seems good!
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
	StorageTask taskStorage; // should be set to private and have getInstances after doing singleton
	StorageLabel labelStorage; // should be set to private and have getInstances after doing singleton
	

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
		
		switch (objType){
		
			case TYPE_TASK:
				
				return taskStorage.retrieveStoredTasks();	
				
			case TYPE_LABEL:
				
				return labelStorage.retrieveStoredLabels();
				
			default:
				
				return null;
		}
	}
}
