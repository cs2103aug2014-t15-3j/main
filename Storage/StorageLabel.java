//@author A0112898U

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;



/*********************************************************************/
/******************* QA I - Refactor Code I***************************/
/*********************************************************************/
// @ChuanWei - StorageLabel.java
//
// 1. 
//
//
//
//
//
// @ChuanWei - StorageLabel.java
/*********************************************************************/
/*********************************************************************/


public class StorageLabel extends StorageBase{
	
	//Constants
	private static final String STORAGE_LABEL_FILENAME = "StorageLabel";

	
	
	//@author A0112898U
	/**
	 * overrides base class storeObject() to implement
	 * calls a must method from the super class - serializeObject for the object store
	 * 
	 * @param obj - object to be stored into the storage
	 * 
	 */
	@Override
	void storeObject(Object obj){

		this.serializeObject(STORAGE_LABEL_FILENAME,obj); //if possible sort first!

	}
	
	
	
	//@author A0112898U
	/**
	 * overrides base class retrieveObject() to implement
	 * calls a method from the super class - deSerializeObject for the object retrieval
	 * 
	 */
	@Override
	protected LinkedList<Label> retrieveObject(){
		
		LinkedList<Label> storedLabels = new LinkedList<Label>();
	
		storedLabels = (LinkedList<Label>) deSerializeObject(STORAGE_LABEL_FILENAME);
	
		return storedLabels;
	}
	
	
	
	//@author A0112898U
	/**
	 * public method retrieveStoredLabels() 
	 * for outside call to retrieve 'ALL' Stored Labels from the storage
	 * 
	 */
	public LinkedList<Label> retrieveStoredLabels(){
		
		return retrieveObject();
	
	}

}
