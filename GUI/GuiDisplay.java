import java.util.LinkedList;


public class GuiDisplay {
	//Some very basic Help text for now.
	private static final String HELP_TEXT = "Commands:\n\n@!add - Add Tasks\n@!view - View Tasks\n@!delete - Delete Tasks\n@!edit - Edit Tasks\n\n";
	
	static void displayHelp(){
		//Ultimately the method will read an in-depth help text from a file stored. HELP_TEXT string is temporarily created. 
		GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+HELP_TEXT);
	}

	static void display(String inputStr) {
		LinkedList<Task> tasks = LogicMain.processInput(inputStr);
	
		if(!tasks.isEmpty()) {
	
			Task firstTask = tasks.get(0);
			String state = firstTask.getState();
	
			if(state.equals(OperationsConstant.ADD_OPERATION)) {
				GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
						"The following task has been added:\n\n" + 
						firstTask.toString());
			}
			else if(state.equals(OperationsConstant.EDIT_OPERATION)) {
				GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
						"The following task has been edited:\n\n" + 
						firstTask.toString());
			}
			else if(state.equals(OperationsConstant.VIEW_OPERATION)) {
	
				if(!firstTask.getName().equals(OperationsConstant.EMPTY_MESSAGE)) {
					for(int i=0; i<tasks.size(); i++) {
						Task tempTask = tasks.get(i);
						GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
								i + ".\n" + tempTask);
					}
				}
				else {
					GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
							"No tasks found!\n\n");
				}
			}
			else if(state.equals(OperationsConstant.DELETE_OPERATION)) {
	
				if(!firstTask.getName().equals(OperationsConstant.EMPTY_MESSAGE)) {
					GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
							"The following task has been deleted:\n\n" + 
							firstTask.toString());
				}
				else {
					GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
							"You have specified an invalid task to delete\n\n");
				}
			}
			else if(state.equals(OperationsConstant.SAVE_OPERATION)) {
				GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
						"The save is successful!\n\n");
			}
		}
		else {
	
			GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
					"Remembra doesn't understand your input!\n\n");
		}
	}
}