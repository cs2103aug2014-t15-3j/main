
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.sun.awt.AWTUtilities;
/*
 * Notification Dialog
 * 
 * How to use:
 * Create a new object and pass appropriate parameters. e.g.
 * Notification n = new Notification(TaskName, TaskDescription, TaskDeadline, TaskStartTime, TaskEndTime);
 * n.display();
 * 
 * <<Sample in GuiMain's launch()>>
 * 
 * The TimerButtons created do actually work. And their time can be changed while creating them
 * 
 */

public class Notification  extends JDialog {
	String taskName;
	String taskDesc;
	String deadline;
	String start;
	String end;
	
	public Notification(String task, String desc, String dueDate, String s, String e){
		taskName = task;
		taskDesc = desc;
		deadline = dueDate;
		start = s;
		end = e;
	}
	
	/**
	 * Creates the notification
	 */
    public void display() {
    	
        this.setLayout(new GridLayout(0,1));
    	this.setTitle("Remembra REMINDER");
    	
    	AWTUtilities.setWindowOpaque(this, false);
		this.setAlwaysOnTop(true);
		this.setAutoRequestFocus(true);
		//this.setFocusTraversalKeysEnabled(false);
		
    	JPanel panel = new JPanel();
    	panel.setBackground(new Color(204, 0, 51));		
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(SystemColor.controlHighlight);
		
		JPanel panel3 = new JPanel();
		panel3.setBackground(SystemColor.controlHighlight);
				
    	JLabel name = new JLabel(taskName);
    	name.setFont(new Font("WhitneyBook", Font.BOLD, 17));
    	name.setForeground(Color.white);
    	JLabel desc = new JLabel(taskDesc);
    	desc.setFont(new Font("WhitneyBook", Font.PLAIN, 15));
    	JLabel date = new JLabel(deadline);
    	date.setFont(new Font("WhitneyBook", Font.PLAIN, 15));
    	
    	this.add(panel);
    	this.add(panel2);
    	this.add(panel3);
    	
    	panel.add(name);
        panel2.add(desc);
        panel3.add(date);
        
        //To check whether there is start or end 
        //(assuming there is no deadline but a time slot instead)
    	if (!(start.isEmpty()) || !(end.isEmpty())) {
    		
    		JLabel startlb = new JLabel(start);
    		JLabel endlb = new JLabel(end);
    		JPanel panel4 = new JPanel();
    		
    		this.add(panel4);
    		
    		panel4.setBackground(SystemColor.controlHighlight);
    		panel3.remove(date);
    		panel3.add(startlb);
    		panel4.add(endlb);
    		
    		startlb.setFont(new Font("WhitneyBook", Font.PLAIN, 15));
    		endlb.setFont(new Font("WhitneyBook", Font.PLAIN, 15));
    		
    	}
        
        this.add(new TimerButton("Remind in a second", 1000));
        this.add(new TimerButton("Remind in a minute", 60 * 1000));
        //this.add(new TimerButton("Back in an hour", 60 * 60 * 1000));
        
        this.pack();
        this.setSize(400,300);
        
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();// size of the screen
        Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());// height of the task bar
        this.setLocation(scrSize.width - this.getWidth(), scrSize.height - toolHeight.bottom - this.getHeight());
        
        this.setVisible(true);

        
        fadeOut(this);
    }
    
    /**
     * Fades out the reminder after 10 secs
     */
	private void fadeOut(final JDialog dialog) {
		final Timer timer = new Timer(100, null);
		timer.setRepeats(true);
		timer.setInitialDelay(10*1000); //10 *1000 for 10 seconds
		
		timer.addActionListener(new ActionListener() {
			private float opacity = 1;
			@Override public void actionPerformed(ActionEvent e) {
				opacity -= 0.05f;
				dialog.setOpacity(Math.max(opacity, 0));
				if (opacity <= 0) {
					timer.stop();
					dialog.setVisible(false);
				}
			}
		});
		
		dialog.setOpacity(1);
		
		timer.start();
	}

    /** 
     * A button that hides the window for a specific time
     *  
     */ 
    private class TimerButton extends JButton {

        private final Timer timer;

        public TimerButton(String text, int delay) {
            super(text);
            this.addActionListener(new StartListener());
            timer = new Timer(delay, new StopListener());
        }

        private class StartListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                Notification.this.setVisible(false);
                timer.start();
            }
        }

        private class StopListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                Notification.this.setVisible(true);
            }
        }
    }

}
