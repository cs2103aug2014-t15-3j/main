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
	
	private static final String FORMAT_NOT_SUPPORTED = "format currently not supported :D please wait for the next version";
	private static final String OBJECT_STORED = "Object Stored";
	private static final String OBJECT_RETRIEVED = "Object Retrieved";
	
	
	StorageTask taskStorage;
	StorageLabel labelStorage;
	
	StorageMain(){
		
		taskStorage = new StorageTask();
		labelStorage = new StorageLabel();
	}
	
	String storeObject(Object obj){
		
		//Still looking into generic classes instance compares
		if(obj instanceof LinkedList<?>){
			
			taskStorage.storeObject(obj);
			return OBJECT_STORED;		
		
		}else{
			
			System.out.print(FORMAT_NOT_SUPPORTED);
			
			return FORMAT_NOT_SUPPORTED;
		}
		
	}
	
	Object retrieveObject(Object obj){
		
		//Still looking into generic classes instance compares
		if(obj instanceof LinkedList<?>){
			
			return taskStorage.retrieveStoredTasks();		
		
		}else{
			
			System.out.print(FORMAT_NOT_SUPPORTED);
			
			return FORMAT_NOT_SUPPORTED;
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
