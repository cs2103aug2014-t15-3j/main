import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
/*
 * Class to handle major display functionality
 */
public class GuiDisplay {

	private static final String HELP_VIEW_TXT = "media/HelpView.txt";
	private static final String HELP_DELETE_TXT = "media/HelpDelete.txt";
	private static final String HELP_EDIT_TXT = "media/HelpEdit.txt";
	private static final String HELP_ADD_TXT = "media/HelpAdd.txt";
	private static final String HELP_TXT = "media/HelpText.txt";
	private static final String HELP_SAVE_TXT = "media/HelpSave.txt";
	private static final String HELP_REMIND_TXT = "media/HelpRemind.txt";
	private static final String HELP_LABEL_TXT = "media/HelpLabel.txt";
	
	private static final String WELCOME_MESSAGE = "Hi, there!\nThis is your "
			+ "feedback display.\n\nFor a quick guide, type help "
			+ "and\npress enter.\n\n\nAll of your tasks, if any, are"
			+ " displayed on the left.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
			+ "To change tabs, press Alt+X\nwhere X is the tab no.";
	private static final String ERROR_MESSAGE = "Remembra doesn't "
			+ "understand your input!\n";
	private static final String DELETE_ERROR_MESSAGE = "You have specified an"
			+ " invalid task to delete\n";
	private static final String VIEW_UNCOMPLETED_TASKS = "\n\n\nEnter ;view to "
			+ "view uncompleted tasks.";
	private static final String DONE_SUCCESSFULLY = "The task has been marked done!"
			+ "\nCongratulations on completing it!\n:)" 
			+ "\n\n\n\n\n\nEnter ;view to view uncompleted tasks.";
	private static final String TASK_DELETED_SUCCESSFULLY = "Following Task Deleted Successfully!";
	private static final String TASK_EDITED_SUCCESSFULLY = "Following Task Has Been Edited!";
	private static final String TASK_ADDED_SUCCESSFULLY = "Following Task Added Successfully!";

	private final static String task = "Task :";
	private final static String deadline = "Deadline :";
	private final static String label = "Label :";
	private final static String desc = "Description :";

	private final static String TASK_ADDED = "The following task"
			+ " has been added:\n\n";
	private final static String TASK_EDITED = "The following task"
			+ " has been edited:\n\n";
	private final static String TASK_DELETED = "The following task"
			+ " has been deleted:\n\n";
	private final static String LABEL_ADDED = "The following label"
			+ " has been added:\n\n";
	private final static String LABEL_DELETED = "The following label"
			+ " has been deleted:\n\n";
	private final static String LABEL_EDITED = "The following label"
			+ " has been edited:\n\n";
	private final static String VIEW_ALL = ";view ;all";
	private static final String VIEW_DONE = ";view ;done";
	private static final String VIEW_NOT_DONE = ";view ;!done";
	private static final String VIEW = ";view";

	//@author A0116160W
	/**
	 * Displays specific help by reading through the input string to find any 
	 * function name following 'help'.
	 * 
	 * @param inputStr
	 */
	static void displayHelp(String input) throws IOException {
		String helpFunction = input.substring(4).trim();
		GuiMain.feedback.setText("Try a valid help option next");
		String fileContent;
		switch (helpFunction){
		case "":
			fileContent = readFile(HELP_TXT);
			GuiMain.feedback.setText(fileContent);
			break;
		case Operations.ADD_OPERATION:
			fileContent = readFile(HELP_ADD_TXT);
			GuiMain.feedback.setText(fileContent);
			break;
		case Operations.EDIT_OPERATION:
			fileContent = readFile(HELP_EDIT_TXT);
			GuiMain.feedback.setText(fileContent);
			break;
		case Operations.DELETE_OPERATION:
			fileContent = readFile(HELP_DELETE_TXT);
			GuiMain.feedback.setText(fileContent);
			break;
		case Operations.VIEW_OPERATION:
			fileContent = readFile(HELP_VIEW_TXT);
			GuiMain.feedback.setText(fileContent);
			break;
		case "remind":
			fileContent = readFile(HELP_REMIND_TXT);
			GuiMain.feedback.setText(fileContent);
			break;
		case "label":
			fileContent = readFile(HELP_LABEL_TXT);
			GuiMain.feedback.setText(fileContent);
			break;
		case "save":
			fileContent = readFile(HELP_SAVE_TXT);
			GuiMain.feedback.setText(fileContent);
			break;
		}
	}

