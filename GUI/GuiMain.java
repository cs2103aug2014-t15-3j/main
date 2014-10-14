/**
 * @author Sankalp 
 * 
 * 
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

import javax.swing.ScrollPaneConstants;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class GuiMain {

	private JFrame frameRemembra;
	static JTextArea mainDisplay;
	private static JTextField inputField;
	private JScrollPane scrollPane;

	private Operations operations;
	private LogicMain logic;

	/**
	 * Launch the application.
	 */
	public void launch() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiMain window = new GuiMain();
					window.frameRemembra.setVisible(true);
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
						if(displayStr.equals("Welcome to Remembra!\nFor a quick guide, type help and press enter.")){
							displayStr = "";
						}


						//this prints the input text on the display
						mainDisplay.setText(displayStr + inputStr + "\n");
						//this clears the input field
						inputField.setText("");

						if (inputStr.equalsIgnoreCase("help")){
							GuiDisplay.displayHelp();
						}else {
							GuiDisplay.display(inputStr);
						}

					}


				});
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GuiMain() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameRemembra = new JFrame("Remembra");
		frameRemembra.setTitle("Remembra V0.1");
		frameRemembra.addWindowListener(new WindowAdapter() {
			@Override
			//this pops-up a confirmation dialog box when the user tries to exit the program
			public void windowClosing(WindowEvent arg0) {
				int option = JOptionPane.showConfirmDialog(frameRemembra, "Do you want to save changes before exiting?", "Save and Exit?", JOptionPane.YES_NO_OPTION);
				if (option == 0){

					operations = new Operations();
					logic = new LogicMain();
					String saveOperation = operations.saveOperations.get(0);
					logic.processInput(saveOperation);
				}

			}

		});
		frameRemembra.setResizable(false);
		frameRemembra.getContentPane().setBackground(new Color(0, 0, 0));
		frameRemembra.setBounds(100, 100, 587, 398);
		frameRemembra.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frameRemembra.getContentPane().setLayout(null);

		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(0, 325, 581, 34);
		inputPanel.setBackground(new Color(178, 34, 34));
		frameRemembra.getContentPane().add(inputPanel);
		inputPanel.setLayout(new FlowLayout());

		inputField = new JTextField();
		inputField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		inputField.setForeground(new Color(165, 42, 42));
		inputField.setBackground(new Color(230, 230, 250));
		inputPanel.add("SOUTH", inputField);
		inputField.setColumns(43);

		JPanel displayPanel = new JPanel();
		displayPanel.setBackground(new Color(107, 142, 35));
		displayPanel.setBounds(10, 11, 561, 303);
		frameRemembra.getContentPane().add(displayPanel);
		displayPanel.setLayout(new FlowLayout());

		scrollPane = new JScrollPane();
		displayPanel.add(scrollPane);

		mainDisplay = new JTextArea();
		scrollPane.setViewportView(mainDisplay);
		mainDisplay.setColumns(78);
		mainDisplay.setRows(19);
		mainDisplay.setForeground(new Color(65, 105, 225));
		mainDisplay.setFont(new Font("Consolas", Font.PLAIN, 12));
		mainDisplay.setBackground(new Color(230, 230, 250));
		mainDisplay.setEditable(false);
		mainDisplay.setText("Welcome to Remembra!\nFor a quick guide, type help and press enter.");
		mainDisplay.setBounds(14, 22, 323, 120);
		frameRemembra.setLocationRelativeTo(null);
	}
}
