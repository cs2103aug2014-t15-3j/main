//@author A0112898U

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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
		TYPE_ALL,
		
		//Following constants are for the smart search
		//Searches via name, description and labels ONLY
		SEARCH_START_LETTER,
		SEARCH_SUBSTRING_CONTAINS,
		SEARCH_MATCH_WORD,
		SEARCH_POWER_SEARCH
	};
	
	
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

			case TYPE_ALL:
			
			case TYPE_NAME:
				
	
				if(searchType == SEARCH_TYPES.TYPE_NAME){
					break;
				}
				
			case TYPE_DESCRIPTION:
				
				//Search each query string through all the stored task via name
				for (String searchString:tokenizedInputs){
					
					for(Task t:bufferedTaskList){ 
						
						if (t.getDescription().contains(searchString)){
							
							tempCollatedList.add(t);
						}
					}
				}
				
				if(searchType == SEARCH_TYPES.TYPE_DESCRIPTION){
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
				
			
				
			default:
				
				System.out.println("Type not supported");
				return null;
		}

		//Finally return the collated list of matched task
		return tempCollatedList;
	}	
	
	
	//@author A0112898U
	/**
	 * Searches the user search inputs,  with the list of tasks via name/label/description
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
	private static LinkedList<Task> startLetterSearch(String queryString, LinkedList<Task> 
				collatedMatchedTaskList, LinkedList<Task> bufferedTaskList, SEARCH_TYPES searchType){
		
		LinkedList<Task> tempCollatedList = new LinkedList<Task>();//(collatedMatchedTaskList);
		
		//Tokenize the search input
		tokenizedInputs = tokenizeSearchInput(queryString);
		
		for (String searchString:tokenizedInputs){
			
			//if the token is a character
			if (searchString.length() == 1){
			
				//Compare the token with the list of stored tasks
				for (Task t:bufferedTaskList){ 
					
					//Tokenized the searchType string
					LinkedList<LinkedList<String>> chkToks = new LinkedList<LinkedList<String>>();
					
					switch (searchType){
						
						case TYPE_ALL:
						
						case TYPE_DESCRIPTION:
							chkToks.add(tokenizeSearchInput(t.getDescription()));
							
							if (searchType == SEARCH_TYPES.TYPE_DESCRIPTION){
								break;
							}
							
							
						case TYPE_NAME:
							chkToks.add(tokenizeSearchInput(t.getName()));
							
							if (searchType == SEARCH_TYPES.TYPE_NAME){
								break;
							}
							
						case TYPE_LABEL:
							//chkToks = tokenizeSearchInput(t.getLabel());
							
							if (searchType == SEARCH_TYPES.TYPE_LABEL){
								break;
							}
							
					}
					
					for (LinkedList<String> chkTok:chkToks){
						
						boolean isTaskAdded = false;
						
						for(String chkT:chkTok){
								
							//Search through the first Letter of every word in the description
							if (searchString.equals(Character.toString(chkT.charAt(0)))){
								
								//System.out.println(chkTok);
								
								//Task tt = new Task(t);
								
								if(!isTaskExist(tempCollatedList,t)){  // why only t0 got added? // i really need this
									
									tempCollatedList.add(t);
									isTaskAdded = true;
									break; //Break out of the current task
								}
							}
						}	
						
						//if task is already added dont' need to check for the next type
						if (isTaskAdded){
							break;
						}
					}
				}
			}
		}
		
		//Checking purposes
		for(Task t:tempCollatedList){
			System.out.println(t.getName());
		}
		
		//collatedMatchedTaskList.addAll(tempCollatedList);
		return tempCollatedList;
	}
	
	
	//@author A0112898U
	/**
	 * Searches the user search inputs, with the list of tasks via name/label/description,
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
	private static LinkedList<Task> matchWordSearch(String queryString, LinkedList<Task> 
				collatedMatchedTaskList, LinkedList<Task> bufferedTaskList, SEARCH_TYPES searchType){
	
		LinkedList<Task> tempCollatedList = new LinkedList(collatedMatchedTaskList);
		
		//Tokenize the search input
		tokenizedInputs = tokenizeSearchInput(queryString);
		
		//Search each query string through all the stored task via name
		for (String searchString:tokenizedInputs){
			
			//if the token is a word
			if (searchString.length() > 1){
			
				for(Task t:bufferedTaskList){ 
					
					//Tokenized the searchType string
					LinkedList<String> chkString = new LinkedList<String>();
					
					switch (searchType){
						
						case TYPE_ALL:
							
						case TYPE_DESCRIPTION:
							chkString.add(t.getDescription());
							
							if (searchType == SEARCH_TYPES.TYPE_DESCRIPTION){
								break;
							}
							
						case TYPE_NAME:
							chkString.add(t.getName());
							
							if (searchType == SEARCH_TYPES.TYPE_NAME){
								break;
							}
							
						case TYPE_LABEL:
							//chkString[2] = t.getLabel();
							break;	
					}
						
					
					for (String chkStr:chkString){
						
						if (chkStr.contains(searchString)){
							
							System.out.println(chkString);
							
							//Task tt = new Task(t);
							
							if(!isTaskExist(tempCollatedList,t)){
								
								tempCollatedList.add(t);
								
								break; //Break out of the current task if task is added
							}
						}					
					}
				}
			}
		}
		

		return tempCollatedList;
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
				LinkedList<Task> bufferedTaskList, SEARCH_TYPES searchType, SEARCH_TYPES searchAlgoType){
		
		//Task that is collated through the searches.
		LinkedList<Task> matchedTasks = new LinkedList<Task>(); 
		
		//Tokenize the search input
		//tokenizedInputs = tokenizeSearchInput(searchLine);
		/*
		for(String s:tokenizedInputs){
			System.out.println(s);
		}
		*/
		
		//matchedTasks.addAll(startLetterSearch(searchLine, matchedTasks, bufferedTaskList,searchType));
		//matchedTasks.addAll(matchWordSearch(searchLine, matchedTasks, bufferedTaskList,searchType));
		
		
		switch(searchAlgoType){
		
			case TYPE_ALL:
			
			case SEARCH_START_LETTER:
				//matchedTasks = startLetterSearch(searchLine, matchedTasks, bufferedTaskList,searchType);
				//startLetterSearch(matchedTasks, bufferedTaskList, SEARCH_TYPES.TYPE_NAME);
				matchedTasks.addAll(startLetterSearch(searchLine, matchedTasks, bufferedTaskList,searchType));
				
				if (searchAlgoType == SEARCH_TYPES.SEARCH_START_LETTER){
					break;
				}
				
			case SEARCH_MATCH_WORD:
				//matchedTasks = matchWordSearch(searchLine, matchedTasks, bufferedTaskList,searchType);
				matchedTasks.addAll(matchWordSearch(searchLine, matchedTasks, bufferedTaskList,searchType));
				
				if (searchAlgoType == SEARCH_TYPES.SEARCH_MATCH_WORD){
					break;
				}
				
			case SEARCH_POWER_SEARCH:
				
				System.out.println("Type not supproted yet");
				break;
		}
		
		return matchedTasks;
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
	
	//@author A0112898U
	/**
	 * Checks if the task to be added is already in the collated list of matched task
	 * 
	 * Reason why not to use the '.contains' but to create and use this function is because the 
	 * '.equals' in task was overrided by the original creator for other checking purpose 
	 * thus the .contain doesn't work like the intended original purpose, which would work for my code
	 *
	 * @param currentCollatedTasks the added list of task to check with
	 * @param tobeAddedTask task that is to be added to check if it already existed
	 * 
	 * @return returns true if task is already present in the collated task, and returns
	 * 				   false if task hasn't been found in the collated task
	 */
	public static boolean isTaskExist(LinkedList<Task> currentCollatedTasks, Task tobeAddedTask){
		
		for(Task t:currentCollatedTasks){
			
			if(t.getName().equals(tobeAddedTask.getName()) 
					&& t.getDescription().equals(tobeAddedTask.getDescription())
					&& (t.getTimeStamp() == tobeAddedTask.getTimeStamp())
					&& (t.getLabel() == tobeAddedTask.getLabel())
					){
				
				return true;
			}
		}
		
		return false;
	}


}
