//@author A0112898U

import java.io.File;
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
// 1. You should consider using Logger instead of System.out.print
//    for debugging (line 192)
//
//
//
//
// @ChuanWei - StorageBase.java
/*********************************************************************/
/*********************************************************************/


public abstract class StorageBase{
	
	//Constants
	protected static final String FILENAME_EXTENSION = ".ser";
	
	//Objects
	FileOutputStream fileOut;
	FileInputStream fileIn;
	ObjectOutputStream objectOut;
	ObjectInputStream objectIn;

	
	//asbtract functions to be implemented by inherited objects
	abstract public String getFileName();
	abstract public void storeObject(Object obj);
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
				
				fileOut = new FileOutputStream(filename + FILENAME_EXTENSION);
				
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
	Object deSerializeObject (String filename){
		
		Object obj = null;	
		
		//returns a null immediately if file is empty //Boundary case!
		if(isEmptyFile(filename)){
			
			return null;
			
		}
		
		try {
			
			fileIn = new FileInputStream(filename + FILENAME_EXTENSION);			
			objectIn = new ObjectInputStream(fileIn);
			obj = (Object) objectIn.readObject();
			objectIn.close();
			fileIn.close();
			
		}catch (FileNotFoundException e) {
			
			System.out.println("File not Found ...");
			e.printStackTrace();
			
		}catch (IOException e) {

			e.printStackTrace();
			
		}catch (ClassNotFoundException e) {
			
			System.out.println("Class not found!");
			e.printStackTrace();
		}
		
		return obj;
	}

	
	
	
	//@author A0112898U
	/**
	 * Checks for empty file
	 * 
	 * @param filename - name of the file to be Checked
	 * 
	 * @return true if file is empty else false is returned
	 * 
	 */
	private boolean isEmptyFile(String filename){
		
		File storageFile = new File(filename + FILENAME_EXTENSION);
				
		if(storageFile.length() <= 0){
			
			return true;
		}
		
		return false;
	}
	
	
	
	//@author A0112898U
	/**
	 * Creates a new file for storage purpose if file doesn't exist
	 * 
	 * @param filename - name of the file to be created i.e testfile.ser
	 * 
	 */
	protected void createNewFile(String filename){
		
		File storageFile = new File(filename + FILENAME_EXTENSION);
		
		if(!storageFile.exists()){
		
			try {
				
				System.out.println("File not Found ... Creating one now"); // Should assert here
				storageFile.createNewFile();
				
			} catch (IOException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Can't create file");
				
			}
			
		}
	}
}
