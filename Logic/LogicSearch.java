//@author A0112898U

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;


public final class LogicSearch {
	
	// Constant
	private static final long DAY_MILLISECOND = 86400000;

	//private Objects
	private static LinkedList<String> suggestInputs = new LinkedList<String>();
	private static LinkedList<String> tokenizedInputs = new LinkedList<String>(); 
	private LinkedList<Task> matchedTasks = new LinkedList<Task>();
	
	
	//@author A0112898U
	/**
	 * Enum for search types
	 */
	public enum SEARCH_TYPES{
		
		//Types of search that could be available to search in a  task
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
	public static LinkedList<Task> searchTasks(SEARCH_TYPES searchType, 
				long queryParam, LinkedList<Task> bufferedTaskList){

		LinkedList<Task> storedTasks = bufferedTaskList;
		LinkedList<Task> tempCollatedList = new LinkedList<Task>();

		switch(searchType) {

		case TYPE_DEADLINE:

			for(Task t:storedTasks){
				
				System.out.println(">>>"+queryParam);
				
				int startOfDay = (int) (queryParam / DAY_MILLISECOND);
				long startOfDayMs = startOfDay * DAY_MILLISECOND;
				long endOfDayMs = startOfDayMs + DAY_MILLISECOND;
				

				if ( t.getDeadline() >= startOfDayMs &&
						t.getDeadline() < endOfDayMs ) {
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
			return new LinkedList<Task>();
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
	public static LinkedList<Task> searchTasks( String queryString, 
			LinkedList<Task> bufferedTaskList, SEARCH_TYPES searchType ,SEARCH_TYPES... searchAlgoType){

		LinkedList<Task> storedTasks = bufferedTaskList;
		LinkedList<Task> tempCollatedList = new LinkedList();
		
		if (searchAlgoType.length > 0){
			
			tempCollatedList = smartSearch(queryString, bufferedTaskList, searchType, searchAlgoType[0]);
		
		}else {
			tempCollatedList =  smartSearch(queryString, bufferedTaskList, searchType,SEARCH_TYPES.TYPE_ALL);
				
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
							chkToks.add(tokenizeSearchInput(t.getLabelName()));
							
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
						//for double security of check duplicated task purpose!
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
	 * ***IMPT***
	 * This search only searches 2char and above string because, this system assume users 
	 * uses 1 char as the heading of the word to be search, which is covered in and advance
	 * search by 'startLetterSearch'
	 * 
	 * @param collatedList - accepts a LinkedList<Task> type list that has been 
	 * 						 previously initiated/accumulated 
	 * 
	 * @param bufferedTaskList - List of task that have been added by user
	 * 
	 * @return returns the newly collated list
	 */
	private static LinkedList<Task> matchWordSearch(String queryString, LinkedList<Task> 
				collatedMatchedTaskList, LinkedList<Task> bufferedTaskList, 
				SEARCH_TYPES searchType, boolean isPowerSearch){
	
		LinkedList<Task> tempCollatedList = new LinkedList(collatedMatchedTaskList);
		
		//Tokenize the search input
		tokenizedInputs = tokenizeSearchInput(queryString);
		
		
		//Search each query string through all the stored task via name
		for (String searchString:tokenizedInputs){
			
			//if the token is a word
			//if (searchString.length() > 1){
			
			//removes single character search if is not pwoer search, 
			//this is to prevent a whole cluster of search as the single character 
			//is already considered as only the start of each string
			if(searchString.length() <= 1 && !isPowerSearch){
				break;
			}
			
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
						chkString.add(t.getLabelName());
						
						if (searchType == SEARCH_TYPES.TYPE_LABEL){
							break;
						}
							
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
			//}
		}
		

		return tempCollatedList;
	}
	
	@SuppressWarnings("unchecked")
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
	public static LinkedList<Task> smartSearch(String searchLine, LinkedList<Task> 
				bufferedTaskList, SEARCH_TYPES searchType, SEARCH_TYPES searchAlgoType){
		
		//Task that is collated through the searches.
		LinkedList<Task> matchedTasks = new LinkedList<Task>(); 
		
		
		switch(searchAlgoType){
		
			case SEARCH_POWER_SEARCH:

				LinkedList<LinkedList<?>> returnList = powerSearch(searchLine, matchedTasks ,bufferedTaskList, searchType);
				
				if (!(returnList.get(0).size() == 0)){
//					//addSuggestedString(new LinkedList<String>((LinkedList<String>)returnList.get(1)));
					
					suggestInputs = new LinkedList<String>((LinkedList<String>)returnList.get(1));	
				}else {
//					//suggestInputs = new LinkedList<String>();
					suggestInputs.add("no match found");
				}
				return (LinkedList<Task>) returnList.get(0); 
				
			
			case TYPE_ALL:
			
			case SEARCH_START_LETTER:
				
				matchedTasks.addAll(startLetterSearch(searchLine, matchedTasks, bufferedTaskList,searchType));
				
				if (searchAlgoType == SEARCH_TYPES.SEARCH_START_LETTER){
					break;
				}
				
			case SEARCH_MATCH_WORD:

				matchedTasks.addAll(matchWordSearch(searchLine, matchedTasks, bufferedTaskList,searchType,false));
				
				if (searchAlgoType == SEARCH_TYPES.SEARCH_MATCH_WORD){
					break;
				}
				

		}
		
		return removeDuplicate(matchedTasks);
	}
	
	
	//@author A0112898U
	/*
	private static LinkedList<String> addSuggestedString(LinkedList<String> suggestedStrings) {
	
		LinkedList<String> temp = suggestedStrings;
		suggestInputs/
		
		return ;
		
	}
	*/
	
	//@author A0112898U
	public static LinkedList<String> getSuggestedString() {
	
		return suggestInputs;
		
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
	 * @return returns List of List, the first list represents the matched tasks, the 2nd list
	 * represents the suggested keywords
	 */
	public static LinkedList<LinkedList<?>> powerSearch(String queryString, LinkedList<Task> 
				collatedMatchedTaskList, LinkedList<Task> bufferedTaskList, SEARCH_TYPES searchTypes){
		
		LinkedList<Task> tempCollatedList = new LinkedList(collatedMatchedTaskList);
		
		LinkedList<String> suggestedString = new LinkedList<String>();
		//
		//Breaks the task via tokenization method and search through
		//
		tokenizedInputs = tokenizeSearchInput(queryString);
	
		//
		//If possible return suggestion like if user types ankalp but ankalp is contained in sankalp
		//
		//Implementing the 
		
		for (String tok:tokenizedInputs){
			
			for (Task t:bufferedTaskList){
			
				
				switch (searchTypes){
				
					case TYPE_ALL:
					
					case TYPE_DESCRIPTION:
					
						//Double security to check if task has been added
						if(!isTaskExist(collatedMatchedTaskList,t)){
							
							LinkedList<String> tokenizeDescription 
									= tokenizeSearchInput(t.getDescription());
							
							//Check against each word in the Description
							for (String desTok:tokenizeDescription){
								
								if (chkStrSimilarity(tok,desTok) > 40){
								
									if(chkStrSimilarity(tok,desTok) > 50){
										
										suggestedString.add(desTok);
									}
									collatedMatchedTaskList.add(t);
									break; //break out loop if task is added
								}
							}
							
						}
						
						if (searchTypes == SEARCH_TYPES.TYPE_DESCRIPTION){
							break;
						}
					
					case TYPE_NAME:
					
						//Search Names
						if (!isTaskExist(collatedMatchedTaskList,t)){
							
							//Check against names
							if (chkStrSimilarity(tok,t.getName()) > 40){
								if(chkStrSimilarity(tok,t.getName()) > 50){
									
									suggestedString.add(t.getName());
								}
								collatedMatchedTaskList.add(t);
								break; //break out loop if task is added
							}
						}
						
						if (searchTypes == SEARCH_TYPES.TYPE_NAME){
							break;
						}
					
					case TYPE_LABEL:
					
						//Double security to check if task has been added
						if(!isTaskExist(collatedMatchedTaskList,t)){
							
							LinkedList<String> tokenizeLabel
									= tokenizeSearchInput(t.getLabelName());
							
							//Check against each word in the label
							for (String labelTok:tokenizeLabel){
								
								if (chkStrSimilarity(tok,labelTok) > 40){
								
									if(chkStrSimilarity(tok,labelTok) > 50){
										
										suggestedString.add(labelTok);
									}
									collatedMatchedTaskList.add(t);
									break; //break out loop if task is added
								}
							}
							
						}
						
						if (searchTypes == SEARCH_TYPES.TYPE_LABEL){
							break;
						}
					
					default:
						System.out.println("type not supported");
						break;
				}
			}
		}
		
		LinkedList<LinkedList<?>> returnList = new LinkedList<LinkedList<?>>();
		
		returnList.add(collatedMatchedTaskList);
		returnList.add(suggestedString);
		
		return returnList;
	}
	
	public static void main (String[] args){
		
		//System.out.println(computeEditDistance("Sakalp", "Sankalp")); //differ by 1 character
		//System.out.println(chkStrSimilarity("Sakalp", "Sankalp")); //differ percentage
		
		LinkedList<Task> taskList = new LinkedList<Task>();
		
		Task task1 = new Task("task1","to love Remembra 1 Sankalp");
		Task task2 = new Task("task2","to love Remembra 2 Samuel");
		Task task3 = new Task("task3","to love Remembra 1 ChuanWei");
		Task task4 = new Task("task4","to love Remembra 2 Candiie");
		Task task5 = new Task("task5","to love Remembra 0 Enchanted");
		
		taskList.add(task1);
		taskList.add(task2);
		taskList.add(task3);
		taskList.add(task4);
		taskList.add(task5);
		
		String queryString = "Sakalp Samel candie";
		
		//LinkedList<LinkedList<?>> returnList = powerSearch(queryString, new LinkedList<Task>() ,taskList);
		//LinkedList<Task> returnList = smartSearch(queryString,taskList,LogicSearch.SEARCH_TYPES.TYPE_ALL, LogicSearch.SEARCH_TYPES.SEARCH_POWER_SEARCH);
		
		//System.out.println(returnList + "\n");
		
		//System.out.println("You Searched : " + queryString);
		
		//System.out.println("Did you mean : ");
		//System.out.println(getSuggestedString());
		
		
		String queryString1 = "candie";
		String queryString2 = "Sank candie";
		LinkedList<Task> returnList2 = searchTasks(queryString1,taskList,LogicSearch.SEARCH_TYPES.TYPE_NAME, LogicSearch.SEARCH_TYPES.SEARCH_POWER_SEARCH);
		LinkedList<Task> returnList3 = searchTasks(queryString2,taskList,LogicSearch.SEARCH_TYPES.TYPE_DESCRIPTION, LogicSearch.SEARCH_TYPES.SEARCH_POWER_SEARCH);

		System.out.println("BREAKER\nBREAKER\nBREAKER");		
		
		System.out.println(returnList2 + "\n");
	
		System.out.println("You Searched : " + queryString1);
		
		System.out.println("Did you mean : ");
		
		System.out.println(getSuggestedString());
		
		
		System.out.println("BREAKER\nBREAKER\nBREAKER");		
		
		System.out.println(returnList3 + "\n");
		
		System.out.println("You Searched : " + queryString2);
		
		System.out.println("Did you mean : ");
		System.out.println(getSuggestedString());
		
	}
	
    public static double chkStrSimilarity(String s1, String s2) {
        if (s1.length() < s2.length()) { // s1 should always be bigger
            String swap = s1; s1 = s2; s2 = swap;
        }
        int bigLen = s1.length();
        if (bigLen == 0) { return 1.0; /* both strings are zero length */ }
        return ((bigLen - computeEditDistance(s1, s2)) / (double) bigLen)*100;
    }

    public static int computeEditDistance(String s1, String s2) {
    	
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0){
                    costs[j] = j;
                }  else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        }
                        costs[j - 1] = lastValue;
                        
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
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
	 * '.equals' in task was override by the original creator for other important checking purpose 
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
	
	
	//@author A0112898U
	/**
	 * Removes any duplicated tasks in the collated List
	 *
	 * @param currentCollatedTasks the added list of task to check with
	 * 
	 * @return returns a new linkedlist with any duplicated task removed 
	 * 
	 */
	private static LinkedList<Task> removeDuplicate(LinkedList<Task> currentCollatedTasks){
		
		LinkedList<Task> tempList = new LinkedList<Task>(currentCollatedTasks);
		
		Set<Task> newSet = new HashSet(tempList);
		LinkedList<Task> tempList2 =  new LinkedList<Task>(newSet);
		
		return tempList2;
	}

}
