
public class StorageMain {
	
	//Constants
	public static final String FORMAT_NOT_SUPPORTED = 
			"format currently not supported";
	public static final String TASKS_STORED = "Tasks Stored";
	public static final String LABELS_STORED = "Labels Stored";
	public static final String OBJECT_RETRIEVED = "Object Retrieved";
	
	
	//Storage Objects
	// should be set to private and have getInstances after doing singleton
	StorageTask taskStorage; 
	StorageLabel labelStorage;
	
	static StorageMain storageMain = new StorageMain();
	
	//@author A0112898U
	/**
	 * Enum for retrival and storing methods 
	 */
	public static enum OBJ_TYPES {
		TYPE_TASK,
		TYPE_LABEL
	}
	
	
	//@author A0112898U
	/**
	 * StorageMain's constructor, initiates storage object types
	 */
	private StorageMain() {
		taskStorage = new StorageTask();
		labelStorage = new StorageLabel();
	}
	
	
	
	//@author A0112898U
	/**
	 * StorageMain's Singleton's Accessor!
	 * 
	 * @return storageMain instance
	 */
	static public StorageMain getInstance() {
		return storageMain;
	}
	
	
	//@author A0112898U
	/**
	 * API - storeObject() for storing object into the storage
	 * 
	 * @param objType - the type of object that is to be stored 
	 * @param obj - object to be stored into the storage
	 * @return String - Msg to acknowledge object stored
	 */
	String storeObject(OBJ_TYPES objType, Object obj) {
		switch (objType) {
			case TYPE_TASK:
				taskStorage.storeObject(obj);
				return TASKS_STORED;	
				
			case TYPE_LABEL:
				labelStorage.storeObject(obj);
				return LABELS_STORED;
				
			default:
				return FORMAT_NOT_SUPPORTED;
		}
	}
	
	
	//@author A0112898U
	/**
	 * API - retrieveObject() for stored object from the storage
	 * 
	 * @param objType - the type of object that is to be retrieved 
	 * @return Object - returns the list of tasks from the .ser file 
	 */
	Object retrieveObject(OBJ_TYPES objType) {
		switch (objType) {		
			case TYPE_TASK:
				return taskStorage.retrieveStoredTasks();	
				
			case TYPE_LABEL:
				return labelStorage.retrieveStoredLabels();
				
			default:
				return null;
		}
	}
	
}
