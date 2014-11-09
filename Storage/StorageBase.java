//@author A0112898U

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class StorageBase {
	
	//Constants
	protected static final String FILENAME_EXTENSION = ".ser";
	private final static String LOG_NAME = "Storage Base Logger";
	
	//Objects
	FileOutputStream fileOut;
	FileInputStream fileIn;
	ObjectOutputStream objectOut;
	ObjectInputStream objectIn;

	// Logger: Use to troubleshoot problems
	private static Logger logger = Logger.getLogger(LOG_NAME);
	
	//asbtract functions to be implemented by inherited objects
	abstract public String getFileName();
	abstract public void storeObject(Object obj);
	abstract protected Object retrieveObject();
	
	//@author A0112898U
	/**
	 * Serializes the object to be save and stores in a .ser file
	 * file's name, will prompt user if the given name is invalid.
	 * 
	 * @param filename - name of the file to be created i.e testfile.ser
	 * @param obj - object to be serialized and stored
	 */
	void serializeObject (String filename, Object obj) {
		
		try {
			fileOut = new FileOutputStream(filename + FILENAME_EXTENSION);
			
		} catch (FileNotFoundException e) {
			
			System.out.println("File not found! from serializeObject()"); 
			
		}
		
		try {
			objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(obj);
			objectOut.close();
			fileOut.close();
			
		} catch (IOException e) {
			logger.log(Level.WARNING, 
					"StorageBase: Error with IO in serializeObject()");
		}
		
				
	}
	

	
	//@author A0112898U
	/**
	 * Deserializes the file to retrieved stored object from a .ser file
	 * 
	 * @param filename - name of the file to be created i.e testfile.ser
	 * @return obj - returns a object (which is the deserialized list)
	 */
	Object deSerializeObject (String filename){
		Object obj = null;	
		
		//returns a null immediately if file is empty //Boundary case!
		if (isEmptyFile(filename)) {			
			return null;
		}
		
		try {
			fileIn = new FileInputStream(filename + FILENAME_EXTENSION);			
			objectIn = new ObjectInputStream(fileIn);
			obj = (Object) objectIn.readObject();
			objectIn.close();
			fileIn.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.log(Level.WARNING, 
					"StorageBase: File not found!");
		} catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.log(Level.WARNING, 
					"StorageBase: Class not found!");
		}
		return obj;
	}

	
	//@author A0112898U
	/**
	 * Checks for empty file
	 * 
	 * @param filename - name of the file to be Checked
	 * @return true if file is empty else false is returned
	 */
	private boolean isEmptyFile(String filename) {
		File storageFile = new File(filename + FILENAME_EXTENSION);
			
		if (storageFile.length() <= 0) {	
			return true;
		}
		return false;
	}
	
	
	
	//@author A0112898U
	/**
	 * Creates a new file for storage purpose if file doesn't exist
	 * 
	 * @param filename - name of the file to be created i.e testfile.ser
	 */
	protected void createNewFile(String filename) {
		File storageFile = new File(filename + FILENAME_EXTENSION);
		
		if (!storageFile.exists()) {
			try {
				//File not found, create a file
				storageFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				logger.log(Level.WARNING, 
						"StorageBase: File can't be created");
			}
		}
	}
}
