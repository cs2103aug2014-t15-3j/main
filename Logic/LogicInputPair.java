//@author: A0111942N

public class LogicInputPair {
	
	private String operation;
	private String content;
	
	public LogicInputPair(String operation, String content) {
		this.operation = operation;
		this.content = content;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public String getContent() {
		return content;
	}
	
	@Override
	public String toString() {
		return "{"+operation+","+content+"}";
	}
}
