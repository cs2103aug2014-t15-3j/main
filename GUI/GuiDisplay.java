import java.util.LinkedList;

/*********************************************************************/
/******************* QA I - Refactor Code I***************************/
/*********************************************************************/
// @Sankalp - GuiDisplay.java
//
// 1. could you add in some comments into the code?
// 2. perhaps you can consider using switch cases for the checks in
//    the display() function
// 3. for lines > 80 char do indent them to the next line i.e. line 20
//
// @Sankalp - GuiDisplay.java
/*********************************************************************/
/*********************************************************************/


public class GuiDisplay {
	//Some very basic Help text for now.
	private static final String HELP_TEXT = "Commands:\n\n@!add - Add Tasks\n@!view - View Tasks\n@!delete - Delete Tasks\n@!edit - Edit Tasks\n\n";
	
	static void displayHelp(){
		//Ultimately the method will read an in-depth help text from a file stored. HELP_TEXT string is temporarily created. 
		GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+HELP_TEXT);
	}

	static void display(String inputStr) {
		LogicMain logic = new LogicMain();
		LinkedList<Task> tasks = logic.processInput(inputStr);
	
		if(!tasks.isEmpty()) {
	
			Task firstTask = tasks.get(0);
			String state = firstTask.getState();
	
			if(state.equals(Operations.ADD_OPERATION)) {
				GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
						"The following task has been added:\n\n" + 
						firstTask.toString());
			}
			else if(state.equals(Operations.EDIT_OPERATION)) {
				GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
						"The following task has been edited:\n\n" + 
						firstTask.toString());
			}
			else if(state.equals(Operations.VIEW_OPERATION)) {
	
				if(!firstTask.getName().equals(Operations.EMPTY_MESSAGE)) {
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
			else if(state.equals(Operations.DELETE_OPERATION)) {
	
				if(!firstTask.getName().equals(Operations.EMPTY_MESSAGE)) {
					GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
							"The following task has been deleted:\n\n" + 
							firstTask.toString());
				}
				else {
					GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
							"You have specified an invalid task to delete\n\n");
				}
			}
			else if(state.equals(Operations.SAVE_OPERATION)) {
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