	//@author A0116160W
	/**
	 * Reads the input string and displays lists provided by LogicMain
	 * according to the operation requested in input string
	 * 
	 * @param inputStr
	 */
	static void display(String inputStr) {
		LogicMain logic = new LogicMain();
		LinkedList<Item> tasks = logic.processInput(inputStr);
		LinkedList<Item> allTasks = 
				new  LinkedList<Item> (LogicMain.getAllTasks());

		assert(!inputStr.isEmpty()): "Input String was empty! Assertion Error!";

		if(!tasks.isEmpty()) {

			Item firstItem = tasks.get(0);
			String state = firstItem.getState();

			switch (state){
			case Operations.ADD_OPERATION:
				Task firstTask = (Task) firstItem;
				GuiMain.feedback.setText(TASK_ADDED + 
						firstItem.toString());
				updateTodoTable(logic.processInput(VIEW_NOT_DONE));
				updateDoneTable(logic.processInput(VIEW_DONE));
				updateAllTable(allTasks);

				//Acknowledgement pop-up
				Dialog d = new Dialog(GuiMain.frameRemembra,
						TASK_ADDED_SUCCESSFULLY, 
						task + firstTask.getName(), desc + 
						firstTask.getDescription(), label + 
						firstTask.getLabelName(), deadline + 
						firstTask.getFormattedDeadline(), "add");
				d.displayDialog();
				DialogFX.fadeIn(d);

				//To display the tab where action happened
				GuiMain.tabbedPane.setSelectedComponent(GuiMain.todoTab);
				GuiMain.tabbedPane.getSelectedComponent();
				break;

			case Operations.EDIT_OPERATION:
				Task firstTask1 = (Task) firstItem;
				GuiMain.feedback.setText(TASK_EDITED 
						+ firstItem.toString() + 
						VIEW_UNCOMPLETED_TASKS);
				updateTodoTable(logic.processInput(VIEW_NOT_DONE));
				updateDoneTable(logic.processInput(VIEW_DONE));
				updateAllTable(allTasks);

				Dialog d1 = new Dialog(GuiMain.frameRemembra, 
						TASK_EDITED_SUCCESSFULLY, 
						task + firstTask1.getName(), desc +
						firstTask1.getDescription(), 
						label + firstTask1.getLabelName(), 
						deadline+ firstTask1.getFormattedDeadline(), "edit");
				d1.displayDialog();
				DialogFX.fadeIn(d1);
				break;

			case Operations.VIEW_OPERATION:
				GuiMain.feedback.setText("All uncompleted tasks displayed!");

				viewOperation(tasks, firstItem);
				displayFloatingTasksTable(tasks, firstItem);

				GuiMain.tabbedPane.setSelectedComponent(GuiMain.todoTab);
				GuiMain.tabbedPane.getSelectedComponent();
				break;

			case Operations.VIEW_TASK_OPERATION:
				GuiMain.feedback.setText(firstItem.toString());
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

				updateAllTable(tasks);

				GuiMain.tabbedPane.setSelectedComponent(GuiMain.allTab);
				GuiMain.tabbedPane.getSelectedComponent();
				break;

			case Operations.VIEW_DONE_OPERATION:
				updateDoneTable(tasks);

				GuiMain.tabbedPane.setSelectedComponent(GuiMain.doneTab);
				GuiMain.tabbedPane.getSelectedComponent();
				break;

			case Operations.FIND_OPERATION:
				updateSearchTable(tasks, firstItem);
				findOperation(tasks, inputStr);

				GuiMain.tabbedPane.setSelectedComponent(GuiMain.searchTab);
				GuiMain.tabbedPane.getSelectedComponent();

				Operations.lastSearch = inputStr;
				break;

			case Operations.FIND_ERROR:
				updateSearchTable(tasks, firstItem);
				findOperation(tasks, inputStr);
				break;

			case Operations.DELETE_OPERATION:	
				Task firstTask3 = (Task) firstItem;

				deleteOperation(firstItem);
				updateTodoTable(logic.processInput(VIEW_NOT_DONE));
				updateDoneTable(logic.processInput(VIEW_DONE));
				updateAllTable(allTasks);

				Dialog d3 = new Dialog(GuiMain.frameRemembra,
						TASK_DELETED_SUCCESSFULLY, 
						task + firstTask3.getName(), desc + 
						firstTask3.getDescription(), 
						label + firstTask3.getLabelName(),
						deadline + firstTask3.getFormattedDeadline(), 
						"delete");
				d3.displayDialog();
				DialogFX.fadeIn(d3);
				break;
			case Operations.DONE_OPERATION:
				GuiMain.feedback.setText(DONE_SUCCESSFULLY);

				updateTodoTable(logic.processInput(VIEW_NOT_DONE));
				updateDoneTable(logic.processInput(VIEW_DONE));
				updateAllTable(allTasks);

				GuiMain.tabbedPane.setSelectedComponent(GuiMain.doneTab);
				GuiMain.tabbedPane.getSelectedComponent();
				break;
			case Operations.UNDO_OPERATION:
				GuiMain.feedback.setText("Undo successful!\n" 
						+ "\n\n\n" + VIEW_UNCOMPLETED_TASKS);

				updateTodoTable(logic.processInput(VIEW_NOT_DONE));
				updateDoneTable(logic.processInput(VIEW_DONE));
				updateAllTable(allTasks);
				break;
			case Operations.SAVE_OPERATION:
				GuiMain.feedback.setText("Everything saved successfully!\n"
						+ "\n\n\n" + VIEW_UNCOMPLETED_TASKS);

				Dialog d4 = new Dialog(GuiMain.frameRemembra, "Save Successful!", 
						"", "", "", "", "save");
				d4.displayDialog();
				DialogFX.fadeIn(d4);
				break;
			case Operations.ADD_LABEL_OPERATION:
				GuiMain.feedback.setText(LABEL_ADDED + firstItem.toString() 
						+ "\n\n\n" + VIEW_UNCOMPLETED_TASKS);
				break;
			case Operations.DELETE_LABEL_OPERATION:
				GuiMain.feedback.setText(LABEL_DELETED + firstItem.toString() 
						+ "\n\n\n" + VIEW_UNCOMPLETED_TASKS);
				break;
			case Operations.EDIT_LABEL_OPERATION:
				GuiMain.feedback.setText(LABEL_EDITED+ firstItem.toString() 
						+ "\n\n\n" + VIEW_UNCOMPLETED_TASKS);
				break;
			case Operations.VIEW_LABEL_OPERATION:
				viewLabelsOperation(tasks, firstItem);
				break;
			default:
				GuiMain.feedback.setText(ERROR_MESSAGE);
			}
			//Reload view
			if (!state.equals(Operations.SAVE_OPERATION)) {
				switch (Operations.viewState) {

				case Operations.ALL_STATE:
					tasks = logic.processInput(VIEW_ALL);
					displayFloatingTasksTable(tasks, firstItem);
					break;
				case Operations.TODO_STATE:
					tasks = logic.processInput(VIEW);
					displayFloatingTasksTable(tasks, firstItem);
					break;
				case Operations.DONE_STATE:
					tasks = logic.processInput(VIEW_DONE);
					updateDoneTable(tasks);
					break;
				case Operations.FIND_STATE:
					tasks = logic.processInput(Operations.lastSearch);
					GuiDisplay.updateSearchTable(tasks, tasks.get(0));

				}
			}

		}else {
			GuiMain.feedback.setText(ERROR_MESSAGE);
		}

	}

