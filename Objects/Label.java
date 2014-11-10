import java.util.Random;

//@author A0111942N

public class Label implements Item, java.io.Serializable, Comparable<Label>{
	
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

		this(name, "");
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
		
		if (color.isEmpty()) {
			// Generate color
			color = "#";
			Random random = new Random();
			
			for (int i = 0; i < 3; i++) {
				int value = random.nextInt(200)+16;
				color += Integer.toHexString(value);
			}
		}
		
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
	 * Check if label contains the param _name
	 * 
	 * @param	_name	Search keyword of label name
	 * @return	Whether two labels are the same
	 */
	public boolean isLabel(String _name) {
		
		if (_name.length() < 3) {
			return name.toLowerCase().equals(_name.toLowerCase());
		} else {
			return name.toLowerCase().contains(_name.toLowerCase());
		}
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

	//@author A0111942N
	/**
	 * Mutator:
	 * Populate the variation of month lists with its keywords
	 */
	public String editState(String state) {
		this.state = state;
		return state;
	}
	
	//@author A0111942N
	/**
	 * @return	String representation of label
	 */
	@Override
	public String toString() {
		return name;
	}
	
	//@author A0111942N
	/**
	 * Use to sort the label in a list
	 */
	@Override
	public int compareTo(Label label) {
		return name.compareTo(label.getName());
	}
}
