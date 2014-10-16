//@author Samuel Lim Yi Jie

/*********************************************************************/
/******************* QA I - Refactor Code I***************************/
/*********************************************************************/
// @Sam - Label.java
//
// 1. Would it be possible if you could alter your comments of the
//    functions to the java comments structure given in the java
//	  coding standards?
//
// @Sam - Label.java
/*********************************************************************/
/*********************************************************************/

public class Label implements java.io.Serializable {
	

	
	//Class variables
	private String name;
	private String color;
	private long timeStamp;
	//private static final long serialVersionUID = 7286818833738055728L; //Have to re-generate one after everything settled so i comment out first
	
	//Constructor (When specified with label's name only)
	public Label(String name) {
		this(name, "#80A6CD");
	}
	
	//Constructor (When specified with label's name and color)
	public Label(String name, String color) {
		this.name = name;
		this.color = color;
		this.timeStamp = System.currentTimeMillis();
	}
	
	//Accessor: Returns label's name
	public String getName() {
		return name;
	}
	
	//Accessor: Returns label's color
	public String getColor() {
		return color;
	}
	
	//Accessor: Returns label's time stamp
	public long getTimeStamp() {
		return timeStamp;
	}
	
	//@author A0111942N
	//This method compares its variables with the variables of the object
	//being passed in.
	@Override
	public boolean equals(Object object) {

		if(object instanceof Label) {
			Label label = (Label) object;
			return name.equals(label.getName());
		}
		else {
			return false;
		}
	}
}
