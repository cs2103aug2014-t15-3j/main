//@author A0112898U

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class LogicSearch {
	
	// Constant
	private static final String NO_MATCH_FOUND = "No match found!";
	private static final long DAY_MILLISECOND = 86400000;
	private final static String LOG_NAME = "Logic Search Logger";

	//private Objects
	private static LinkedList<String> suggestInputs = 
			new LinkedList<String>();
	private static LinkedList<String> tokenizedInputs = 
			new LinkedList<String>(); 
	
	// Logger: Use to troubleshoot problems
	private static Logger logger = Logger.getLogger(LOG_NAME);
	
	
	//@author A0112898U
	/**
	 * Enum for search types
	 */
	public enum SEARCH_TYPES {
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
	 * Tokenizes the input parameter - searchString
	 * 
	 * @param searchString - search string input by user
	 * @return a LinkedList of tokenized strings
	 */
	private static LinkedList<String> tokenizeSearchInput(
			String searchString) {
		
		StringTokenizer strTokens = new StringTokenizer(searchString);
		LinkedList<String> strToks = new LinkedList<String>();
		
		//Tokenizes the search String taken in
		while (strTokens.hasMoreTokens()) {	
			strToks.add(strTokens.nextToken());
		}
		return strToks;
	}
	
	
	//@author A0112898U
	/**
	 * Overloaded method searchTasks() for 'Long' datatype query
	 * for outside call to retrieve Stored Task 
	 * 
	 * @param searchType - the type of query i.e. TYPE_DEADLINE/TYPE_TIME_ADDED
	 * @param queryParam - query of 'Long' datatype variable is to input 
	 * @return queryTasks returns a LinkedList with all task categorized with 
	 * the same 'searchType' & 'Long' datatype variable
	 */
	public static LinkedList<Task> searchTasks(SEARCH_TYPES searchType, 
				long queryParam, LinkedList<Task> bufferedTaskList) {
		LinkedList<Task> storedTasks = bufferedTaskList;
		LinkedList<Task> tempCollatedList = new LinkedList<Task>();

		switch (searchType) {
			case TYPE_DEADLINE:
				for (Task t:storedTasks) {
					int startOfDay = (int) (queryParam / DAY_MILLISECOND);
					long startOfDayMs = startOfDay * DAY_MILLISECOND;
					long endOfDayMs = startOfDayMs + DAY_MILLISECOND;
	
					if (t.getDeadline() >= startOfDayMs &&
							t.getDeadline() < endOfDayMs) {
						tempCollatedList.add(t);
					}
				}
				break;
	
	
			case TYPE_TIME_ADDED:
				for (Task t:storedTasks) {
					if (t.getTimeStamp() == queryParam) {
						tempCollatedList.add(t);
					}
				}
				break;
	
			default:
				logger.log(Level.WARNING, 
						"LogicSearch: Unable to Parse dates");
				return new LinkedList<Task>();
			}
		return tempCollatedList;
	}
	

	//@author A0112898U
	/**
	 * public overloaded method searchTasks() for 'String' datatype query
	 * for outside call to retrieve Stored Task 
	 * 
	 * @param searchType - the type of query type i.e. 
	 * 					   TYPE_NAME/TYPE_LABEL/TYPE_DESCRIPTION
	 * @param queryParam - query of 'String' datatype variable is to input 
	 * 
	 * @return queryTasks returns a LinkedList with all task categorized with
	 * the same 'searchType' & 'String' datatype variable
	 */
	public static LinkedList<Task> searchTasks( String queryString, 
			LinkedList<Task> bufferedTaskList, 
			SEARCH_TYPES searchType, SEARCH_TYPES... searchAlgoType) {
		
		LinkedList<Task> storedTasks = bufferedTaskList;
		LinkedList<Task> tempCollatedList = new LinkedList();
		
		if (searchAlgoType.length > 0) {
			tempCollatedList = smartSearch(queryString, 
					bufferedTaskList, searchType, searchAlgoType[0]);
		} else {
			tempCollatedList =  smartSearch(queryString, 
					bufferedTaskList, searchType, SEARCH_TYPES.TYPE_ALL);	
		}
		//Finally return the collated list of matched task
		return tempCollatedList;
	}	
	
	
	//@author A0112898U
	/**
	 * Searches the user search inputs,  with the list of tasks via 
	 * name/label/description.
	 * ONLY Searches a char if present with the tokenized inputs, 
	 * check the char with the 1st letter of every string if applies, 
	 * matched tasks is then added to the list of return linkedlist string
	 * 
	 * @param collatedMatchedTaskList - accepts a LinkedList<Task> type list 
	 *                                  that has been previously 
	 * 						            initiated/accumulated 
	 * 
	 * @param bufferedTaskList - List of task that have been added by user
	 * 
	 * @return returns the newly collated list
	 */
	private static LinkedList<Task> startLetterSearch(String queryString,
			LinkedList<Task> collatedMatchedTaskList, 
			LinkedList<Task> bufferedTaskList, SEARCH_TYPES searchType) {
		
		LinkedList<Task> tempCollatedList = new LinkedList<Task>();
		
		//Tokenize the search input
		tokenizedInputs = tokenizeSearchInput(queryString);
		
		for (String searchString:tokenizedInputs) {
			//if the token is a character
			if (searchString.length() == 1) {
				//Compare the token with the list of stored tasks
				for (Task t:bufferedTaskList) { 
					//Tokenized the searchType string
					LinkedList<LinkedList<String>> chkToks = 
							new LinkedList<LinkedList<String>>();
					
					switch (searchType){
						case TYPE_ALL:
						
						case TYPE_DESCRIPTION:
							chkToks.add(tokenizeSearchInput(
									t.getDescription().toLowerCase()));
							
							if (searchType == SEARCH_TYPES.TYPE_DESCRIPTION) {
								break;
							}
							
						case TYPE_NAME:
							chkToks.add(tokenizeSearchInput(
									t.getName().toLowerCase()));
							
							if (searchType == SEARCH_TYPES.TYPE_NAME) {
								break;
							}
							
						case TYPE_LABEL:
							chkToks.add(tokenizeSearchInput(
									t.getLabelName().toLowerCase()));
							
							if (searchType == SEARCH_TYPES.TYPE_LABEL) {
								break;
							}
					}
					
					for (LinkedList<String> chkTok:chkToks) {
						boolean isTaskAdded = false;
						
						for (String chkT:chkTok) {	
							//Search through the first Letter of every word
							if (searchString.equals(
									Character.toString(chkT.charAt(0)))) {
								if (!isTaskExist(tempCollatedList, t)) {
									tempCollatedList.add(t);
									isTaskAdded = true;
									break; //Break out of the current task
								}
							}
						}
						//if task is already added don't need to check 
						//for the next type for double security of check 
						//duplicated task purpose!
						if (isTaskAdded) {
							break;
						}
					}
				}
			}
		}
		return tempCollatedList;
	}
	
	
	//@author A0112898U
	/**
	 * Searches the user search inputs, with the list of tasks via 
	 * name/label/description, Simple substring search via .contains with 
	 * the tokenized inputs if applies, matched task is added to the list 
	 * of return linkedlist string
	 * 
	 * ***IMPT***
	 * This search only searches 2char and above string because, this system 
	 * assume users uses 1 char as the heading of the word to be search, 
	 * which is covered in and advance search by 'startLetterSearch'
	 * 
	 * @param collatedList - accepts a LinkedList<Task> type list that has been
	 * 						 previously initiated/accumulated 
	 * @param bufferedTaskList - List of task that have been added by user
	 * @return returns the newly collated list
	 */
	private static LinkedList<Task> matchWordSearch(String queryString, 
			LinkedList<Task> collatedMatchedTaskList, 
			LinkedList<Task> bufferedTaskList, 
			SEARCH_TYPES searchType, 
			boolean isPowerSearch) {
	
		LinkedList<Task> tempCollatedList = 
				new LinkedList(collatedMatchedTaskList);
		
		//Tokenize the search input
		tokenizedInputs = tokenizeSearchInput(queryString);
		
		//Search each query string through all the stored task via name
		for (String searchString:tokenizedInputs) {
			//removes single character search if is not pwoer search, 
			//this is to prevent a whole cluster of search as the single 
			//character is already considered as only the start of each string
			if (searchString.length() <= 1 && !isPowerSearch) {
				break;
			}
			
			for (Task t:bufferedTaskList) { 
				//Tokenized the searchType string
				LinkedList<String> chkString = new LinkedList<String>();
				
				switch (searchType) {
					case TYPE_ALL:
						
					case TYPE_DESCRIPTION:
						chkString.add(t.getDescription().toLowerCase());
						
						if (searchType == SEARCH_TYPES.TYPE_DESCRIPTION) {
							break;
						}
						
					case TYPE_NAME:
						chkString.add(t.getName().toLowerCase());
						
						if (searchType == SEARCH_TYPES.TYPE_NAME) {
							break;
						}
						
					case TYPE_LABEL:
						chkString.add(t.getLabelName().toLowerCase());
						
						if (searchType == SEARCH_TYPES.TYPE_LABEL) {
							break;
						}
				}
				
				for (String chkStr:chkString) {
					if (chkStr.contains(searchString)) {						
						if (!isTaskExist(tempCollatedList, t)) {
							tempCollatedList.add(t);
							break; //Break out of the current task if added
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
	 * @param collatedList - accepts a LinkedList<Task> type list that has  
	 * 						 been previously initiated/accumulated 
	 * @param bufferedTaskList - List of task that have been added by user
	 * @return LinkedList<Task> - Returns the list of tasks that matches 
	 *                            the search.
	 */
	public static LinkedList<Task> smartSearch(String searchLine, 
			LinkedList<Task> bufferedTaskList, 
			SEARCH_TYPES searchType, 
			SEARCH_TYPES searchAlgoType){
		
		//Task that is collated through the searches.
		LinkedList<Task> matchedTasks = new LinkedList<Task>(); 
		
		switch (searchAlgoType) {
			case SEARCH_POWER_SEARCH:
				LinkedList<LinkedList<?>> returnList = powerSearch(searchLine,
						matchedTasks, bufferedTaskList, searchType);
				
				if (!(returnList.get(0).size() == 0)) {
					suggestInputs = 
							new LinkedList<String>(
									(LinkedList<String>)returnList.get(1));	
				} else {
					suggestInputs.add(NO_MATCH_FOUND);
				}
				return (LinkedList<Task>) returnList.get(0); 
					
			case TYPE_ALL:
			
			case SEARCH_START_LETTER:
				matchedTasks.addAll(startLetterSearch(searchLine, 
						matchedTasks, bufferedTaskList, searchType));
				
				if (searchAlgoType == SEARCH_TYPES.SEARCH_START_LETTER) {
					break;
				}
				
			case SEARCH_MATCH_WORD:
				matchedTasks.addAll(matchWordSearch(searchLine, 
						matchedTasks, bufferedTaskList,searchType, false));
				
				if (searchAlgoType == SEARCH_TYPES.SEARCH_MATCH_WORD) {
					break;
				}
		}
		return removeDuplicate(matchedTasks);
	}
	
	
	//@author A0112898U
	/**
	 * Getter method to get the suggested string.
	 * 
	 * @return LinkedList<String> returns the suggested inputs
	 */
	public static LinkedList<String> getSuggestedString() {
		return suggestInputs;
	}
	

	//@author A0112898U
	/**
	 * !!! IMPORANT WARNING !!! 
	 * Do this search ONLY WHEN other match case = null collated List !!!
	 * Or if user activates
	 * 
	 * Searches the user search inputs, with the list of tasks via description,
	 * Extensive search with percentage of letter containment with suggestion 
	 * to user to detect similar strings via the user inputs
	 * if matches 40% and above by letter diff den add task to collatedList
	 * if matches 50% by letter containment den suggest 
	 * 
	 * 
	 * @param collatedList - accepts a LinkedList<Task> type list that has been
	 * 						 previously initiated/accumulated 
	 * 
	 * @param bufferedTaskList - List of task that have been added by user
	 * 
	 * @return returns List of List, the first list represents the matched 
	 * tasks, the 2nd list represents the suggested keywords
	 */
	public static LinkedList<LinkedList<?>> powerSearch(String queryString, 
			LinkedList<Task> collatedMatchedTaskList, 
			LinkedList<Task> bufferedTaskList, SEARCH_TYPES searchTypes) {

		int PERCENT_TO_ADD = 40;
		int PERCENT_TO_SUGGEST = 50;
		int PERCENT_MATCHED_STR = 100;
		
		LinkedList<Task> tempCollatedList = 
				new LinkedList(collatedMatchedTaskList);
		LinkedList<String> suggestedString = new LinkedList<String>();
		
		//Breaks the task via tokenization method and search through
		tokenizedInputs = tokenizeSearchInput(queryString);
	
		//If possible return suggestion like if user types ankalp but 
		//ankalp is contained in sankalp
		for (String tok:tokenizedInputs) {
			for (Task t:bufferedTaskList) {	
				switch (searchTypes) {				
					case TYPE_ALL:
					
					case TYPE_DESCRIPTION:
						//Double security to check if task has been added
						if (!isTaskExist(collatedMatchedTaskList,t)) {
							LinkedList<String> tokenizeDescription 
									= tokenizeSearchInput(
											t.getDescription().toLowerCase());
							
							//Check against each word in the Description
							for (String desTok:tokenizeDescription) {
								if (chkStrSimilarity(tok,desTok) 
										> PERCENT_TO_ADD) {
									//Check how similar the 2 strings are
									if ((chkStrSimilarity(tok, desTok) 
											> PERCENT_TO_SUGGEST)
											&& (chkStrSimilarity(tok, desTok) 
													!= PERCENT_MATCHED_STR)) {
										suggestedString.add(desTok);
									}
									collatedMatchedTaskList.add(t);
									break; //break out loop if task is added
								}
							}
						}
						
						if (searchTypes == SEARCH_TYPES.TYPE_DESCRIPTION) {
							break;
						}
					
					case TYPE_NAME:
						//Search Names
						if (!isTaskExist(collatedMatchedTaskList,t)) {
							LinkedList<String> tokenizeName
							= tokenizeSearchInput(t.getName().toLowerCase());
							
							for (String nameTok:tokenizeName) {
								//Check against names
								if (chkStrSimilarity(tok, nameTok) 
										> PERCENT_TO_ADD) {
									if((chkStrSimilarity(tok, nameTok) 
											> PERCENT_TO_SUGGEST) 
											&& (chkStrSimilarity(tok, nameTok)
													!= PERCENT_MATCHED_STR)) {
										
										suggestedString.add(nameTok);
									}
									collatedMatchedTaskList.add(t);
									break; //break out loop if task is added
								}
							}
						}
						
						if (searchTypes == SEARCH_TYPES.TYPE_NAME) {
							break;
						}
					
					case TYPE_LABEL:
						//Double security to check if task has been added
						if (!isTaskExist(collatedMatchedTaskList, t)) {
							LinkedList<String> tokenizeLabel
									= tokenizeSearchInput(
											t.getLabelName().toLowerCase());
							
							//Check against each word in the label
							for (String labelTok:tokenizeLabel) {
								if (chkStrSimilarity(tok, labelTok) 
										> PERCENT_TO_ADD) {
									if ((chkStrSimilarity(tok, labelTok) 
											> PERCENT_TO_SUGGEST)
											&& (chkStrSimilarity(tok, labelTok)
													!= PERCENT_MATCHED_STR)) {
										
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
						logger.log(Level.WARNING, "LogicSearch-SearchType not"
								+ "supported!");
						break;
				}
			}
		}
		
		LinkedList<LinkedList<?>> returnList = new LinkedList<LinkedList<?>>();
		
		returnList.add(collatedMatchedTaskList);
		returnList.add(suggestedString);
		return returnList;
	}
	
	//@author A0112898U
	/**
	 * String similarity checking and returns a percentage of
	 * how similar the strings are
	 * 
	 * @param s1 - First string to check with.
	 * @param s2 - Secnd string to check with.
	 * @return double The percentage of the similarity of strings
	 */
    public static double chkStrSimilarity(String s1, String s2) {
    	// s1 should always be bigger, for easy check thus the swapping.
    	if (s2.length() > s1.length()) {
            String tempStr = s1; 
            s1 = s2; 
            s2 = tempStr;
        }
    	
    	int FULL_PERCENT = 100;
        int bigLen = s1.length();
        
        if (bigLen == 0) { 
        	return FULL_PERCENT; 
        }
        return ((bigLen - computeEditDistance(s1, s2)) /
        		(double) bigLen) * FULL_PERCENT;
    }

    
    //@author A0112898U
    /**
     * Computes the distance btw the 2 strings, via the 
     * Levenshtein Distance Algorithm
     * 
	 * @param s1 - First string to check with.
	 * @param s2 - Secnd string to check with.
     * @return the new Cost to change to make the string same
     */
    public static int computeEditDistance(String s1, String s2) {
    	
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        int[] costToChange = new int[s2.length() + 1];
        
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                	costToChange[j] = j;
                }  else {
                    if (j > 0) {
                        int newValue = costToChange[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                            newValue = Math.min(Math.min(newValue, lastValue),
                            		costToChange[j]) + 1;
                        }
                        costToChange[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0) {
            	costToChange[s2.length()] = lastValue;
            }
        }
        return costToChange[s2.length()];
    }

	
	//@author A0112898U
	/**
	 * Checks if the task to be added is already in the collated list of 
	 * matched task
	 * 
	 * Reason why not to use the '.contains' but to create and use this 
	 * function is because the '.equals' in task was override by the 
	 * original creator for other important checking purpose.
	 *
	 * @param currentCollatedTasks the added list of task to check with
	 * @param tobeAddedTask task that is to be added to check if it existed
	 * 
	 * @return returns true if task is already present in the collated task, 
	 * and returns false if task hasn't been found in the collated task
	 */
	public static boolean isTaskExist(LinkedList<Task> currentCollatedTasks, 
			Task taskToAdd) {
		
		for (Task t:currentCollatedTasks) {
			if (t.getName().equals(taskToAdd.getName()) 
					&& t.getDescription().equals(taskToAdd.getDescription())
					&& (t.getTimeStamp() == taskToAdd.getTimeStamp())
					&& (t.getLabel() == taskToAdd.getLabel())
					) {
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
	 * @return returns a new linkedlist with any duplicated task removed 
	 */
	private static LinkedList<Task> removeDuplicate(
			LinkedList<Task> currentCollatedTasks){
		
		LinkedList<Task> tempList = new LinkedList<Task>(currentCollatedTasks);
		
		Set<Task> newSet = new HashSet(tempList);
		LinkedList<Task> tempList2 =  new LinkedList<Task>(newSet);
		
		return tempList2;
	}
	
}
