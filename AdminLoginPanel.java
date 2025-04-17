package com.bloodbank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLoginPanel extends JPanel {
    private MainFrame parentFrame;

    public AdminLoginPanel(MainFrame frame) {
        this.parentFrame = frame;
        setLayout(new BorderLayout());

        // Top banner panel for the bank name
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(204, 0, 0));  // Rich red background
        topPanel.setPreferredSize(new Dimension(800, 80));
        JLabel bankNameLabel = new JLabel("Daffodil Blood Bank");
        bankNameLabel.setFont(new Font("Arial", Font.BOLD, 36));
        bankNameLabel.setForeground(Color.WHITE);
        topPanel.add(bankNameLabel);
        add(topPanel, BorderLayout.NORTH);

        // Main form panel using GridBagLayout for the login form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel title = new JLabel("Admin Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(102, 51, 153)); // Deep purple
        formPanel.add(title, gbc);
        gbc.gridwidth = 1;  // Reset
        
        // Username Label and Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(userLabel, gbc);
        
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(usernameField, gbc);
        
        // Password Label and Field
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(passLabel, gbc);
        
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(passwordField, gbc);
        
        // Login Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(0, 153, 76)); // Green background
        loginButton.setForeground(Color.WHITE);
        formPanel.add(loginButton, gbc);
        
        // Back Button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(100, 100, 100)); // Gray background
        backButton.setForeground(Color.WHITE);
        formPanel.add(backButton, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Action Listener for Login Button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String adminUsername = usernameField.getText().trim();
                String adminPassword = new String(passwordField.getPassword());
                
                if(adminUsername.isEmpty() || adminPassword.isEmpty()){
                    JOptionPane.showMessageDialog(AdminLoginPanel.this, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Use the loginAdmin method from DonorDAO to verify credentials
                Admin admin = new DonorDAO().loginAdmin(adminUsername, adminPassword);
                if(admin != null) {
                    // Login successful; navigate to the Admin dashboard (AdminPanel)
                    parentFrame.setContentPane(new AdminPanel(parentFrame));
                    parentFrame.validate();
                } else {
                    JOptionPane.showMessageDialog(AdminLoginPanel.this, "Invalid admin credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Action Listener for Back Button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Return to the main dashboard
                parentFrame.setContentPane(new DashboardPanel(parentFrame));
                parentFrame.validate();
            }
        });
    }
}
