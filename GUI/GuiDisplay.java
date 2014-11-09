import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
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
	private final static String task = "Task :";
	private final static String deadline = "Deadline :";
	private final static String label = "Label :";
	private final static String desc = "Description :";

	static void displayHelp(String input) throws IOException {
		String helpFunction = input.substring(4).trim();
		GuiMain.feedback.setText(helpFunction);
		String fileContent;
		switch (helpFunction){
		case "":
			fileContent = readFile("media/HelpText.txt");
			GuiMain.feedback.setText(fileContent);
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


		//assert(!inputStr.isEmpty()): "Input String was empty! Therefore Assertion Error!";
		if(!tasks.isEmpty()) {

			Item firstItem = tasks.get(0);
			Task firstTask = (Task) firstItem;
			String state = firstItem.getState();
			System.out.print(state); //For testing
			switch (state){
			case Operations.ADD_OPERATION:
				GuiMain.feedback.setText("The following task has been added:\n\n" + 
								firstItem.toString());
				updateTable();
				Dialog d = new Dialog(GuiMain.frameRemembra, "Following Task Added Successfully!", 
						task + firstTask.getName(), desc + firstTask.getDescription(), label + 
						firstTask.getLabelName(), deadline + firstTask.getFormattedDeadline(), "add");
				d.displayDialog();
				DialogFX.fadeIn(d);
				break;

			case Operations.EDIT_OPERATION:
				GuiMain.feedback.setText("The following task has been edited:\n\n" + firstItem.toString() + 
						"\n\n\nEnter ; to view all tasks.");
				updateTable();
				Dialog d1 = new Dialog(GuiMain.frameRemembra, "Following Task Has Been Edited!", 
						task + firstTask.getName(), desc + firstTask.getDescription(), 
						label + firstTask.getLabelName(), deadline+ firstTask.getFormattedDeadline(), "edit");
				d1.displayDialog();
				DialogFX.fadeIn(d1);
				break;
				
			case Operations.VIEW_OPERATION:
				GuiMain.feedback.setText("All tasks displayed below:\n");
				viewOperation(tasks, firstItem);
				updateTable();
				GuiMain.tabbedPane.setSelectedComponent(GuiMain.todoTab);
				GuiMain.tabbedPane.getSelectedComponent();
				break;
				
			case Operations.VIEW_FLOAT_OPERATION:
				GuiMain.feedback.setText("All floating tasks displayed below:\n");
				viewOperation(tasks, firstItem);
				displayFloatingTasksTable(tasks, firstItem);
				GuiMain.tabbedPane.setSelectedComponent(GuiMain.todoTab);
				GuiMain.tabbedPane.getSelectedComponent();
				break;
				
			case Operations.VIEW_ALL_OPERATION:
				GuiMain.feedback.setText("All tasks displayed in the 'All Tasks' Tab!");
				updateTable();
				GuiMain.tabbedPane.setSelectedComponent(GuiMain.allTab);
				GuiMain.tabbedPane.getSelectedComponent();
				break;
				
			case Operations.VIEW_DONE_OPERATION:
				updateTable();
				GuiMain.tabbedPane.setSelectedComponent(GuiMain.doneTab);
				GuiMain.tabbedPane.getSelectedComponent();
				break;
				
			case Operations.FIND_OPERATION:
				updateSearchTable(tasks, firstItem);
				findOperation(tasks, inputStr);
				GuiMain.tabbedPane.setSelectedComponent(GuiMain.searchTab);
				GuiMain.tabbedPane.getSelectedComponent();
				break;
				
			case Operations.FIND_ERROR:
				//System.out.print("why not working!!");
				updateSearchTable(tasks, firstItem);
				findOperation(tasks, inputStr);
				break;
			case Operations.DELETE_OPERATION:	
				deleteOperation(firstItem);
				updateTable();
				Dialog d3 = new Dialog(GuiMain.frameRemembra, "Following Task Deleted Successfully!", 
						task + firstTask.getName(), desc + firstTask.getDescription(), 
						label + firstTask.getLabelName(), deadline + firstTask.getFormattedDeadline(), "delete");
				d3.displayDialog();
				DialogFX.fadeIn(d3);
				break;
			case Operations.DONE_OPERATION:
				GuiMain.feedback.setText("The task has been marked done!\nCongratulations on completing it!\n:)" 
						+ "\n\n\n\n\n\nEnter ; to view all tasks.");
				updateTable();
				GuiMain.tabbedPane.setSelectedComponent(GuiMain.doneTab);
				GuiMain.tabbedPane.getSelectedComponent();
				break;
			case Operations.UNDO_OPERATION:
				GuiMain.feedback.setText("Undo successful!\n" + "\n\n\n\n\n\n\nEnter ; to view all tasks.");
				updateTable();
				break;
			case Operations.SAVE_OPERATION:
				GuiMain.feedback.setText("Everything saved successfully!\n"+ "\n\n\n\n\n\n\nEnter ; to view all tasks.");
				Dialog d4 = new Dialog(GuiMain.frameRemembra, "Save Successful!", 
						"", "", "", "", "save");
				d4.displayDialog();
				DialogFX.fadeIn(d4);
				break;
			case Operations.ADD_LABEL_OPERATION:
				GuiMain.feedback.setText("The Following Label Has Been Added:\n\n" + firstItem.toString() 
						+ "\n\n\n\n\n\n\nEnter ; to view all tasks.");
				break;
			case Operations.DELETE_LABEL_OPERATION:
				GuiMain.feedback.setText("The Following Label Has Been Deleted:\n\n" + firstItem.toString() 
						+ "\n\n\n\n\n\n\nEnter ; to view all tasks.");
				break;
			case Operations.EDIT_LABEL_OPERATION:
				GuiMain.feedback.setText("The Following Label Has Been Edited:\n\n" + firstItem.toString() 
						+ "\n\n\n\n\n\n\nEnter ; to view all tasks.");
				break;
			case Operations.VIEW_LABEL_OPERATION:
				viewLabelsOperation(tasks, firstItem);
				break;
			}
		}else {

			GuiMain.feedback.setText("Remembra doesn't understand your input!\n");
		}

	}



	private static void displayFloatingTasksTable(LinkedList<Item> items, Item firstTask) {
		DefaultTableModel tableModel = (DefaultTableModel) GuiMain.todoTable.getModel();
		tableModel.setRowCount(0);
		GuiMain.myData = (DefaultTableModel) GuiMain.todoTable.getModel();
		if(!(firstTask.getName().equals(Operations.EMPTY_MESSAGE))) {

			for(int i=0; i<items.size(); i++) {
				Task tempTask = (Task) items.get(i);
				Object[] data = new Object[7];

				data[1] = i+1;
				if (tempTask.getLabelName().equalsIgnoreCase("<empty>")){
					data[2] = "";

				} else {
					data[2] = tempTask.getLabelName();
				}
				data[3] = tempTask.getName();
				data[4] = tempTask.getDescription();

				if (!(tempTask.getFormattedStart().isEmpty())){
					data[5] = tempTask.getFormattedStart() + " " + tempTask.getFormattedEnd();
				}else {
					data[5] = tempTask.getFormattedDeadline();
				}

				data[6] = tempTask.getDone();
				if(!(data[2].toString().isEmpty())){
					Color color = LogicMain.getLabelColor(tempTask.getLabel());
					data[0] = color;//will b the color function
				} else {
					data[0] = Color.WHITE;
				}
				GuiMain.myData.addRow(data);
			}
		} else{
			Object[] data = new Object[7];
			data[3] = "No floating tasks found.";
			GuiMain.myData.addRow(data);
		}

		GuiMain.todoTable.setModel(GuiMain.myData);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.LEFT);

		GuiMain.todoTable.setModel(GuiMain.myData);
		GuiMain.todoTable.getColumnModel().getColumn(3).setCellRenderer(new MyCellRenderer());
		GuiMain.todoTable.getColumnModel().getColumn(4).setCellRenderer(new MyCellRenderer());
		GuiMain.todoTable.getColumnModel().getColumn(5).setCellRenderer(new MyCellRenderer());
		GuiMain.todoTable.setDefaultRenderer(Color.class, new CellColorRenderer());
		GuiMain.todoTable.setDefaultRenderer(String.class, centerRenderer);
		GuiMain.todoTable.setDefaultRenderer(Integer.class, centerRenderer);
		
	}

	private static void findOperation(LinkedList<Item> tasks, String str) {
		String[] s = str.split(" ");
		Item item = tasks.get(0);
		GuiMain.feedback.setText("Search results for \"" + s[s.length-1] + "\":\n");
		if(!(item.getName().equals(Operations.EMPTY_MESSAGE))) {
			for(int i=0; i<tasks.size(); i++) {
				Item tempTask = tasks.get(i);
				GuiMain.feedback.setText(GuiMain.feedback.getText() +
						(i+1) + ".\n" + tempTask + "--------------------x-------------------\n");

			}
			GuiMain.feedback.setText(GuiMain.feedback.getText() + "\n\n\nEnter ; to view all tasks.");
		}
		else {
			GuiMain.feedback.setText("No Tasks Found containing that keyword!\n");
		}

	}

	private static void deleteOperation(Item firstTask) {
		if(!(firstTask.getName().equals(Operations.EMPTY_MESSAGE))) {
			GuiMain.feedback.setText(
					"The following task has been deleted:\n\n" + 
							firstTask.toString());
			GuiMain.feedback.setText("Task deleted successfully!");
		}
		else {
			GuiMain.feedback.setText("You have specified an invalid task to delete\n");
		}
	}

	private static void viewOperation(LinkedList<Item> tasks, Item firstTask) {
		if(!(firstTask.getName().equals(Operations.EMPTY_MESSAGE))) {
			for(int i=0; i<tasks.size(); i++) {
				Item tempTask = tasks.get(i);
				GuiMain.feedback.setText(GuiMain.feedback.getText() +
						(i+1) + ".\n" + tempTask  + "--------------------x-------------------\n");
			}
		}
		else {
			GuiMain.feedback.setText("Sorry, No Tasks Found!\n");
		}
	}
	private static void viewLabelsOperation(LinkedList<Item> labels, Item firstLabel) {
		GuiMain.feedback.setText("All Labels Displayed Below:\n");
		if(!(firstLabel.getName().equals(Operations.EMPTY_MESSAGE))) {
			for(int i=0; i<labels.size(); i++) {
				Item tempLabel = labels.get(i);
				GuiMain.feedback.setText(GuiMain.feedback.getText() +
						(i+1) + ".\n" + tempLabel  + "--------------------x-------------------\n");
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

		DefaultTableModel tableModel = (DefaultTableModel) GuiMain.todoTable.getModel();
		tableModel.setRowCount(0);
		DefaultTableModel tableModel2= (DefaultTableModel) GuiMain.doneTable.getModel();
		tableModel2.setRowCount(0);
		DefaultTableModel tableModel3= (DefaultTableModel) GuiMain.allTable.getModel();
		tableModel3.setRowCount(0);
		
		LinkedList<Task> allTasks= LogicMain.getAllTasks();
		if (!(allTasks.size()==0)){
			Item firstTask = allTasks.get(0);

			GuiMain.myData = (DefaultTableModel) GuiMain.todoTable.getModel();
			GuiMain.myDoneData = (DefaultTableModel) GuiMain.doneTable.getModel();
			GuiMain.allData = (DefaultTableModel) GuiMain.allTable.getModel();
			if(!firstTask.getName().equals(Operations.EMPTY_MESSAGE)) {
				for(int i=0; i<allTasks.size(); i++) {
					Task tempTask = allTasks.get(i);
					Object[] data = new Object[7];

					data[1] = i+1;
					if (tempTask.getLabelName().equalsIgnoreCase("<empty>")){
						data[2] = "";

					} else {
						data[2] = tempTask.getLabelName();
					}
					data[3] = tempTask.getName();
					data[4] = tempTask.getDescription();

					if (!(tempTask.getFormattedStart().isEmpty())){
						data[5] = tempTask.getFormattedStart() + " " + tempTask.getFormattedEnd();
					}else {
						data[5] = tempTask.getFormattedDeadline();
					}

					data[6] = tempTask.getDone();
					if(!(data[2].toString().isEmpty())){
						Color color = LogicMain.getLabelColor(tempTask.getLabel());
						data[0] = color;//will b the color function
					} else {
						data[0] = Color.WHITE;
					}
					GuiMain.allData.addRow(data);
					if (tempTask.getDone()) {
						GuiMain.myDoneData.addRow(data);
					} else {
						GuiMain.myData.addRow(data);
					}


				}
			}
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.LEFT);

			GuiMain.todoTable.setModel(GuiMain.myData);
			GuiMain.todoTable.getColumnModel().getColumn(3).setCellRenderer(new MyCellRenderer());
			GuiMain.todoTable.getColumnModel().getColumn(4).setCellRenderer(new MyCellRenderer());
			GuiMain.todoTable.getColumnModel().getColumn(5).setCellRenderer(new MyCellRenderer());
			GuiMain.todoTable.setDefaultRenderer(Color.class, new CellColorRenderer());
			GuiMain.todoTable.setDefaultRenderer(String.class, centerRenderer);
			GuiMain.todoTable.setDefaultRenderer(Integer.class, centerRenderer);

			GuiMain.doneTable.setModel(GuiMain.myDoneData);
			GuiMain.doneTable.getColumnModel().getColumn(3).setCellRenderer(new MyCellRenderer());
			GuiMain.doneTable.getColumnModel().getColumn(4).setCellRenderer(new MyCellRenderer());
			GuiMain.doneTable.getColumnModel().getColumn(5).setCellRenderer(new MyCellRenderer());
			GuiMain.doneTable.setDefaultRenderer(Color.class, new CellColorRenderer());
			GuiMain.doneTable.setDefaultRenderer(String.class, centerRenderer);
			GuiMain.doneTable.setDefaultRenderer(Integer.class, centerRenderer);
			
			GuiMain.allTable.setModel(GuiMain.allData);
			GuiMain.allTable.getColumnModel().getColumn(3).setCellRenderer(new MyCellRenderer());
			GuiMain.allTable.getColumnModel().getColumn(4).setCellRenderer(new MyCellRenderer());
			GuiMain.allTable.getColumnModel().getColumn(5).setCellRenderer(new MyCellRenderer());
			GuiMain.allTable.setDefaultRenderer(Color.class, new CellColorRenderer());
			GuiMain.allTable.setDefaultRenderer(String.class, centerRenderer);
			GuiMain.allTable.setDefaultRenderer(Integer.class, centerRenderer);

		}
	}

	/**
	 * Updates the table after search, showing the search results.
	 */
	public static void updateSearchTable(LinkedList<Item> items, Item firstTask){
		DefaultTableModel tableModel = (DefaultTableModel) GuiMain.searchTable.getModel();
		tableModel.setRowCount(0);
		GuiMain.searchData = (DefaultTableModel) GuiMain.searchTable.getModel();
		if(!(firstTask.getName().equals(Operations.EMPTY_MESSAGE))) {

			for(int i=0; i<items.size(); i++) {
				Task tempTask = (Task) items.get(i);
				Object[] data = new Object[7];

				data[1] = i+1;
				if (tempTask.getLabelName().equalsIgnoreCase("<empty>")){
					data[2] = "";

				} else {
					data[2] = tempTask.getLabelName();
				}
				data[3] = tempTask.getName();
				data[4] = tempTask.getDescription();

				if (!(tempTask.getFormattedStart().isEmpty())){
					data[5] = tempTask.getFormattedStart() + " " + tempTask.getFormattedEnd();
				}else {
					data[5] = tempTask.getFormattedDeadline();
				}

				data[6] = tempTask.getDone();
				if(!(data[2].toString().isEmpty())){
					Color color = LogicMain.getLabelColor(tempTask.getLabel());
					data[0] = color;//will b the color function
				} else {
					data[0] = Color.WHITE;
				}
				GuiMain.searchData.addRow(data);
			}
		} else{
			Object[] data = new Object[7];
			data[3] = "No task found conatining the keyword.";
			GuiMain.searchData.addRow(data);
		}

		GuiMain.searchTable.setModel(GuiMain.searchData);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.LEFT);

		GuiMain.searchTable.setModel(GuiMain.searchData);
		GuiMain.searchTable.getColumnModel().getColumn(3).setCellRenderer(new MyCellRenderer());
		GuiMain.searchTable.getColumnModel().getColumn(4).setCellRenderer(new MyCellRenderer());
		GuiMain.searchTable.getColumnModel().getColumn(5).setCellRenderer(new MyCellRenderer());
		GuiMain.searchTable.setDefaultRenderer(Color.class, new CellColorRenderer());
		GuiMain.searchTable.setDefaultRenderer(String.class, centerRenderer);
		GuiMain.searchTable.setDefaultRenderer(Integer.class, centerRenderer);
		

	}
	static void initialize(){
		LogicMain logic = new LogicMain();
		logic.processInput(";");
		GuiMain.feedback.setText("Hi, there!\nThis is your feedback display.\n\nFor a quick guide, type help and\npress enter.\n\n\nAll of your tasks, if any, are displayed on the left.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nTo change tabs, press Alt+X\nwhere X is the tab no.");
		updateTable();
	}
}