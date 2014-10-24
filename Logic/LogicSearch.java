
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;


public final class LogicSearch {

	//private Objects
	private static LinkedList<String> suggestInputs = new LinkedList<String>(); //but if static can't be changed! ><" resolve this asap!
	
	//@author A0112898U
	/**
	 * Enum for search types
	 */
	public enum SEARCH_TYPES{
		
		SEARCH_NAME,
		SEARCH_DEADLINE,
		
		//Following constants are for the smart search
		//Searches via name, description and labels ONLY
		SEARCH_START_LETTER,
		SEARCH_SUBSTRING_CONTAINS,
		SEARCH_MATCH_WORD,
		SEARCH_POWER_SEARCH
	};
	
	
	
	//@author A0112898U
	/**
	 * Smart Searches for user input via Stored Tasks
	 */
	public static void smartSearch(String searchLine, 
				LinkedList<Task> listOfTasks, SEARCH_TYPES searchType){
		
		//Task that is collated through the searches.
		LinkedList<Task> matchedTasks = new LinkedList<Task>(); 
		
		switch(searchType){
		
			case SEARCH_START_LETTER:
			case SEARCH_SUBSTRING_CONTAINS:
			case SEARCH_MATCH_WORD:
			case SEARCH_POWER_SEARCH:
				break;
		
		}
		
	}
	
	
	//@author A0112898U
	/**
	 * Searches the user search inputs,  with the list of tasks
	 * ONLY Searches a char if present with the tokenized inputs, 
	 * check the char with the 1st letter of every string if applies, 
	 * matched tasks is then added to the list of return linkedlist string
	 * 
	 * @param collatedList - accepts a LinkedList<Task> type list that has been 
	 * 						 previously initiated/accumulated 
	 * 
	 * @param bufferedTaskList - List of task that have been added by user
	 * 
	 * @return returns the newly collated list
	 */
	public static LinkedList<Task> startLetterSearch(LinkedList<Task> 
				collatedMatchedTaskList, LinkedList<Task> bufferedTaskList){
		
		LinkedList<Task> tempCollatedList = new LinkedList(collatedMatchedTaskList);
		
		//
		//Breaks the task via tokenization method and search through
		//
		
		return tempCollatedList;
		
	}
		
	
	//@author A0112898U
	/**
	 * Searches the user search inputs, with the list of tasks via description,
	 * Compare word by word with the tokenized inputs
	 * if applies, matched task is added to the list of return linkedlist string
	 * 
	 * @param collatedList - accepts a LinkedList<Task> type list that has been 
	 * 						 previously initiated/accumulated 
	 * 
	 * @param bufferedTaskList - List of task that have been added by user
	 * 
	 * @return returns the newly collated list
	 */
	public static LinkedList<Task> matchWordSearch(LinkedList<Task> 
				collatedMatchedTaskList, LinkedList<Task> bufferedTaskList){
	
		LinkedList<Task> tempCollatedList = new LinkedList(collatedMatchedTaskList);
		
		//
		//Breaks the task via tokenization method and search through
		//
		
		return tempCollatedList;
	}
	
	
	//@author A0112898U
	/**
	 * Searches the user search inputs, with the list of tasks via description,
	 * Simple substring search via .contains with the tokenized inputs
	 * if applies, matched task is added to the list of return linkedlist string
	 * 
	 * @param collatedList - accepts a LinkedList<Task> type list that has been 
	 * 						 previously initiated/accumulated 
	 * 
	 * @param bufferedTaskList - List of task that have been added by user
	 * 
	 * @return returns the newly collated list
	 */
	public static LinkedList<Task> subStringContainsSearch(LinkedList<Task> 
				collatedMatchedTaskList, LinkedList<Task> bufferedTaskList){ 
		
		LinkedList<Task> tempCollatedList = new LinkedList(collatedMatchedTaskList);
		
		//
		//Breaks the task via tokenization method and search through
		//
		
		
		//
		//If possible return suggestion like if user types ankalp but ankalp is contained in sankalp
		//
		
		
		
		return tempCollatedList;
		
	}
	
	
	//Freaking expensive to search and tiring to implment 
	/* - TBA - may or may not implement */
	//@author A0112898U
	/**
	 * !!! IMPORANT WARNING !!! Do this search ONLY WHEN other match case = null collated List !!!
	 * 
	 * Searches the user search inputs, with the list of tasks via description,
	 * Extensive search with percentage of letter containment with suggestion to user to
	 * detect similar strings via the user inputs
	 * if matches 50% and above by letter containment den add task to collatedList
	 * if matches 70% by letter containment den suggest 
	 * 
	 * 
	 * @param collatedList - accepts a LinkedList<Task> type list that has been 
	 * 						 previously initiated/accumulated 
	 * 
	 * @param bufferedTaskList - List of task that have been added by user
	 * 
	 * @return returns the newly collated list
	 */
	public static LinkedList<Task> powerSearch(LinkedList<Task> 
				collatedMatchedTaskList, LinkedList<Task> bufferedTaskList){
		
		LinkedList<Task> tempCollatedList = new LinkedList(collatedMatchedTaskList);
		
		//
		//Breaks the task via tokenization method and search through
		//
		
		
		//
		//If possible return suggestion like if user types ankalp but ankalp is contained in sankalp
		//
		
		
		
		return tempCollatedList;
	}
	
	//@author A0112898U
	/**
	 * Suggest a list of inputs that could be similar to user searches if match = 0;
	 * 
	 * Implemention steps and intentions - To be removed 
	 * How to use? This will also save a List of conjugated task with accordance to the suggested list
	 * so that if any searches string is accepted by the user, the list of matches tasks
	 * is to be returned immediately, no need serach again :)
	 * 
	 */
	public static LinkedList<String> suggestSearchString(){
		
		//Exceptions is for users
		LinkedList<String> tempCollatedSuggestedStrings = new LinkedList(suggestInputs);
		
		return tempCollatedSuggestedStrings;
	}

}
