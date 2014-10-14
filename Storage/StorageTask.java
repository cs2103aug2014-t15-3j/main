import java.io.IOException;
import java.util.LinkedList;


public class StorageTask extends StorageBase{
	
	private static final String STORAGE_TASK_FILENAME = "StorageTask";
	
	
	@Override
	void storeObject(Object obj){
		
		try {
			
			this.serializeObject(STORAGE_TASK_FILENAME, obj);
		
		} catch (ClassNotFoundException | IOException e) {
		
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	protected LinkedList<Task> retrieveObject(){
		
		LinkedList<Task> storedTasks = new LinkedList<Task>();
		
		
		try {
			
			storedTasks = (LinkedList<Task>) deSerializeObject(STORAGE_TASK_FILENAME, storedTasks);
		
		} catch (ClassNotFoundException | IOException e) {
		
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/* FOR TESTING OBJECTS STORED*/
		/*
		for(Task task:storedTasks){
			
			System.out.println(task.getName());			
			System.out.println(task.getDescription());	
		}
		*/

		return storedTasks;
	}
	
	public LinkedList<Task> retrieveStoredTasks(){
		
		return retrieveObject();
	}
	
public LinkedList<Task> retrieveStoredTasks(long queryDate){
		
		LinkedList<Task> storedTasks = retrieveObject();
		LinkedList<Task> queryTasks = new LinkedList<Task>();
		
		for(Task t:storedTasks){
			
			if(t.getDeadline() == queryDate){
				queryTasks.add(t);
			}
		}
		
		return queryTasks;
	}
	
	public LinkedList<Task> retrieveStoredTasks(String queryLabel){
		
		LinkedList<Task> storedTasks = retrieveObject();
		LinkedList<Task> queryTasks = new LinkedList<Task>();
		
		for(Task t:storedTasks){
			
			if(t.getLabel().equals(queryLabel)){
				queryTasks.add(t);
			}
		}
		
		return queryTasks;
	}
	
}
