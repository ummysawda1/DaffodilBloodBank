package com.bloodbank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UpdateDonorPanel extends JPanel {
    private MainFrame parentFrame;
    private Donor donor;

    public UpdateDonorPanel(MainFrame frame, Donor donor) {
        this.parentFrame = frame;
        this.donor = donor;
        setLayout(new BorderLayout());
        
        // Top banner
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(204, 0, 0));
        topPanel.setPreferredSize(new Dimension(800, 80));
        JLabel bankNameLabel = new JLabel("Daffodil Blood Bank", SwingConstants.CENTER);
        bankNameLabel.setFont(new Font("Arial", Font.BOLD, 36));
        bankNameLabel.setForeground(Color.WHITE);
        topPanel.add(bankNameLabel);
        add(topPanel, BorderLayout.NORTH);
        
        // Form panel using GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel formTitle = new JLabel("Update Donor Information", SwingConstants.CENTER);
        formTitle.setFont(new Font("Arial", Font.BOLD, 28));
        formTitle.setForeground(new Color(102, 51, 153));
        formPanel.add(formTitle, gbc);
        gbc.gridwidth = 1;
        
        // Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(donor.getName(), 20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(nameField, gbc);
        
        // Blood Group
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel bloodGroupLabel = new JLabel("Blood Group:");
        bloodGroupLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(bloodGroupLabel, gbc);
        gbc.gridx = 1;
        JTextField bloodGroupField = new JTextField(donor.getBloodGroup(), 20);
        bloodGroupField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(bloodGroupField, gbc);
        
        // Location
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(locationLabel, gbc);
        gbc.gridx = 1;
        JTextField locationField = new JTextField(donor.getLocation(), 20);
        locationField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(locationField, gbc);
        
        // Contact Info
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel contactLabel = new JLabel("Contact Info:");
        contactLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(contactLabel, gbc);
        gbc.gridx = 1;
        JTextField contactField = new JTextField(donor.getContactInfo(), 20);
        contactField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(contactField, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(donor.getPassword(), 20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(passwordField, gbc);
        
        // Availability
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel availableLabel = new JLabel("Available:");
        availableLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(availableLabel, gbc);
        gbc.gridx = 1;
        JCheckBox availableCheck = new JCheckBox();
        availableCheck.setSelected(donor.isAvailable());
        formPanel.add(availableCheck, gbc);
        
        // Button panel for Update and Back buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        JButton updateBtn = new JButton("Update");
        updateBtn.setFont(new Font("Arial", Font.BOLD, 16));
        updateBtn.setBackground(new Color(51, 153, 255));
        updateBtn.setForeground(Color.WHITE);
        buttonPanel.add(updateBtn);
        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        backBtn.setBackground(new Color(100, 100, 100));
        backBtn.setForeground(Color.WHITE);
        buttonPanel.add(backBtn);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Action for update button
        updateBtn.addActionListener(e -> {
            // Gather updated info
            String name = nameField.getText().trim();
            String bloodGroup = bloodGroupField.getText().trim();
            String location = locationField.getText().trim();
            String contact = contactField.getText().trim();
            String password = new String(passwordField.getPassword());
            boolean available = availableCheck.isSelected();
            
            // Update donor object
            donor.setName(name);
            donor.setBloodGroup(bloodGroup);
            donor.setLocation(location);
            donor.setContactInfo(contact);
            donor.setPassword(password);
            donor.setAvailable(available);
            
            // Update in database
            new DonorDAO().updateDonor(donor);
            JOptionPane.showMessageDialog(UpdateDonorPanel.this, "Donor information updated successfully!");
            
            // Return to admin panel
            parentFrame.setContentPane(new AdminPanel(parentFrame));
            parentFrame.validate();
        });
        
        // Action for back button
        backBtn.addActionListener(e -> {
            parentFrame.setContentPane(new AdminPanel(parentFrame));
            parentFrame.validate();
        });
    }
}
