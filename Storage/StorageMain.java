/**
 * @author Bay Chuan Wei Candiie
 *
 */

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
	
	public static final String FORMAT_NOT_SUPPORTED = "format currently not supported :D please wait for the next version";
	public static final String TASKS_STORED = "Tasks Stored";
	public static final String LABELS_STORED = "Labels Stored";
	public static final String OBJECT_RETRIEVED = "Object Retrieved";
	
	
	StorageTask taskStorage;
	StorageLabel labelStorage;
	
	StorageMain(){
		
		taskStorage = new StorageTask();
		labelStorage = new StorageLabel();
	}
	
	public static enum OBJ_TYPES {
		
		TYPE_TASK,
		TYPE_LABEL
		
	}
	
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
	
	
	LinkedList<Task> searchTask(boolean byDate, boolean byLabel, String... queryParams){
		
		if(byDate){
			
			return taskStorage.retrieveStoredTasks(Long.parseLong(queryParams[0]));			
		
		}else if(byLabel){
		
			return taskStorage.retrieveStoredTasks(queryParams[0]);
		
		}
		
		return null;

	}
}
