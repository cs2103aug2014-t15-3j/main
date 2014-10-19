//@author A0112898U

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
	
	//Objects
	FileOutputStream fileOut;
	FileInputStream fileIn;
	ObjectOutputStream objectOut;
	ObjectInputStream objectIn;

	
	//asbtract functions to be implemented by inherited objects
	abstract void storeObject(Object obj);
	abstract protected Object retrieveObject();
	
	
	
	void destructStorageBase(){
		
	}
	
	
	
	//@author A0112898U
	/**
	 * serializes the object to be save and stores in a .ser file
	 * file's name, will prompt user if the given name is invalid.
	 * 
	 * @param filename - name of the file to be created i.e testfile.ser
	 * @param obj - object to be serialized and stored
	 * 
	 */
	void serializeObject (String filename, Object obj) {
		
			try {
				
				fileOut = new FileOutputStream(filename+".ser");
				
			} catch (FileNotFoundException e) {
				
				// TODO Auto-generated catch block
				//e.printStackTrace(); // not sure if should have
				
				//MUST INTERACT WITH USER - RETURN STRING
				System.out.println("File not found! from serializeObject()"); 
				// user can create file is file not found 
				//can offer to create a file with the name 
				// change behaiviour for user, expect certain bahaivour from user
				
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
	

	
	//@author A0112898U
	/**
	 * deserializes the file to retrieved stored object from a .ser file
	 * 
	 * @param filename - name of the file to be created i.e testfile.ser
	 * 
	 */
	Object deSerializeObject (String filename, Object obj)throws IOException, ClassNotFoundException {
		
		fileIn = new FileInputStream(filename+".ser");
		objectIn = new ObjectInputStream(fileIn);
		
		obj = (Object) objectIn.readObject();
		
		objectIn.close();
		fileIn.close();
		
		return obj;
	}
}
