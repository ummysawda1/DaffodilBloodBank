package com.bloodbank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginPanel extends JPanel {
    private MainFrame parentFrame;
    
    public LoginPanel(MainFrame frame) {
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
        
        // Center panel for the login form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Subtitle label
        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(102, 51, 153)); // Deep purple
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(title, gbc);
        
        // Username Label
        gbc.gridwidth = 1;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        userLabel.setForeground(new Color(0, 0, 102));  // Dark blue
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(userLabel, gbc);
        
        // Username Field
        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(usernameField, gbc);
        
        // Password Label
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passLabel.setForeground(new Color(0, 0, 102));  // Dark blue
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(passLabel, gbc);
        
        // Password Field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(passwordField, gbc);
        
        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(0, 153, 76));  // Green background
        loginButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(loginButton, gbc);
        
        // Register Button
        JButton registerButton = new JButton("Register as Donor");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setBackground(new Color(51, 153, 255)); // Blue background
        registerButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(registerButton, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Bottom panel for Back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(100, 100, 100));
        backButton.setForeground(Color.WHITE);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Action Listeners
        loginButton.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            DonorDAO donorDAO = new DonorDAO();
            Donor donor = donorDAO.login(username, password);
            if (donor != null) {
                // Login successful; navigate to donor profile (or dashboard)
                parentFrame.setContentPane(new DonorProfilePanel(parentFrame, donor));
                parentFrame.validate();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        registerButton.addActionListener((ActionEvent e) -> {
            parentFrame.setContentPane(new RegistrationPanel(parentFrame));
            parentFrame.validate();
        });
        
        backButton.addActionListener((ActionEvent e) -> {
            parentFrame.setContentPane(new DashboardPanel(parentFrame));
            parentFrame.validate();
        });
    }
}
