//@author A0112898U

import java.util.LinkedList;


public class StorageTask extends StorageBase {
	
	//Constants
	private static final String STORAGE_TASK_FILENAME = "StorageTask";
	
	//@author A0112898U
	/**
	 * Constructor of StorageTask, creates a storage file
	 * if file doesn't exist.
	 */
	StorageTask() {
		createNewFile(STORAGE_TASK_FILENAME);
	}
	
	
	//@author A0112898U
	/**
	 * Returns the filename of the .ser file
	 * 
	 * @return the allocated filename of the .ser file
	 */
	@Override
	public String getFileName() {
		return STORAGE_TASK_FILENAME + FILENAME_EXTENSION;
	}
	
	
	
	//@author A0112898U
	/**
	 * overrides base class storeObject() to implement
	 * calls a must method from the super class - serializeObject for the object store
	 * 
	 * @param obj - object to be stored into the storage
	 */
	@Override
	public void storeObject(Object objToStore) {
		this.serializeObject(STORAGE_TASK_FILENAME, objToStore);
	}
	
	
	
	//@author A0112898U
	/**
	 * Overrides base class protected method retrieveObject() to 
	 * implement calls a method from the super class 
	 * - deSerializeObject for the object retrieval
	 * 
	 * @return LinkedList with all the Stored Task from the storage
	 */
	@Override
	protected LinkedList<Task> retrieveObject() {
		LinkedList<Task> storedTasks = new LinkedList<Task>();
		storedTasks = (LinkedList<Task>) deSerializeObject(STORAGE_TASK_FILENAME);
		
		return storedTasks;
	}
	
	
	
	//@author A0112898U
	/**
	 * Getter method to retrieve 'ALL' Stored Task from the storage
	 * 
	 * @return LinkedList return all Stored Task from the storage
	 */
	public LinkedList<Task> retrieveStoredTasks() {
		return retrieveObject();
	}	
}
