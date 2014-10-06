/**
 * @author Samuel Lim Yi Jie
 *
 */

public class Label {
	
	private String name;
	private String color;
	private long timeStamp;
	
	public Label(String name) {
		this(name, "#80A6CD");
	}
	
	public Label(String name, String color) {
		this.name = name;
		this.color = color;
		this.timeStamp = System.currentTimeMillis();
	}
	
	public String getName() {
		return name;
	}
	
	public String getColor() {
		return color;
	}
	
	public long getTimeStamp() {
		return timeStamp;
	}
	
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
