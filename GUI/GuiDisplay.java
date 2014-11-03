import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.table.DefaultTableModel;

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

			Item firstItem = tasks.get(0);
			String state = firstItem.getState();
			switch (state){
			case Operations.ADD_OPERATION:
				GuiMain.feedback.setText(
						"The following task has been added:\n\n" + 
								firstItem.toString());
				updateTable();
				break;

			case Operations.EDIT_OPERATION:
				GuiMain.feedback.setText(
						"The following task has been edited:\n\n" + 
								firstItem.toString());
				updateTable();
				break;

			case Operations.VIEW_OPERATION:
			case Operations.FIND_OPERATION:
				viewOperation(tasks, firstItem, inputStr);
				updateSearchTable(tasks, firstItem);
				break;
			case Operations.DELETE_OPERATION:	
				deleteOperation(firstItem);
				updateTable();
				break;

			case Operations.SAVE_OPERATION:
				GuiMain.feedback.setText("The save is successful!\n");
				break;
			case Operations.ADD_LABEL_OPERATION:
				GuiMain.feedback.setText("The Following Label Has Been Added:\n\n" + firstItem.toString());
				break;
			case Operations.DELETE_LABEL_OPERATION:
				GuiMain.feedback.setText("The Following Label Has Been Deleted:\n\n" + firstItem.toString());
				break;
			case Operations.EDIT_LABEL_OPERATION:
				GuiMain.feedback.setText("The Following Label Has Been Edited:\n\n" + firstItem.toString());
				break;
			case Operations.VIEW_LABEL_OPERATION:
				viewLabelsOperation(tasks, firstItem);
				break;
			}
		}else {

			GuiMain.feedback.setText("Remembra doesn't understand your input!\n");
		}

	}

	//	private static void findOperation(LinkedList<Item> tasks, String str) {
	//		String[] s = str.split(" ");
	//		Item item = tasks.get(0);
	//		GuiMain.feedback.setText("Search results for \"" + s[s.length-1] + ":\n");
	//		if(!item.getName().equals(Operations.EMPTY_MESSAGE)) {
	//			for(int i=0; i<tasks.size(); i++) {
	//				Item tempTask = tasks.get(i);
	//				GuiMain.feedback.setText(GuiMain.feedback.getText() +
	//						(i+1) + ".\n" + tempTask);
	//			}
	//		}
	//		else {
	//			GuiMain.feedback.setText("No Tasks Found containing that keyword!\n");
	//		}
	//
	//	}

	private static void deleteOperation(Item firstTask) {
		if(!firstTask.getName().equals(Operations.EMPTY_MESSAGE)) {
			GuiMain.feedback.setText(
					"The following task has been deleted:\n\n" + 
							firstTask.toString());
			GuiMain.feedback.setText("Task deleted successfully!");
		}
		else {
			GuiMain.feedback.setText("You have specified an invalid task to delete\n");
		}
	}

	private static void viewOperation(LinkedList<Item> tasks, Item firstTask, String inputStr) {
		GuiMain.feedback.setText("All tasks displayed below:\n");
		if(!firstTask.getName().equals(Operations.EMPTY_MESSAGE)) {
			for(int i=0; i<tasks.size(); i++) {
				Item tempTask = tasks.get(i);
				GuiMain.feedback.setText(GuiMain.feedback.getText() +
						(i+1) + ".\n" + tempTask);
			}
		}
		else {
			GuiMain.feedback.setText("Sorry, No Tasks Found!\n");
		}
	}
	private static void viewLabelsOperation(LinkedList<Item> labels, Item firstLabel) {
		GuiMain.feedback.setText("All labels displayed below:\n");
		if(!firstLabel.getName().equals(Operations.EMPTY_MESSAGE)) {
			for(int i=0; i<labels.size(); i++) {
				Item tempLabel = labels.get(i);
				GuiMain.feedback.setText(GuiMain.feedback.getText() +
						(i+1) + ".\n" + tempLabel);
			}
		}
		else {
			GuiMain.feedback.setText("Sorry, No Labels Found!\n");
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
	
	/**
	 * Updates and displays the table.
	 */
	public static void updateTable() {
		DefaultTableModel tableModel = (DefaultTableModel) GuiMain.table_1.getModel();
		tableModel.setRowCount(0);
		LinkedList<Task> allTasks= LogicMain.getAllTasks();
		if (!(allTasks.size()==0)){
			Item firstTask = allTasks.get(0);

			GuiMain.myData = (DefaultTableModel) GuiMain.table_1.getModel();
			if(!firstTask.getName().equals(Operations.EMPTY_MESSAGE)) {

				for(int i=0; i<allTasks.size(); i++) {
					Task tempTask = allTasks.get(i);
					Object[] data = new Object[6];

					data[1] = i+1;
					data[2] = tempTask.getLabelName();
					data[3] = tempTask.getName();
					data[4] = tempTask.getDescription();
					data[5] = tempTask.getFormattedDeadline();

					GuiMain.myData.addRow(data);
				}
			}

			GuiMain.table_1.setModel(GuiMain.myData);

		}
	}
	
	/**
	 * Updates the table after search, showing the search results.
	 */
	public static void updateSearchTable(LinkedList<Item> items, Item firstTask){
		DefaultTableModel tableModel = (DefaultTableModel) GuiMain.table_1.getModel();
		tableModel.setRowCount(0);
		GuiMain.myData = (DefaultTableModel) GuiMain.table_1.getModel();
		if(!firstTask.getName().equals(Operations.EMPTY_MESSAGE)) {

			for(int i=0; i<items.size(); i++) {
				Task tempTask = (Task) items.get(i);
				Object[] data = new Object[6];

				data[1] = i+1;
				data[2] = tempTask.getLabelName();
				data[3] = tempTask.getName();
				data[4] = tempTask.getDescription();
				data[5] = tempTask.getFormattedDeadline();

				GuiMain.myData.addRow(data);
			}
		} else{
			Object[] data = new Object[1];
			data[1] = "No task found conatining the keyword.";
			GuiMain.myData.addRow(data);
		}

		GuiMain.table_1.setModel(GuiMain.myData);

	}
	static void initialize(){
		LogicMain logic = new LogicMain();
		logic.processInput(";");
		GuiMain.feedback.setText("Hi, there!\nThis is your feedback display.\n\nFor a quick guide, type help and\npress enter.\n\n\nAll of your tasks, if any, are displayed\non the left.\n\n\n\n\n\n\n\n\n\n\n\n\n\nTo change tabs, press Alt+X\nwhere X is the tab no.");
		updateTable();
	}
}