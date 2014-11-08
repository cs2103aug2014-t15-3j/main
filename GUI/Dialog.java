import com.sun.awt.AWTUtilities;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * @author A0116160W
 */
@SuppressWarnings("serial")
public class Dialog extends JDialog {
	public String title;
	static String taskName;
	static String taskDesc;
	static String labelName;
	static String deadline;
	private String command;
	private JLabel taskNameLabel;
	private JLabel taskDescLabel;
	private JLabel labelNameLabel;
	private JLabel deadlineLabel;

	public Dialog(JFrame parent, String dialogBoxTitle, String tN, String tD, String lb, String dl, String cd){
		super(parent, true);
		title = dialogBoxTitle;
		taskName = tN;
		taskDesc = tD;
		labelName = lb;
		deadline = dl;
		command = cd;

	}
	
	public void displayDialog() {

		this.setLayout(new GridBagLayout());
		setUndecorated(true);
		AWTUtilities.setWindowOpaque(this, false);


		setTitle(title);

		taskNameLabel = new JLabel(taskName);
		taskNameLabel.setForeground(Color.DARK_GRAY);
		taskNameLabel.setFont(new Font("WhitneyBook", Font.PLAIN, 15));		
		taskDescLabel = new JLabel(taskDesc);
		taskDescLabel.setForeground(Color.DARK_GRAY);
		taskDescLabel.setFont(new Font("WhitneyBook", Font.PLAIN, 15));
		labelNameLabel = new JLabel(labelName);
		labelNameLabel.setForeground(Color.DARK_GRAY);
		labelNameLabel.setFont(new Font("WhitneyBook", Font.PLAIN, 15));
		deadlineLabel = new JLabel(deadline);
		deadlineLabel.setForeground(Color.DARK_GRAY);
		deadlineLabel.setFont(new Font("WhitneyBook", Font.PLAIN, 15));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setLayout(new GridLayout(0,1));
		panel.add(taskNameLabel);
		panel.add(taskDescLabel);
		panel.add(labelNameLabel);
		panel.add(deadlineLabel);
		panel.setPreferredSize(new Dimension(300, 150));


		// the following two lines are only needed because there is no
		// focusable component in here, and the "hit escape to close" requires
		// the focus to be in the dialog. If you have a button, a textfield,
		// or any focusable stuff, you don't need these lines.
		panel.setFocusable(true);
		panel.requestFocusInWindow();

		getContentPane().add(panel);

		DialogFX.createDialogBackPanel(this, GuiMain.frameRemembra.getContentPane(), command);
		DialogFX.addEscapeToCloseSupport(this, true);
		disappear(this);

	}
	void disappear(JDialog d){
		//current delay time 2.5 secs
		Timer timer = new Timer(2500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DialogFX.fadeOut(d);
			}
		});
		timer.setRepeats(false); // Only execute once
		timer.start(); 
	}
}
