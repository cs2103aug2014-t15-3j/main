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
		
	}

	//@author A0112898U
	/**
	 * Runs the Shell Script to set up HotKey to start remembra in windows desktop
	 * @throws IOException
	 */
	public static void runWsShell() throws IOException {
		//Run windows PowerShell Script
		//Runtime.getRuntime().exec("wscript wsPowerShell.vbs");
	}
}




