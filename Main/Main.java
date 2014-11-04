import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;

//@author A0112898U
/**
 *  Here are some ground rules I hope we can follow so that we can ensure that there's no
	Conflicts when committing,merging and during QA of codes
	
	1. When committing your local copy, make sure you've updated to the latest 
	   copy and there are NO ERRORS (THIS IS VERY IMPORTANT) before you commit! 
	   i.e Sam's code might be working before but after My code's commit, 
	   his codes should still work, if his code suddenly doesn't work, 
	   the one who did the commit would have to be responsible to debug the error.
	
	2. Ensure that no one is committing when you are committing to prevent  version conflicts,
	   text the group to make sure that no one is committing
	   
	3. Committing frequently is good but please only commit when there's a significant change in your
	   code, don't commit every few seconds, its just like dropbox, it's really kinda irritating if
	   the pop-up keeps appearing and in this case u'll have to let us know in the chat when you commit
	   every few seconds thus, it is best to commit when 'for example' there's a major change 
	   to your code where it will affect the other person's code etc.
	
	4. When you really need to edit someone else's code, whether it is minor o
	   not, do inform them first (as explained in the tutorial).
	   Also if you change a certain code i.e. code name and it has been called by the other person, make
	   sure to change the error for them don't leave the errors hanging! As mention in 1. NO ERRORS when
	   committing!
	
	5. Everyone have different coding standards but the QA will check codes 
	   once in awhile to ensure a common level of coding, 
	   do follow the given java coding standard as closely as possible.
	
	6. Refactor codes even once in awhile to ensure that you don't snowball.
	
	Do feel free to add in more and notify the others if you have any suggestions.
	Yupp that should be all and happy coding :) - Chuan Wei

 * @author Bay Chuan Wei Candiie
 * @author Samuel Lim Yi Jie
 * @author Sankalp 
 *
 */
public class Main {

