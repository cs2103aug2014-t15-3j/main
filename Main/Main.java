import java.io.IOException;
import java.util.LinkedList;

/**
 * 
 */

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

	static StorageMain storage;
	static GuiMain gui;
	
	@SuppressWarnings("unchecked") // To suppress the object type cast from LinkedList<?> to LinkedList<OBJTYPE>
	
	public static void main(String[] args) {
		
		System.out.println("Starting Remembra...");
		
		gui = new GuiMain();
		gui.launch();
		
		StorageMain storageMain = new StorageMain();
		
				
		/************ Chuan Wei's Example for Logic Search for LogicMain Implementation ***********/
		/*************************************** * DO NOT REMOVE :D *******************************/
				
		//Task Stubs for Logic Search
		LinkedList<Task> taskList = new LinkedList<Task>();
		LinkedList<Task> taskListStored = new LinkedList<Task>();
		
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
		
		//retun all 
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

		//return task 1, 3, 4
		LinkedList<Task> searchedList4 = LogicSearch.searchTasks("1 4", taskList, 
					LogicSearch.SEARCH_TYPES.TYPE_ALL, LogicSearch.SEARCH_TYPES.SEARCH_POWER_SEARCH);

		//return all task
		LinkedList<Task> searchedList5 = LogicSearch.searchTasks("1 4 e", taskList, 
					LogicSearch.SEARCH_TYPES.TYPE_ALL, LogicSearch.SEARCH_TYPES.SEARCH_POWER_SEARCH);
		
		
		//Hi Sam, do change the index of the searchList'1|2|3|4' below to see the result
		for(Task t:searchedList4){
			System.out.println(t.getName());
		}
		
		/*************************************** * DO NOT REMOVE :D ******************************/
		/************ Chuan Wei's Example for Logic Search for LogicMain Implementation ***********/	
		
	}

}




