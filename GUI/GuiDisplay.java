import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JDialog;

/*********************************************************************/
/******************* QA I - Refactor Code I***************************/
/*********************************************************************/
// @Sankalp - GuiDisplay.java
//
// 1. could you add in some comments into the code?
//
// @Sankalp - GuiDisplay.java
/*********************************************************************/
/*********************************************************************/


public class GuiDisplay {

	static void displayHelp(String input) throws IOException {
		String helpFunction = input.substring(4).trim();
		GuiMain.feedback.setText(helpFunction);
		String fileContent;
		switch (helpFunction){
		case "":
			fileContent = readFile("HelpText.txt");
			GuiMain.feedback.setText(fileContent);
			//Something I am experimenting with for better GUI (for later)
			//			Dialog dialog = new Dialog(GuiMain.frameRemembra);
			//			DialogFX.fadeIn(dialog);
			break;
		case Operations.ADD_OPERATION:
			fileContent = readFile("HelpAdd.txt");
			GuiMain.feedback.setText(fileContent);
			break;
		case Operations.EDIT_OPERATION:
			fileContent = readFile("HelpEdit.txt");
			GuiMain.feedback.setText(fileContent);
			break;
		case Operations.DELETE_OPERATION:
			fileContent = readFile("HelpDelete.txt");
			GuiMain.feedback.setText(fileContent);
			break;
		case Operations.VIEW_OPERATION:
			fileContent = readFile("HelpView.txt");
			GuiMain.feedback.setText(fileContent);
			break;
		}
	}

	static void display(String inputStr) {
		LogicMain logic = new LogicMain();
		LinkedList<Item> tasks = logic.processInput(inputStr);


		assert(!inputStr.isEmpty()): "Input String was empty! Therefore Assertion Error!";
		if(!tasks.isEmpty()) {

			Item firstTask = tasks.get(0);
			String state = firstTask.getState();

			switch (state){
			case Operations.ADD_OPERATION:
				GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
						"The following task has been added:\n\n" + 
						firstTask.toString());
				GuiMain.feedback.setText("Task added successfully!");
				break;

			case Operations.EDIT_OPERATION:
				GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
						"The following task has been edited:\n\n" + 
						firstTask.toString());
				GuiMain.feedback.setText("Task edited successfully!");
				break;

			case Operations.VIEW_OPERATION:
			case Operations.FIND_OPERATION:
				if(!firstTask.getName().equals(Operations.EMPTY_MESSAGE)) {
					for(int i=0; i<tasks.size(); i++) {
						Item tempTask = tasks.get(i);
						GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
								i + ".\n" + tempTask);
					}
					GuiMain.feedback.setText("All tasks displayed!");
				}
				else {
					GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
							"No tasks found!\n\n");
				}
				break;

			case Operations.DELETE_OPERATION:	
				if(!firstTask.getName().equals(Operations.EMPTY_MESSAGE)) {
					GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
							"The following task has been deleted:\n\n" + 
							firstTask.toString());
					GuiMain.feedback.setText("Task deleted successfully!");
				}
				else {
					GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
							"You have specified an invalid task to delete\n\n");
				}
				break;

			case Operations.SAVE_OPERATION:
				GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
						"The save is successful!\n\n");
				break;
			}
		}else {

			GuiMain.mainDisplay.setText(GuiMain.mainDisplay.getText()+
					"Remembra doesn't understand your input!\n\n");
		}

	}
	static String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}
}