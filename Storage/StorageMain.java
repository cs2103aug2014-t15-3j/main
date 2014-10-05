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


public class StorageMain {
	
	private static final String FORMAT_NOT_SUPPORTED = "format currently not supported :D please wait for the next version";
	private static final String OBJECT_STORED = "Object Stored";
	private static final String OBJECT_RETRIEVED = "Object Retrieved";
	
	StorageTask taskStorage;
	
	StorageMain(){
		
		taskStorage = new StorageTask();
	}
	
	String storeObject(Object obj){
		
		//Still looking into generic classes instance compares
		if(obj instanceof LinkedList<?>){
			
			taskStorage.storeObject(obj);
			return OBJECT_STORED;		
		}
		else{
			
			System.out.print(FORMAT_NOT_SUPPORTED);
			
			return FORMAT_NOT_SUPPORTED;
		}
		
	}
	
	String retrieveObject(Object obj){
		
		//Still looking into generic classes instance compares
		if(obj instanceof LinkedList<?>){
			
			taskStorage.retrieveStoredTasks();
			return OBJECT_RETRIEVED;		
		}
		else{
			
			System.out.print(FORMAT_NOT_SUPPORTED);
			
			return FORMAT_NOT_SUPPORTED;
		}
		
	}
}