	//@author A0116160W
	/**
	 * Update the done table in the done tab
	 * 
	 * @param done tasks
	 */
	protected static void updateDoneTable(LinkedList<Item> tasks) {
		DefaultTableModel tableModel = 
				(DefaultTableModel) GuiMain.doneTable.getModel();
		tableModel.setRowCount(0);

		if (!(tasks.size()==0)){
			Item firstTask = tasks.get(0);
			GuiMain.myDoneData = 
					(DefaultTableModel) GuiMain.doneTable.getModel();
			if(!firstTask.getName().equals(Operations.EMPTY_MESSAGE)) {
				for(int i=0; i<tasks.size(); i++) {
					Task tempTask = (Task) tasks.get(i);
					Object[] data = new Object[7];

					data[1] = i+1;
					if (tempTask.getLabelName().equalsIgnoreCase("<empty>")) {
						data[2] = "";

					} else {
						data[2] = tempTask.getLabelName();
					}
					data[3] = tempTask.getName();
					data[4] = tempTask.getDescription();

					if (!(tempTask.getFormattedStart().isEmpty())){
						data[5] = tempTask.getFormattedStart() + 
								" to " + tempTask.getFormattedEnd();
					}else {
						data[5] = tempTask.getFormattedDeadline();
					}

					data[6] = tempTask.getDone();
					if(!(data[2].toString().isEmpty())){
						Color color = LogicMain.getLabelColor(tempTask.getLabel());
						data[0] = color;
					} else {
						data[0] = Color.WHITE;
					}

					GuiMain.myDoneData.addRow(data);

				}
			}
			DefaultTableCellRenderer centerRenderer = 
					new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.LEFT);

			GuiMain.doneTable.setModel(GuiMain.myDoneData);
			GuiMain.doneTable.getColumnModel().
			getColumn(3).setCellRenderer(new MyCellRenderer());
			GuiMain.doneTable.getColumnModel().
			getColumn(4).setCellRenderer(new MyCellRenderer());
			GuiMain.doneTable.getColumnModel().
			getColumn(5).setCellRenderer(new MyCellRenderer());
			GuiMain.doneTable.setDefaultRenderer(Color.class, 
					new CellColorRenderer());
			GuiMain.doneTable.setDefaultRenderer(String.class, 
					centerRenderer);
			GuiMain.doneTable.setDefaultRenderer(Integer.class, 
					centerRenderer);
		}
	}

	//@author A0116160W
	/**
	 * Update the 'all' table in the 'all' tab
	 * 
	 * @param all tasks
	 */
	private static void updateAllTable(LinkedList<Item> tasks) {
		DefaultTableModel tableModel =
				(DefaultTableModel) GuiMain.allTable.getModel();
		tableModel.setRowCount(0);
		if (!(tasks.size()==0)){
			Item firstTask = tasks.get(0);
			GuiMain.allData = 
					(DefaultTableModel) GuiMain.allTable.getModel();
			if(!firstTask.getName().equals(Operations.EMPTY_MESSAGE)) {
				for(int i=0; i<tasks.size(); i++) {
					Task tempTask = (Task) tasks.get(i);
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
						data[5] = tempTask.getFormattedStart() + " to " 
								+ tempTask.getFormattedEnd();
					}else {
						data[5] = tempTask.getFormattedDeadline();
					}

					data[6] = tempTask.getDone();
					if(!(data[2].toString().isEmpty())){
						Color color = 
								LogicMain.getLabelColor(tempTask.getLabel());
						data[0] = color;
					} else {
						data[0] = Color.WHITE;
					}

					GuiMain.allData.addRow(data);

				}
			}
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.LEFT);

			GuiMain.allTable.setModel(GuiMain.allData);
			GuiMain.allTable.getColumnModel().getColumn(3).
			setCellRenderer(new MyCellRenderer());
			GuiMain.allTable.getColumnModel().getColumn(4).
			setCellRenderer(new MyCellRenderer());
			GuiMain.allTable.getColumnModel().getColumn(5).
			setCellRenderer(new MyCellRenderer());
			GuiMain.allTable.setDefaultRenderer(Color.class, 
					new CellColorRenderer());
			GuiMain.allTable.setDefaultRenderer(String.class, 
					centerRenderer);
			GuiMain.allTable.setDefaultRenderer(Integer.class, 
					centerRenderer);
		}
	}

	//@author A0116160W
	/**
	 * Update the 'todo' table in the 'todo' tab
	 * 
	 * @param all uncompleted tasks
	 */
	private static void updateTodoTable(LinkedList<Item> tasks) {
		DefaultTableModel tableModel = 
				(DefaultTableModel) GuiMain.todoTable.getModel();
		tableModel.setRowCount(0);
		if (!(tasks.size()==0)){
			Item firstTask = tasks.get(0);

			GuiMain.myData = (DefaultTableModel) GuiMain.todoTable.getModel();

			if(!firstTask.getName().equals(Operations.EMPTY_MESSAGE)) {
				for(int i=0; i<tasks.size(); i++) {
					Task tempTask = (Task) tasks.get(i);
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
						data[5] = tempTask.getFormattedStart() +
								" to " + tempTask.getFormattedEnd();
					}else {
						data[5] = tempTask.getFormattedDeadline();
					}

					data[6] = tempTask.getDone();
					if(!(data[2].toString().isEmpty())){
						Color color = 
								LogicMain.getLabelColor(tempTask.getLabel());
						data[0] = color;
					} else {
						data[0] = Color.WHITE;
					}

					GuiMain.myData.addRow(data);

				}
			}
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.LEFT);

			GuiMain.todoTable.setModel(GuiMain.myData);
			GuiMain.todoTable.getColumnModel().getColumn(3).
			setCellRenderer(new MyCellRenderer());
			GuiMain.todoTable.getColumnModel().getColumn(4).
			setCellRenderer(new MyCellRenderer());
			GuiMain.todoTable.getColumnModel().getColumn(5).
			setCellRenderer(new MyCellRenderer());
			GuiMain.todoTable.setDefaultRenderer(Color.class, 
					new CellColorRenderer());
			GuiMain.todoTable.setDefaultRenderer(String.class, 
					centerRenderer);
			GuiMain.todoTable.setDefaultRenderer(Integer.class, 
					centerRenderer);
		}
	}

	//@author A0116160W
	/**
	 * Update the 'todo' table in the 'todo' tab
	 * 
	 * @param all floating tasks
	 * @param first task on the list
	 */
	protected static void displayFloatingTasksTable(LinkedList<Item> items, 
			Item firstTask) {
		DefaultTableModel tableModel = 
				(DefaultTableModel) GuiMain.todoTable.getModel();
		tableModel.setRowCount(0);
		GuiMain.myData = 
				(DefaultTableModel) GuiMain.todoTable.getModel();
		if (!(firstTask.getName().equals(Operations.EMPTY_MESSAGE))) {

			for (int i=0; i<items.size(); i++) {
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
					data[5] = tempTask.getFormattedStart() 
							+ " to " + tempTask.getFormattedEnd();
				}else {
					data[5] = tempTask.getFormattedDeadline();
				}

				data[6] = tempTask.getDone();
				if(!(data[2].toString().isEmpty())){
					Color color = LogicMain.getLabelColor(tempTask.getLabel());
					data[0] = color;
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
		DefaultTableCellRenderer centerRenderer =
				new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.LEFT);

		GuiMain.todoTable.setModel(GuiMain.myData);
		GuiMain.todoTable.getColumnModel().getColumn(3).
		setCellRenderer(new MyCellRenderer());
		GuiMain.todoTable.getColumnModel().getColumn(4).
		setCellRenderer(new MyCellRenderer());
		GuiMain.todoTable.getColumnModel().getColumn(5).
		setCellRenderer(new MyCellRenderer());
		GuiMain.todoTable.setDefaultRenderer(Color.class, 
				new CellColorRenderer());
		GuiMain.todoTable.setDefaultRenderer(String.class, 
				centerRenderer);
		GuiMain.todoTable.setDefaultRenderer(Integer.class, 
				centerRenderer);

	}

	//@author A0116160W
	/**
	 * Displays the list of task found to feedback panel
	 * 
	 * @param tasks found 
	 * @param String containing the keyword that needs to be searched
	 */
	private static void findOperation(LinkedList<Item> tasks, String str) {
		String[] s = str.split(" ");
		Item item = tasks.get(0);
		GuiMain.feedback.setText("Search results for \"" 
				+ s[s.length-1] + "\":\n");
		if(!(item.getName().equals(Operations.EMPTY_MESSAGE))) {

			GuiMain.feedback.setText(GuiMain.feedback.getText() +
					"  - " + tasks.size() + " found\n");

			GuiMain.feedback.setText(GuiMain.feedback.getText() 
					+ VIEW_UNCOMPLETED_TASKS);
		}
		else {
			GuiMain.feedback.setText("No Tasks Found "
					+ "containing that keyword!\n");
		}

	}

	//@author A0116160W
	/**
	 * Displays the deleted task for confirmation onto feedback panel
	 * 
	 * @param the task deleted
	 */
	private static void deleteOperation(Item firstTask) {
		if(!(firstTask.getName().equals(Operations.EMPTY_MESSAGE))) {
			GuiMain.feedback.setText(
					TASK_DELETED + 
					firstTask.toString());
			GuiMain.feedback.setText("Task deleted successfully!");
		}
		else {
			GuiMain.feedback.setText(DELETE_ERROR_MESSAGE);
		}
	}

	//@author A0116160W
	/**
	 * Updates the todo Table with the list of tasks provided
	 * 
	 * @param all tasks that need to be showed
	 * @param the first task on the list
	 */
	private static void viewOperation(LinkedList<Item> tasks, Item firstTask) {
		if(!(firstTask.getName().equals(Operations.EMPTY_MESSAGE))) {
			updateTodoTable(tasks);

			GuiMain.tabbedPane.setSelectedComponent(GuiMain.todoTab);
			GuiMain.tabbedPane.getSelectedComponent();
		}
		else {
			GuiMain.feedback.setText("Sorry, No Tasks Found!\n");
		}
	}

	//@author A0116160W
	/**
	 * Diplays all the labels at the feedback panel
	 * 
	 * @param list of all labels
	 * @param first label
	 */
	private static void viewLabelsOperation(LinkedList<Item> labels, 
			Item firstLabel) {
		GuiMain.feedback.setText("All Labels Displayed Below:\n");
		if(!(firstLabel.getName().equals(Operations.EMPTY_MESSAGE))) {
			for(int i=0; i<labels.size(); i++) {
				Item tempLabel = labels.get(i);
				GuiMain.feedback.setText(GuiMain.feedback.getText() +
						"\n  " + (i+1) + ". "+ tempLabel + "\n");
			}
		}
		else {
			GuiMain.feedback.setText("Sorry, No Labels Found!\n");
		}
	}

	//@author A0116160W
	/**
	 * Reads a txt file and return it in string format
	 * 
	 * @param file location/filename
	 * 
	 * @return File text in string format
	 */
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

	//@author A0116160W
	/**
	 * Updates the 'search' table after search, showing the search results
	 * in the 'search' tab.
	 * 
	 * @param Search Results List
	 * @param First result
	 */
	public static void updateSearchTable(LinkedList<Item> items, Item firstTask) {
		DefaultTableModel tableModel = 
				(DefaultTableModel) GuiMain.searchTable.getModel();
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
					data[5] = tempTask.getFormattedStart() 
							+ " to " + tempTask.getFormattedEnd();
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
		GuiMain.searchTable.getColumnModel().getColumn(3).
		setCellRenderer(new MyCellRenderer());
		GuiMain.searchTable.getColumnModel().getColumn(4).
		setCellRenderer(new MyCellRenderer());
		GuiMain.searchTable.getColumnModel().getColumn(5).
		setCellRenderer(new MyCellRenderer());
		GuiMain.searchTable.setDefaultRenderer(Color.class, 
				new CellColorRenderer());
		GuiMain.searchTable.setDefaultRenderer(String.class, 
				centerRenderer);
		GuiMain.searchTable.setDefaultRenderer(Integer.class, 
				centerRenderer);


	}

	//@author A0116160W
	/**
	 * Initializes Remembra by calling all update methods for 
	 * displaying all the necessary details.
	 */
	static void initialize(){
		LogicMain logic = new LogicMain();
		logic.processInput(VIEW);
		GuiMain.feedback.setText(WELCOME_MESSAGE);
		updateDoneTable(logic.processInput(VIEW_DONE));
		updateAllTable(logic.processInput(VIEW_ALL));
		updateTodoTable(logic.processInput(VIEW_NOT_DONE));
	}
}