/**
 * @author Bay Chuan Wei Candiie
 *
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;



public abstract class StorageBase{
	
	FileOutputStream fileOut;
	FileInputStream fileIn;
	ObjectOutputStream objectOut;
	ObjectInputStream objectIn;

	
	void destructStorageBase(){
		
	}
	
	void serializeObject (String filename, Object obj)throws IOException, ClassNotFoundException {
		
		fileOut = new FileOutputStream(filename+".ser");
		objectOut = new ObjectOutputStream(fileOut);
		
		objectOut.writeObject(obj);
		objectOut.close();
		fileOut.close();		
	}
	
	Object deSerializeObject (String filename, Object obj)throws IOException, ClassNotFoundException {
		
		fileIn = new FileInputStream(filename+".ser");
		objectIn = new ObjectInputStream(fileIn);
		
		obj = (Object) objectIn.readObject();
		
		objectIn.close();
		fileIn.close();
		
		return obj;
	}
	
	abstract void storeObject(Object obj);
	abstract protected Object retrieveObject();
}
