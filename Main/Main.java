import java.io.IOException;

/**
 * 
 */

/**
 * @author Bay Chuan Wei Candiie
 * @author Samuel Lim Yi Jie
 * @author Sankalp 
 *
 */
public class Main {

	public static void main(String[] args) {


		//Hi Sam, Sankalp I know its a little wordy but it'll really help :) - Chuan Wei :)
		
		//For this tutorial, I'll just use LogicMain.java and StorageMain.java for a simple
		//run through on how would the different components from our architecture calls each other
		
		//TUT - PART 1 - How does the entire project mingles with each other
		
		//To get straight to the point, there'll be an entry point to each section of the 
		//architecture component - (I'll elaborate on this point in part 2)
		
		//So here's a simple example on the interaction flow
		//on how might Logic call the Storage to save the task 
		
		//THis is just a temp code.
		LogicMain logicMainObj = new LogicMain(); 
		
		//You can call methods via 2 ways, above is one of them, where you need to create an obj first
		//I'll show you the other way to call in part 2 of this tutorial
		
		//So now lets move on to 'LogicMain.java' for PART 2 
		//DOn't move on to part 4 cos you might not be able to understand what I'm trying to convey yet :)
		
		
		
		
		
		
		//TUT - PART 4 - Running the demo and reviewing the results 
		
		//As you can see I've added a try catch here just to demo the work around, although I should have 
		//done it in StorageMain but its for explanation sake that I've left it as it is.
		
		try {
			logicMainObj.saveTask();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Now run the app and you should see a file created which marks the end of this tutorial.
		
		//Ultimately in our end project, Main.java should only consist of those process 
		//that needed only needed to be called/created once (i.e GuiMain Obj) to be called here.
		//Main should never be clutter with codes if possible.
		
		//But when we are just starting to code, all of us can use Main to call our codes of course
		//like what i did for this tutorial
		
		//(make sure to scroll till the bottom)

		/* Final Notes
		
		Hey Guys,
		
		Hope your have learn from the tutorial as I've really put in effort to plan it Lols!
		
		Here are some ground rules I hope we can follow so that we can ensure that there's no
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
		
		4. When you really need to edit someone else's code, whether it is minor or
		   not, do inform them first (as explained in the tutorial).
		   Also if you change a certain code i.e. code name and it has been called by the other person, make
		   sure to change the error for them don't leave the errors hanging! As mention in 1. NO ERRORS when
		   committing!
		
		5. Everyone have different coding standards but the QA will check codes 
		   once in awhile to ensure a common level of coding, 
		   do follow the given java coding standard as closely as possible.
		
		6. Refactor codes even once in awhile to ensure that you don't snowball.
		
		
		Yupp that should be all and happy coding :) - Chuan Wei
		
		
		
		DO SMS THE LINE CHAT WHEN YOU"RE DONE SO THAT I CAN UPDATE A NEW VERSION WITH THE COMMMENTS 
		AND EVERYTHING REMOVED SO WE ALL CAN START CODING :)
		
		- yes u might say I can remove it myself too, yes but like I've mention before, it would cause 
		trouble if it was just one person to remove and update the code .
		
		*/
	}

}