	//static StorageMain storage;
	static GuiMain gui;
	
	
	public static void main(String[] args) throws IOException {

		//Run Shell Script to set up hotkey
		runWsShell();

		
		//Start Process
		System.out.println("Starting Remembra...");
		
		gui = new GuiMain();
		gui.launch();
		
		//StorageMain storageMain = new StorageMain();
		
				
		/************ Chuan Wei's Example for Logic Search for LogicMain Implementation ***********/
		/*************************************** * DO NOT REMOVE :D *******************************/
				
		//Task Stubs for Logic Search
		LinkedList<Task> taskList = new LinkedList<Task>();
		
		Task task1 = new Task("task1","to love Remembra 1 uniqueKey1");
		Task task2 = new Task("task2","to love Remembra 2 uniqueKey2");
		Task task3 = new Task("task3","to love Remembra 1 uniqueKey3");
		Task task4 = new Task("task4","to love Remembra 2 uniqueKey4");
		Task task5 = new Task("task5","to love Remembra 0 Enchanted");
		
		taskList.add(task1);
		taskList.add(task2);
		taskList.add(task3);
		taskList.add(task4);
		taskList.add(task5);
		
		/********************************************Search normal********************************************/
		LinkedList<Task> searchedList1 = LogicSearch.searchTasks("love", taskList, 
					LogicSearch.SEARCH_TYPES.TYPE_ALL);
		
		//return only 1 and 3
		LinkedList<Task> searchedList2 = LogicSearch.searchTasks("task1 task4", taskList, 
					LogicSearch.SEARCH_TYPES.TYPE_ALL);

		//return task 1, 3, reason WHY task 4 didn't appear because standard search treats single character
		//as the start of each word only, this reason is supported with the 'e' search in search list 5, 
		//lets say if i search for e, i actually only want task 5 but all other task appears as a cluster
		//it would be kind of non eye pleasing to the user
		//
		//So we must also indicate in the instructions that users can ask for advance search
		//
		LinkedList<Task> searchedList3 = LogicSearch.searchTasks("1 4 e", taskList, 
					LogicSearch.SEARCH_TYPES.TYPE_ALL);


		/********************************************Search DEADLINE********************************************/
		//THIS IS DIFFERENT, BCOS IT TAKES IN LONG!
		long testVar = 123;
		LinkedList<Task> searchedList4 = LogicSearch.searchTasks(LogicSearch.SEARCH_TYPES.SEARCH_DEADLINE,testVar, taskList);
		
		/********************************************Search LABEL********************************************/
		//return task 1, 3, 4
		LinkedList<Task> searchedList5 = LogicSearch.searchTasks("1 4", taskList, 
					LogicSearch.SEARCH_TYPES.TYPE_LABEL, LogicSearch.SEARCH_TYPES.TYPE_ALL);
		
		/********************************************Search DESCRIPTION********************************************/
		//return all task
		LinkedList<Task> searchedList6 = LogicSearch.searchTasks("1 4 e", taskList, 
					LogicSearch.SEARCH_TYPES.TYPE_DESCRIPTION, LogicSearch.SEARCH_TYPES.TYPE_ALL);
		
		/********************************************Search TYPE_NAME********************************************/
		//return all task
		LinkedList<Task> searchedList7 = LogicSearch.searchTasks("1 4 e", taskList, 
					LogicSearch.SEARCH_TYPES.TYPE_NAME, LogicSearch.SEARCH_TYPES.TYPE_ALL);
		
		/***************************Search TYPE_NAME / LABEL / DECRIPTIONS BY STARTING CHARACTER********************************************/
		//return all task
		LinkedList<Task> searchedList8 = LogicSearch.searchTasks("1 4 e", taskList, 
					LogicSearch.SEARCH_TYPES.TYPE_NAME, LogicSearch.SEARCH_TYPES.SEARCH_START_LETTER);
		//return all task
		LinkedList<Task> searchedList9 = LogicSearch.searchTasks("1 4 e", taskList, 
					LogicSearch.SEARCH_TYPES.TYPE_DESCRIPTION, LogicSearch.SEARCH_TYPES.SEARCH_START_LETTER);
				
		//return all task
		LinkedList<Task> searchedList10 = LogicSearch.searchTasks("1 4 e", taskList, 
					LogicSearch.SEARCH_TYPES.TYPE_LABEL, LogicSearch.SEARCH_TYPES.SEARCH_START_LETTER);
						
		
		/***************************Search TYPE_NAME / LABEL / DECRIPTIONS BY WORD MATCH ********************************************/
		//return all task
		LinkedList<Task> searchedList11 = LogicSearch.searchTasks("1 4 e", taskList, 
					LogicSearch.SEARCH_TYPES.TYPE_NAME, LogicSearch.SEARCH_TYPES.SEARCH_MATCH_WORD);
		//return all task
		LinkedList<Task> searchedList12 = LogicSearch.searchTasks("1 4 e", taskList, 
					LogicSearch.SEARCH_TYPES.TYPE_DESCRIPTION, LogicSearch.SEARCH_TYPES.SEARCH_MATCH_WORD);
				
		//return all task
		LinkedList<Task> searchedList113 = LogicSearch.searchTasks("1 4 e", taskList, 
					LogicSearch.SEARCH_TYPES.TYPE_LABEL, LogicSearch.SEARCH_TYPES.SEARCH_MATCH_WORD);
		
		
		//Hi Sam, do change the index of the searchList'1|2|3|4' below to see the result
		for(Task t:searchedList4){
			System.out.println(t.getName());
		}
		
		
		
		/******************************************POWER SEARCH*************************************/
		String queryString = "Sakalp Samel candie";
		
		//This will return as per normal in pwoer search
		LinkedList<Task> returnList = LogicSearch.searchTasks(queryString,taskList,LogicSearch.SEARCH_TYPES.TYPE_ALL, LogicSearch.SEARCH_TYPES.SEARCH_POWER_SEARCH);
		LinkedList<Task> returnList2 = LogicSearch.searchTasks(queryString,taskList,LogicSearch.SEARCH_TYPES.TYPE_LABEL, LogicSearch.SEARCH_TYPES.SEARCH_POWER_SEARCH);
		LinkedList<Task> returnList3 = LogicSearch.searchTasks(queryString,taskList,LogicSearch.SEARCH_TYPES.TYPE_NAME, LogicSearch.SEARCH_TYPES.SEARCH_POWER_SEARCH);
		LinkedList<Task> returnList5 = LogicSearch.searchTasks(queryString,taskList,LogicSearch.SEARCH_TYPES.TYPE_DESCRIPTION, LogicSearch.SEARCH_TYPES.SEARCH_POWER_SEARCH);
			
		
		System.out.println(returnList + "\n");
		
		System.out.println("You Searched : " + queryString);
		
		System.out.println("Did you mean : ");
		
		//This is to get the suggested task list
		System.out.println(LogicSearch.getSuggestedString());
		
		
		
		
		/*************************************** * DO NOT REMOVE :D ******************************/
		/************ Chuan Wei's Example for Logic Search for LogicMain Implementation ***********/	
		
		
		
		
		/*************************************** * DO NOT REMOVE :D ******************************/
		/************ Chuan Wei's Example for Logic Reminder for LogicMain Implementation ***********/	
		
		
		//1.
		//Call this function (at your constructor after you read in the data from the sotrage)
		//to init the LogicReminder System's reminder list
		

		//***NOTE *****/
		
		//I've implemented this for u already !
		
		//		LinkedList<Task> tasks = new LinkedList<Task>();
		//		
		//		try {
		//			
		//			LogicReminder.getInstance().regenReminderList(tasks);
		//			
		//		} catch (ParseException e) {
		//			
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		
		//***NOTE *****/
		
		
		
		//Start here! Do some simple testing after implementing it thks! 
	
		/* 2.
		//Call this function everytime you update/edit a task
		//best is if you check if a reminder is required, i've already included a parameter hasReminder for you to use
		 * 
		//LogicReminder.getInstance().updateTaskTobeReminded(newTask, oldTask);
		//since you have a undo function, please parse the oldTask in.
		
		*
		* Testing 2
		* like when u edit the reminder/task set to a nearest time, see if the reminder with
		* the updated info shows at the console >>>
		*/
		
		
		/* 3.
		//Call this function everytime you add a task
		//also best is if you check if a reminder is required, i've already included a parameter hasReminder for you to use
		 * 
		LogicReminder.getInstance().addTaskTobeReminded(t);
		*
		* Testing 3
		* like when u edit add a task set to a nearest time to your clock, see if the reminder shows at the console >>>
		*/
		
		
		
		/* 4.
		//Call this function everytime you delete a task
		//also best is if you check if a reminder is present 
		 * 
		LogicReminder.getInstance().stopTask(taskToStop)		
		*
		* Testing 4
		* Like when u add a task set to a nearest time to your clock like 1 min away, then
		* delete the task, then see if the reminder shows at the console >>> (It shouldn't show!)
		*/
		
		

		
	}

	//@author A0112898U
	/**
	 * Runs the Shell Script to set up HotKey to start remembra in windows desktop
	 * @throws IOException
	 */
	public static void runWsShell() throws IOException{
		
		//Run windows PowerShell Script
		//Runtime.getRuntime().exec("wscript ./scripts/powerShell.vbs");
	}
}




