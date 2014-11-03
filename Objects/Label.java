import java.text.SimpleDateFormat;
import java.util.Date;

//@author A0111942N

public class Label implements Item, java.io.Serializable{

	// Class variables
	private String name;
	private String color;
	private long timeStamp;
	private String state;

	//@author A0111942N
	/**
	 * Constructor:
	 * When specified with its name only
	 */
	public Label(String name) {
		this(name, "#80A6CD");
	}

	//@author A0111942N
	/**
	 * Constructor:
	 * When specified with another label object
	 */
	public Label(Label oldLabel) {
		this.name = oldLabel.getName();
		this.color = oldLabel.getColor();
		this.timeStamp = oldLabel.getTimeStamp();
	}

	//@author A0111942N
	/**
	 * Constructor:
	 * When specified with its name and color only
	 */
	public Label(String name, String color) {
		this.name = name;
		this.color = color;
		this.timeStamp = System.currentTimeMillis();
	}

	//@author A0111942N
	/**
	 * Return the label's name.
	 *
	 * @return Label's name
	 */
	public String getName() {
		return name;
	}

	//@author A0111942N
	/**
	 * Return the label's color.
	 *
	 * @return Label's color
	 */
	public String getColor() {
		return color;
	}

	//@author A0111942N
	/**
	 * Return the label's time stamp.
	 *
	 * @return Label's time stamp
	 */
	public long getTimeStamp() {
		return timeStamp;
	}
	
	//@author A0111942N
	/**
	 * Return the label's operation state.
	 *
	 * @return Label's operation state (can be empty)
	 */
	public String getState() {
		return state;
	}
	
	//@author A0111942N
	/**
	 * Edit name of label &
	 * Return the label's name.
	 *
	 * @return Label's operation state (can be empty)
	 */
	public String editName(String _name) {
		name = _name;
		return name;
	}
	
	//@author A0111942N
	/**
	 * Edit color of label &
	 * Return the label's color.
	 *
	 * @return Label's operation state (can be empty)
	 */
	public String editColor(String _color) {
		color = _color;
		return color;
	}
	
	//@author A0111942N
	/**
	 * Compare itself with another label. Return if they're the same.
	 *
	 * @return Whether two labels are the same
	 */
	public boolean isLabel(String _name) {

		return name.toLowerCase().contains(_name.toLowerCase());
	}

	//@author A0111942N
	/**
	 * Compare itself with another label. Return if they're the same.
	 *
	 * @return Whether two labels are the same
	 */
	public boolean equals(Object object) {

		if (object instanceof Label) {
			Label label = (Label) object;
			return name.equals(label.getName());
		} else {
			return false;
		}
	}

	public String editState(String state) {
		this.state = state;
		return state;
	}

	@Override
	public String toString() {
		return "Label: " + name + "\nColor: " + color + "\n\n";
	}
	
	
}
