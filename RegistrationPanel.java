package com.bloodbank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegistrationPanel extends JPanel {
    private MainFrame parentFrame;

    public RegistrationPanel(MainFrame frame) {
        this.parentFrame = frame;
        setLayout(new BorderLayout());

        // Top banner panel for the bank name
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(204, 0, 0));
        topPanel.setPreferredSize(new Dimension(800, 80));
        JLabel bankNameLabel = new JLabel("Daffodil Blood Bank");
        bankNameLabel.setFont(new Font("Arial", Font.BOLD, 36));
        bankNameLabel.setForeground(Color.WHITE);
        topPanel.add(bankNameLabel);
        add(topPanel, BorderLayout.NORTH);

        // Form panel for registration details
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel title = new JLabel("Donor Registration", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(102, 51, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(title, gbc);
        gbc.gridwidth = 1;

        // Name Label & Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLabel.setForeground(new Color(0, 0, 102));
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(nameField, gbc);

        // Blood Group Label & Field
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel bloodGroupLabel = new JLabel("Blood Group:");
        bloodGroupLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        bloodGroupLabel.setForeground(new Color(0, 0, 102));
        formPanel.add(bloodGroupLabel, gbc);
        gbc.gridx = 1;
        JTextField bloodGroupField = new JTextField(20);
        bloodGroupField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(bloodGroupField, gbc);

        // Location Label & Field
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        locationLabel.setForeground(new Color(0, 0, 102));
        formPanel.add(locationLabel, gbc);
        gbc.gridx = 1;
        JTextField locationField = new JTextField(20);
        locationField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(locationField, gbc);

        // Contact Info Label & Field
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel contactLabel = new JLabel("Contact Info:");
        contactLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        contactLabel.setForeground(new Color(0, 0, 102));
        formPanel.add(contactLabel, gbc);
        gbc.gridx = 1;
        JTextField contactField = new JTextField(20);
        contactField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(contactField, gbc);

        // Password Label & Field
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setForeground(new Color(0, 0, 102));
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(passwordField, gbc);

        // Availability Label & Checkbox
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel availableLabel = new JLabel("Available:");
        availableLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        availableLabel.setForeground(new Color(0, 0, 102));
        formPanel.add(availableLabel, gbc);
        gbc.gridx = 1;
        JCheckBox availableCheck = new JCheckBox();
        availableCheck.setSelected(true);
        formPanel.add(availableCheck, gbc);

        // Button Panel for Register and Back buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        JButton registerBtn = new JButton("Register");
        registerBtn.setFont(new Font("Arial", Font.BOLD, 16));
        registerBtn.setBackground(new Color(51, 153, 255));
        registerBtn.setForeground(Color.WHITE);
        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        backBtn.setBackground(new Color(100, 100, 100));
        backBtn.setForeground(Color.WHITE);
        buttonPanel.add(registerBtn);
        buttonPanel.add(backBtn);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);
        
        add(formPanel, BorderLayout.CENTER);

        // Action Listeners:
        registerBtn.addActionListener((ActionEvent e) -> {
            // Collect form data
            String name = nameField.getText().trim();
            String bloodGroup = bloodGroupField.getText().trim();
            String location = locationField.getText().trim();
            String contactInfo = contactField.getText().trim();
            String password = new String(passwordField.getPassword());
            boolean available = availableCheck.isSelected();
            
            // Basic validation
            if(name.isEmpty() || bloodGroup.isEmpty() || location.isEmpty() ||
               contactInfo.isEmpty() || password.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create a new donor and register in the database
            Donor newDonor = new Donor(name, bloodGroup, location, contactInfo, password, available);
            DonorDAO donorDAO = new DonorDAO();
            donorDAO.registerDonor(newDonor);
            
            JOptionPane.showMessageDialog(this, "Registration Successful!");
            // Navigate back to the LoginPanel after successful registration
            parentFrame.setContentPane(new LoginPanel(parentFrame));
            parentFrame.validate();
        });
        
        backBtn.addActionListener((ActionEvent e) -> {
            parentFrame.setContentPane(new DashboardPanel(parentFrame));
            parentFrame.validate();
        });
    }
}
