package examples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import arduino.*;

/*
 * IMPORTANT: Please download and import the Arduino.jar and jSerialComm-1.3.11.jar files from my GitHub or SourceForge before running.
 * NOTE: Before running, please upload and run the attached Arduino code to your board.
 */

public class LEDExampleWithGUI {
	static Arduino arduino;
	static JFrame frame = new JFrame("An Led Controller");
	static JButton btnOn = new JButton("ON");
	static JButton btnOff = new JButton("OFF");
	static JButton btnRefresh;
	
	public static void main(String[] args) {
		setUpGUI(); // refer to this function only if you have knowledge of JAVA swing classes and GUI elements.
		
		frame.setResizable(false);
		
		btnOn.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent e) {
				arduino.serialWrite('1');
				
				} 
			});
		
		btnOff.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent e) {
				arduino.serialWrite('0');
				} 
			});
	
	}
	
	public static void populateMenu(){ //gets the list of available ports and fills the dropdown menu
		final PortDropdownMenu portList = new PortDropdownMenu();
		portList.refreshMenu();
		final JButton connectButton = new JButton("Connect");
		
		
		ImageIcon refresh = new ImageIcon("/Users/HirdayGupta/Documents/workspace/Java-Arduino-Communication-Library/src/examples/refresh.png");
		btnRefresh = new JButton(refresh);
		
		JPanel topPanel = new JPanel();
		
		btnRefresh.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				portList.refreshMenu();
			
		}
		});
		topPanel.add(portList);
		topPanel.add(btnRefresh);
		topPanel.add(connectButton);
		// populate the drop-down box
		
		connectButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(connectButton.getText().equals("Connect")){
				 arduino = new Arduino(portList.getSelectedItem().toString(),9600);
				 if(arduino.openConnection()){
					 connectButton.setText("Disconnect");
					 portList.setEnabled(false);
					 btnOn.setEnabled(true);
					 btnOff.setEnabled(true);
					 btnRefresh.setEnabled(false);
					 frame.pack();
				 }
				}
				else {
					arduino.closeConnection();
					connectButton.setText("Connect");;
					portList.setEnabled(true);
					btnOn.setEnabled(false);
					btnRefresh.setEnabled(true);
					btnOff.setEnabled(false);
				}
			}
			
		});
		//topPanel.setBackground(Color.BLUE);
		frame.add(topPanel, BorderLayout.NORTH);
	}
	
	public static void setUpGUI(){
		frame.setSize(600, 600);
		frame.setBackground(Color.black);
		frame.setForeground(Color.black);
		//frame.setPreferredSize(new Dimension(600,600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		btnOn.setForeground(Color.GREEN);
		btnOn.setEnabled(false);
		btnOff.setForeground(Color.RED);
		btnOff.setEnabled(false);
		JPanel pane = new JPanel();
		//pane.setBackground(Color.blue);
		pane.add(btnOn);
		pane.add(btnOff);
		frame.add(pane, BorderLayout.CENTER);
		populateMenu();
		frame.pack();
		frame.getContentPane();
		frame.setVisible(true);
		
	}
}
