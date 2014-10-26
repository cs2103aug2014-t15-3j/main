//@author A0112898U

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;


public final class LogicSearch {

	//private Objects
	private LinkedList<String> suggestInputs = new LinkedList<String>(); //but if static can't be changed! ><" resolve this asap!
	private static LinkedList<String> tokenizedInputs = new LinkedList<String>(); 
	private LinkedList<Task> matchedTasks = new LinkedList<Task>();
	
	
	//@author A0112898U
	/**
	 * Enum for search types
	 */
	public enum SEARCH_TYPES{
		
		//Types of search that could be available to search in a  task
		SEARCH_NAME,
		SEARCH_DEADLINE,
		TYPE_NAME,
		TYPE_LABEL,
		TYPE_DESCRIPTION,
		TYPE_TIME_ADDED,
		TYPE_DEADLINE,
		
		//Following constants are for the smart search
		//Searches via name, description and labels ONLY
		SEARCH_START_LETTER,
		SEARCH_SUBSTRING_CONTAINS,
		SEARCH_MATCH_WORD,
		SEARCH_POWER_SEARCH
	};
	
	
	
	//@author A0112898U
	/**
	 * public overloaded method searchTasks() for 'Long' datatype query
	 * for outside call to retrieve Stored Task 
	 * 
	 * @param searchType - the type of query type i.e. TYPE_DEADLINE/TYPE_TIME_ADDED
	 * @param queryParam - query of 'Long' datatype variable is to input 
	 * 
	 * @return queryTasks returns a LinkedList with all task categorized with the 
	 * same 'searchType' & 'Long' datatype variable
	 * 
	 */
	private LinkedList<Task> searchTasks(SEARCH_TYPES searchType, 
				long queryParam, LinkedList<Task> bufferedTaskList){

		LinkedList<Task> storedTasks = bufferedTaskList;
		LinkedList<Task> tempCollatedList = new LinkedList();

		switch(searchType){

		case TYPE_DEADLINE:

			for(Task t:storedTasks){

				if(t.getDeadline() == queryParam){
					tempCollatedList.add(t);
				}
			}

			break;


		case TYPE_TIME_ADDED:

			for(Task t:storedTasks){

				if(t.getTimeStamp() == queryParam){
					tempCollatedList.add(t);
				}
			}

			break;

		default:
			System.out.println("Type not supported");
			return null;
		}

		return tempCollatedList;
	}

	
	//@author A0112898U
	/**
	 * public overloaded method searchTasks() for 'String' datatype query
	 * for outside call to retrieve Stored Task 
	 * 
	 * @param searchType - the type of query type i.e. TYPE_NAME/TYPE_LABEL/TYPE_DESCRIPTION
	 * @param queryParam - query of 'String' datatype variable is to input 
	 * 
	 * @return queryTasks returns a LinkedList with all task categorized with the 
	 * same 'searchType' & 'String' datatype variable
	 * 
	 */
	public LinkedList<Task> searchTasks(SEARCH_TYPES searchType, 
				String queryString, LinkedList<Task> bufferedTaskList){

		LinkedList<Task> storedTasks = bufferedTaskList;
		LinkedList<Task> tempCollatedList = new LinkedList();
		
		//Tokenize the search input
		tokenizedInputs = tokenizeSearchInput(queryString);

		
		switch(searchType){

			case TYPE_NAME:
				
				//Search each query string through all the stored task via name
				for (String searchString:tokenizedInputs){
					
					for(Task t:bufferedTaskList){ 
						
						if (t.getName().equals(searchString)){
							
							//If task not already added, add task to collated task
							if(!tempCollatedList.contains(t)){
								
								//If task not already added, add task to collated task
								if(!tempCollatedList.contains(t)){
									
									tempCollatedList.add(t);
								}
							}
						}
					}
				}
	
				if(searchType == SEARCH_TYPES.TYPE_NAME){
					break;
				}
				
			case TYPE_LABEL:
				
				//Search each query string through all the stored task via Label
				for (String searchString:tokenizedInputs){
					
					for(Task t:bufferedTaskList){ 
						
						/* ask sam why label returns long
						if (t.getLabel().equals(searchString)){
						
							//If task not already added, add task to collated task
							if(!tempCollatedList.contains(t)){
								
								tempCollatedList.add(t);
							}
						}
						*/
					}
				}
	
				break;
				
			case TYPE_DESCRIPTION:
				
				//Search each query string through all the stored task via name
				for (String searchString:tokenizedInputs){
					
					for(Task t:bufferedTaskList){ 
						
						if (t.getDescription().contains(searchString)){
							
							tempCollatedList.add(t);
						}
					}
				}
	
				//Finally return the collated list of matched task
	
				break;
				
			default:
				
				System.out.println("Type not supported");
				return null;
		}

		return tempCollatedList;
	}
	
	

