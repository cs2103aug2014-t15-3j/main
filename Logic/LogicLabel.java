import java.util.LinkedList;
import java.util.logging.Level;

public class LogicLabel {
	
	private LinkedList<LogicInputPair> inputList;
	private LinkedList<Task> returnTasks;
	
	public LogicLabel() {}
	
	public LinkedList<Task> processInput(LinkedList<LogicInputPair> _inputList) {
		
		inputList = _inputList;
		
		if(inputList.size() > 1) {
			
			String operation = inputList.get(1).getOperation();
			
			if (Operations.addOperations.contains(operation)) {

				return add();
			} else if (Operations.editOperations.contains(operation)) {

				return postEdit();
			} else if (Operations.viewOperations.contains(operation)) {

				return postView();
			} else if (Operations.findOperations.contains(operation)) {
				
				tempList = new LinkedList<Task>( postFind() );
				return postFind();
			} else if (Operations.deleteOperations.contains(operation)) {

				return postDelete();
			} else if (Operations.saveOperations.contains(operation)) {

				return postSave();
			} else {

				return new LinkedList<Task>();
			}
		}
	}
	
	private LinkedList<Task> add() {
		
		LinkedList<Task> returningTasks = new LinkedList<Task>();

		Task addTask = executeAdd();

		if (addTask != null) {
			Task returnTask = new Task(addTask);
			returnTask.editState(Operations.ADD_OPERATION);
			returningTasks.add(returnTask);
		}

		logger.log(Level.INFO, "Add operation completed");

		return returningTasks;
	}
}
