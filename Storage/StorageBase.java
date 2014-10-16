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

/*********************************************************************/
/******************* QA I - Refactor Code I***************************/
/*********************************************************************/
// @ChuanWei - StorageBase.java
//
// 1. 
//
//
//
//
//
// @ChuanWei - StorageBase.java
/*********************************************************************/
/*********************************************************************/


public abstract class StorageBase{
	
	FileOutputStream fileOut;
	FileInputStream fileIn;
	ObjectOutputStream objectOut;
	ObjectInputStream objectIn;

	
	void destructStorageBase(){
		
	}
	
	void serializeObject (String filename, Object obj) {
		
			try {
				
				fileOut = new FileOutputStream(filename+".ser");
				
			} catch (FileNotFoundException e) {
				
				// TODO Auto-generated catch block
				//e.printStackTrace(); // not sure if should have
				
				//
				System.out.println("File not found!");
			}
			
			//throws IOException, ClassNotFoundException
		
		try {
			
			objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(obj);
			objectOut.close();
			fileOut.close();
			
		} catch (IOException e) {
			
			//e.printStackTrace(); // not sure if should have
			System.out.println("Something wrong with IO in serializeObject()");
		}
		
				
	}
	
	Object deSerializeObject (String filename, Object obj)throws IOException, ClassNotFoundException {
		
		fileIn = new FileInputStream(filename+".ser");
		objectIn = new ObjectInputStream(fileIn);
		
		obj = (Object) objectIn.readObject();
		
		objectIn.close();
		fileIn.close();
		
		return obj;
	}
	
	abstract void storeObject(Object obj);
	abstract protected Object retrieveObject();
}
