/**
 * This class is implemented to aid the process of user's input.
 * 
 * @param operation		Stores the operation
 * @param content		Stores the information needed to carry
 * 						out the operation
 */

public class LogicInputPair {

	// Class Variables
	private String operation;
	private String content;

	//@author A0111942N
	/**
	 * Constructor When specified with operation and its content
	 */
	public LogicInputPair(String operation, String content) {
		this.operation = operation;
		this.content = content;
	}

	//@author A0111942N
	/**
	 * Accessor
	 * 
	 * @return Operation
	 */
	public String getOperation() {
		return operation;
	}

	//@author A0111942N
	/**
	 * Accessor
	 * 
	 * @return Content
	 */
	public String getContent() {
		return content;
	}

	//@author A0111942N
	/**
	 * @return String representation of this class
	 */
	@Override
	public String toString() {
		return "{" + operation + "," + content + "}";
	}
}