	//@author A0112898U
	/**
	 * Tokenizes the param searchString
	 * 
	 * @param searchString - search string input by user
	 * 
	 * @return a LinkedList of tokenized strings
	 */
	private static LinkedList<String> tokenizeSearchInput(String searchString){
		
		StringTokenizer strTokens = new StringTokenizer(searchString);
		LinkedList<String> strToks = new LinkedList<String>();
		
		//Tokenizes the search String taken in
		while (strTokens.hasMoreTokens()){
			
			strToks.add(strTokens.nextToken());
		}
		
		return strToks;
	}
	
	
	//@author A0112898U
	/**
	 * Smart Searches for user input via Stored Tasks
	 * 
	 * @param collatedList - accepts a LinkedList<Task> type list that has been 
	 * 						 previously initiated/accumulated 
	 * 
	 * @param bufferedTaskList - List of task that have been added by user
	 * 
	 */
	public static LinkedList<Task> smartSearch(String searchLine, 
				LinkedList<Task> bufferedTaskList, SEARCH_TYPES searchType){
		
		//Task that is collated through the searches.
		LinkedList<Task> matchedTasks = new LinkedList<Task>(); 
		
		//Tokenize the search input
		tokenizedInputs = tokenizeSearchInput(searchLine);

		for(String s:tokenizedInputs){
			System.out.println(s);
		}
		
		switch(searchType){
		
			case SEARCH_START_LETTER:
				matchedTasks = startLetterSearch(matchedTasks, bufferedTaskList);
				break;
				
			case SEARCH_MATCH_WORD:
			case SEARCH_POWER_SEARCH:
				
				System.out.println("Type not supproted yet");
				break;
		}
		
		return matchedTasks;
		
	}
	
	
	//@author A0112898U
	/**
	 * Searches the user search inputs,  with the list of tasks
	 * ONLY Searches a char if present with the tokenized inputs, 
	 * check the char with the 1st letter of every string if applies, 
	 * matched tasks is then added to the list of return linkedlist string
	 * 
	 * @param collatedMatchedTaskList - accepts a LinkedList<Task> type list that has been 
	 * 						            previously initiated/accumulated 
	 * 
	 * @param bufferedTaskList - List of task that have been added by user
	 * 
	 * @return returns the newly collated list
	 */
	private static LinkedList<Task> startLetterSearch(LinkedList<Task> 
				collatedMatchedTaskList, LinkedList<Task> bufferedTaskList){
		
		LinkedList<Task> tempCollatedList = new LinkedList<Task>();//(collatedMatchedTaskList);
		
		for (String searchString:tokenizedInputs){
			
			//if the token is a character
			if (searchString.length() == 1){
			
				//Compare the token with the list of stored tasks
				for (Task t:bufferedTaskList){ 
				
					//Tokenized the description of the tasks
					LinkedList<String> desToks = tokenizeSearchInput(t.getDescription());
				
					//Search through the first Letter of every word in the description
					for (String desTok:desToks){
					
						
						if (searchString.equals(Character.toString(desTok.charAt(0)))){
						
							System.out.println(desTok);
							
							//Task tt = new Task(t);
							
							//if(!tempCollatedList.contains(t)){  // why only t0 got added?
								
								tempCollatedList.add(t);
								
								break; //Break out of the current task
							//}
						}
					}
				}
			}
		}
		
		for(Task t:tempCollatedList){
			System.out.println(t.getName());
		}
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
	private LinkedList<Task> matchWordSearch(String queryString, LinkedList<Task> 
				collatedMatchedTaskList, LinkedList<Task> bufferedTaskList){
	
		LinkedList<Task> tempCollatedList = new LinkedList(collatedMatchedTaskList);
		
		//Tokenize the search input
		tokenizedInputs = tokenizeSearchInput(queryString);
		
		//Search each query string through all the stored task via name
		for (String searchString:tokenizedInputs){
			
			for(Task t:bufferedTaskList){ 
				
				if (t.getDescription().contains(searchString)){
					
					//If task not already added, add task to collated task
					if(!tempCollatedList.contains(t)){
						
						tempCollatedList.add(t);
					}
				}
			}
		}

		
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
	/*
	public static LinkedList<String> suggestSearchString(){
		
		//Exceptions is for users
		LinkedList<String> tempCollatedSuggestedStrings = new LinkedList(suggestInputs);
		
		return tempCollatedSuggestedStrings;
	}
	*/

}
