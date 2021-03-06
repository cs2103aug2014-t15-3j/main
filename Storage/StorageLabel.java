//@author A0112898U

import java.util.LinkedList;

public class StorageLabel extends StorageBase {
	
	//Constants
	private static final String STORAGE_LABEL_FILENAME = "StorageLabel";

	
	//@author A0112898U
	/**
	 * Constructor Call for StorageLabel 
	 * Also checks for the creation of the .ser storage file
	 */
	StorageLabel() {		
		createNewFile(STORAGE_LABEL_FILENAME);
	}
	
	
	//@author A0112898U
	/**
	 * Returns the filename of the .ser file
	 * 
	 * @return the allocated filename of the .ser file
	 */
	@Override
	public String getFileName() {
		return STORAGE_LABEL_FILENAME + FILENAME_EXTENSION;
	}
	
	
	//@author A0112898U
	/**
	 * Overrides base class storeObject() to implement
	 * calls a must method from the super class 
	 * - serializeObject for the object store
	 * 
	 * @param obj - object to be stored into the storage
	 */
	@Override
	public void storeObject(Object obj) {
		this.serializeObject(STORAGE_LABEL_FILENAME,obj);

	}
	
	
	//@author A0112898U
	/**
	 * Overrides base class retrieveObject() to implement
	 * calls a method from the super class 
	 * - deSerializeObject for the object retrieval
	 *
	 * @return LinkedList<Label> Returns a list of stored labels
	 */
	@Override
	protected LinkedList<Label> retrieveObject() {
		LinkedList<Label> storedLabels = new LinkedList<Label>();
		storedLabels = 
				(LinkedList<Label>) deSerializeObject(STORAGE_LABEL_FILENAME);
		return storedLabels;
	}
	
	
	//@author A0112898U
	/**
	 * API method retrieveStoredLabels() 
	 * for outside call to retrieve 'ALL' Stored Labels from the storage
	 * 
	 * @return LinkedList<Label> Returns a list of stored labels
	 */
	public LinkedList<Label> retrieveStoredLabels() {
		return retrieveObject();
	}

}
