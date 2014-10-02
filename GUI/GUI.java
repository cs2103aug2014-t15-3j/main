/**
 * @author Sankalp 
 * 
 * I have done a very basic looking one. This is all I could do so far. I am still unclear
 * on how to integrate it with Main or Logic.  
 * 
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;


public class GUI {

	private JFrame frame;
	private static JTextArea mainDisplay;
	private static JTextField inputField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				inputField.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg){
						/*
						 * Below is just an example of what will happen when user presses enter after 
						 * typing something in the user input text field at the bottom of the GUI.
						 */
						String inputStr = inputField.getText(); //this is to get the user input text
						String displayStr = mainDisplay.getText();
						if(displayStr.equals("Welcome to Remembra!")){
							displayStr = "";
						}
						//Could be main or could be logic
						//Main control = new Main(inputStr);
						
						
						//this set the text of the main display text field
						mainDisplay.setText(displayStr + inputStr + "\n");
						//this set the text of user input text field
						inputField.setText("");
					}
				});
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Remembra");
		frame.getContentPane().setBackground(new Color(135, 206, 235));
		frame.setBounds(100, 100, 469, 318);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(10, 239, 432, 30);
		inputPanel.setBackground(new Color(240, 248, 255));
		frame.getContentPane().add(inputPanel);
		inputPanel.setLayout(new FlowLayout());
		
		inputField = new JTextField();
		inputPanel.add("SOUTH", inputField);
		inputField.setColumns(38);
		
		JPanel displayPanel = new JPanel();
		displayPanel.setBounds(10, 10, 432, 218);
		frame.getContentPane().add(displayPanel);
		displayPanel.setLayout(new FlowLayout());
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(411, 0, 21, 220);
		displayPanel.add(scrollPane, BorderLayout.EAST);
		
		mainDisplay = new JTextArea();
		frame.getContentPane().add(mainDisplay);
		mainDisplay.setColumns(35);
		mainDisplay.setRows(15);
		mainDisplay.setForeground(new Color(0, 0, 0));
		mainDisplay.setFont(new Font("Beatnik SF", Font.BOLD, 13));
		mainDisplay.setBackground(new Color(211, 211, 211));
		mainDisplay.setEditable(false);
		mainDisplay.setText("Welcome to Remembra!");
		mainDisplay.setBounds(14, 22, 323, 120);
		displayPanel.add(mainDisplay);
		
		//frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}
