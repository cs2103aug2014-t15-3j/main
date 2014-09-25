/**
 * @author Bay Chuan Wei Candiie
 *
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class StorageMain {
	
	
	//TUT - PART 3 - Furthur explaination on codes & changing people's code	
	
	//As you can see i've implemented a static function so that the Logic can just call
	//the function without the need to create an object, this is only applicable for 
	//certain function calls and so, but i've included
	//here so that you might want to implement in your future codes XD
	

	static void save(Task newTask) throws IOException{

	//Note that, it is entirely up to the creator of the class to create the function 
	//the way they want it and
	//others should only give suggestions, it is still up to the creator to change it or not
	//because he/she might have a reason why they wanted to create it that way	
		
		//Temp Codes
		File file = null;
		
		file = new File(Long.toString(newTask.getTimeStamp()));
		BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));

		fileWriter.write(newTask.getName().trim());
		fileWriter.newLine();
		fileWriter.flush();
		fileWriter.close();
		
		
	//WARNING : Observe that due to fileWriter, I've to add a 'throws IOException' which also have 
	//to be implemented
	//by the function which calls this function, which means that I'll have to change LogicMain's code
	
	//In situations like this, 
	//If there's a work around, do the work around method, for example a try/catch however if there's
	// really no choice, do inform the person whose code is being change beforehand first before changing
	// their codes.
		
	//Lets move back to Main.java for the last part of this tutorial.
		
	}
	

}
