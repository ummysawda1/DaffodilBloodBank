package com.bloodbank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DonorProfilePanel extends JPanel {
    private MainFrame parentFrame;
    private Donor donor; // The logged-in donor
    private JCheckBox availabilityCheck;

    public DonorProfilePanel(MainFrame frame, Donor donor) {
        this.parentFrame = frame;
        this.donor = donor;
        setLayout(new BorderLayout());

        // Top banner with bank name
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(204, 0, 0)); // Rich red background
        topPanel.setPreferredSize(new Dimension(800, 80));
        JLabel bankNameLabel = new JLabel("Daffodil Blood Bank");
        bankNameLabel.setFont(new Font("Arial", Font.BOLD, 36));
        bankNameLabel.setForeground(Color.WHITE);
        topPanel.add(bankNameLabel);
        add(topPanel, BorderLayout.NORTH);

        // Center panel to display donor information
        JPanel profilePanel = new JPanel(new GridBagLayout());
        profilePanel.setBackground(Color.WHITE);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Donor Name
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        profilePanel.add(nameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        JLabel nameValue = new JLabel(donor.getName());
        nameValue.setFont(new Font("Arial", Font.PLAIN, 16));
        profilePanel.add(nameValue, gbc);

        // Blood Group
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel bloodGroupLabel = new JLabel("Blood Group:");
        bloodGroupLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        profilePanel.add(bloodGroupLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        JLabel bloodGroupValue = new JLabel(donor.getBloodGroup());
        bloodGroupValue.setFont(new Font("Arial", Font.PLAIN, 16));
        profilePanel.add(bloodGroupValue, gbc);

        // Location
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        profilePanel.add(locationLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        JLabel locationValue = new JLabel(donor.getLocation());
        locationValue.setFont(new Font("Arial", Font.PLAIN, 16));
        profilePanel.add(locationValue, gbc);

        // Availability toggle
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel availableLabel = new JLabel("Available for Donation:");
        availableLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        profilePanel.add(availableLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        availabilityCheck = new JCheckBox();
        availabilityCheck.setSelected(donor.isAvailable());
        profilePanel.add(availabilityCheck, gbc);

        // Update button (to update availability)
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton updateButton = new JButton("Update Availability");
        updateButton.setFont(new Font("Arial", Font.BOLD, 16));
        profilePanel.add(updateButton, gbc);

        add(profilePanel, BorderLayout.CENTER);

        // Bottom panel with Back button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action listener for updating availability
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the donor object's availability
                donor.setAvailable(availabilityCheck.isSelected());
                // Update the record in the database
                DonorDAO donorDAO = new DonorDAO();
                donorDAO.updateDonor(donor);
                JOptionPane.showMessageDialog(DonorProfilePanel.this, "Availability updated successfully!");
            }
        });

        // Action listener for Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Go back to the donor list panel or login panel as needed
                parentFrame.setContentPane(new DonorListPanel(parentFrame));
                parentFrame.validate();
            }
        });
    }
}
