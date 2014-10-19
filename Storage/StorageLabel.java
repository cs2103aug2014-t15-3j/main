import java.io.IOException;
import java.util.LinkedList;



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
	
	private static final String STORAGE_LABEL_FILENAME = "StorageLabel";

	
	@Override
	void storeObject(Object obj){
		this.serializeObject(STORAGE_LABEL_FILENAME,obj);
	}
	
	
	@Override
	protected LinkedList<Label> retrieveObject(){
		
		LinkedList<Label> storedLabels = new LinkedList<Label>();
	
		try {
			
			storedLabels = (LinkedList<Label>) deSerializeObject(STORAGE_LABEL_FILENAME, storedLabels);
		
		} catch (ClassNotFoundException e) {
		
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.out.println("File doesn't exist");
		
		} catch (IOException e) {
		
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.out.println("File doesn't exist");
		}
		return storedLabels;
	}
	
	public LinkedList<Label> retrieveStoredLabels(){
		
		return retrieveObject();
	}

}
