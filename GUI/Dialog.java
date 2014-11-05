import com.sun.awt.AWTUtilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author
 */
@SuppressWarnings("serial")
public class Dialog extends JDialog {
	public String title;
	static String msg;
	private JLabel label;
	
	public void setMyTitle(String title){
		this.title = title;
	}
	public String getMyTitle (){
		return title;
	}
	public String getMyMsg(){
		return msg;
	}
	
	public void setMessage(String msg){
		Dialog.msg = msg;
		label.setText(msg);
	}
	
	public Dialog(JFrame parent) {
		super(parent, true);

		setUndecorated(true);
		AWTUtilities.setWindowOpaque(this, false);

		setTitle(title);

		label = new JLabel();
		label.setForeground(Color.BLACK);
		label.setFont(new Font(label.getFont().getFontName(), Font.PLAIN, 20));
		label.setText(msg);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setLayout(new GridBagLayout());
		panel.add(label);
		panel.setPreferredSize(new Dimension(669, 55));
		panel.setLocation(34, 501);
		
//		panel.add(new JTextField("Something\n"));
//		panel.add(new JTextField("Something\nElse"));
		// the following two lines are only needed because there is no
		// focusable component in here, and the "hit espace to close" requires
		// the focus to be in the dialog. If you have a button, a textfield,
		// or any focusable stuff, you don't need these lines.
		panel.setFocusable(true);
		panel.requestFocusInWindow();

		getContentPane().add(panel);

		DialogFX.createDialogBackPanel(this, parent.getContentPane());
		DialogFX.addEscapeToCloseSupport(this, true);
	}
}
