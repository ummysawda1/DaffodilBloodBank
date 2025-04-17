package com.bloodbank;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class MainFrame extends JFrame {
	public MainFrame() {
	    setTitle("Blood Bank Management System");
	    setSize(800, 600);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
	    
	    // Set DashboardPanel as the initial content
	    setContentPane(new DashboardPanel(this));
	}


}
