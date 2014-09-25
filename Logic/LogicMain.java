/**
 * @author Samuel Lim Yi Jie
 *
 */
import java.io.IOException;


public class LogicMain {

	
	void saveTask () throws IOException{
		
		//TUT - PART 2 - An example between LoginMain and StorageMain 
		
		//As you can see from below, Logic is Trying to access Storage
		//Try to implement your APIS in the - Main - of the archi components i.e LogicMain|StorageMain
		//so that the code connection/interactions won't be too messy.
		
		//If we follow the above structure it'll definitely ease our work in CS2103
		//I've suggested this way of coding convention bcos after analyzing our project purpose, 
		//a single Main for each components should be enough for the interaction flow. However it is not
		//restrictive :) you can implement more then one if needed but do name it XXXMain, so that 
		//others will know you've implemented functions that can be called by others
		
		//I'm trying to save time so i cheated here a little :)
		Task newTask = new Task("TaskName","LALALLA");
		
		//As mention in Part1, here's the alternative way to call a function, further explained in part 3
		StorageMain.save(newTask);
		
		//Let's move on to 'StorageMain.java' for Part3 
		
	}
}
