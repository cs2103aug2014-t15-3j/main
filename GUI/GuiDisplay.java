import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

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
			fileContent = readFile("media/HelpText.txt");
			GuiMain.feedback.setText(fileContent);
			//Something I am experimenting with for better GUI (for later)
			//Dialog dialog = new Dialog(GuiMain.frameRemembra);
			//DialogFX.fadeIn(dialog);
			break;
		case Operations.ADD_OPERATION:
			fileContent = readFile("media/HelpAdd.txt");
			GuiMain.feedback.setText(fileContent);
			break;
		case Operations.EDIT_OPERATION:
			fileContent = readFile("media/HelpEdit.txt");
			GuiMain.feedback.setText(fileContent);
			break;
		case Operations.DELETE_OPERATION:
			fileContent = readFile("media/HelpDelete.txt");
			GuiMain.feedback.setText(fileContent);
			break;
		case Operations.VIEW_OPERATION:
			fileContent = readFile("media/HelpView.txt");
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
				GuiMain.feedback.setText(
						"The following task has been added:\n\n" + 
						firstTask.toString());
				break;

			case Operations.EDIT_OPERATION:
				GuiMain.feedback.setText(
						"The following task has been edited:\n\n" + 
						firstTask.toString());
				break;

			case Operations.VIEW_OPERATION:
			case Operations.FIND_OPERATION:
				findOperation(tasks, firstTask);
				break;

			case Operations.DELETE_OPERATION:	
				deleteOperation(firstTask);
				break;

			case Operations.SAVE_OPERATION:
				GuiMain.feedback.setText("The save is successful!\n");
				break;
			}
		}else {

			GuiMain.feedback.setText("Remembra doesn't understand your input!\n");
		}

	}

	private static void deleteOperation(Item firstTask) {
		if(!firstTask.getName().equals(Operations.EMPTY_MESSAGE)) {
			GuiMain.feedback.setText(
					"The following task has been deleted:\n\n" + 
					firstTask.toString());
			GuiMain.feedback.setText("Task deleted successfully!");
		}
		else {
			GuiMain.feedback.setText("You have specified an invalid\ntask to delete\n");
		}
	}

	private static void findOperation(LinkedList<Item> tasks, Item firstTask) {
		GuiMain.feedback.setText("All tasks displayed below:\n");
		if(!firstTask.getName().equals(Operations.EMPTY_MESSAGE)) {
			for(int i=0; i<tasks.size(); i++) {
				Item tempTask = tasks.get(i);
				GuiMain.feedback.setText(GuiMain.feedback.getText() +
						(i+1) + ".\n" + tempTask);
			}
		}
		else {
			GuiMain.feedback.setText("No Tasks Found!\n");
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
	static void initialize(){
		LogicMain logic = new LogicMain();
		logic.processInput(";");
		GuiMain.feedback.setText("Hi, there!\nThis is your feedback display.\n\nFor a quick guide, type help and\npress enter.\n\n\nAll your tasks, if any, are displayed\non the left.\n\n\n\n\n\n\n\n\n\nTo change tabs, press Alt+X\nwhere X is the tab no.");
		
	}
}