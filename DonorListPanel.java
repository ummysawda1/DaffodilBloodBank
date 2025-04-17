package com.bloodbank;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DonorListPanel extends JPanel {
    private MainFrame parentFrame;
    private JTable donorTable;
    private JTextField bloodGroupField;
    private JTextField locationField;

    public DonorListPanel(MainFrame frame) {
        this.parentFrame = frame;
        setLayout(new BorderLayout());

        // Top banner panel with bank name and title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(204, 0, 0)); // Rich red background
        topPanel.setPreferredSize(new Dimension(800, 80));
        JLabel bankNameLabel = new JLabel("Daffodil Blood Bank", SwingConstants.CENTER);
        bankNameLabel.setFont(new Font("Arial", Font.BOLD, 36));
        bankNameLabel.setForeground(Color.WHITE);
        topPanel.add(bankNameLabel, BorderLayout.CENTER);
        
        JLabel titleLabel = new JLabel("Donor List", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);

        // Center panel: Table for displaying donors
        donorTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(donorTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Bottom panel: Contains search fields and control buttons
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Search: Blood Group
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel bloodGroupLabel = new JLabel("Blood Group:");
        bloodGroupLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        bottomPanel.add(bloodGroupLabel, gbc);
        
        gbc.gridx = 1;
        bloodGroupField = new JTextField(10);
        bloodGroupField.setFont(new Font("Arial", Font.PLAIN, 16));
        bottomPanel.add(bloodGroupField, gbc);
        
        // Search: Location
        gbc.gridx = 2;
        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        bottomPanel.add(locationLabel, gbc);
        
        gbc.gridx = 3;
        locationField = new JTextField(10);
        locationField.setFont(new Font("Arial", Font.PLAIN, 16));
        bottomPanel.add(locationField, gbc);
        
        // Search Button
        gbc.gridx = 4;
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 16));
        searchButton.setBackground(new Color(0, 153, 76));
        searchButton.setForeground(Color.WHITE);
        bottomPanel.add(searchButton, gbc);
        
        // Back Button
        gbc.gridx = 5;
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(100, 100, 100));
        backButton.setForeground(Color.WHITE);
        bottomPanel.add(backButton, gbc);
        
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Load all donors initially
        loadDonors();

        // Action listener for search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bloodGroup = bloodGroupField.getText().trim();
                String location = locationField.getText().trim();
                loadDonors(bloodGroup, location);
            }
        });
        
        // Action listener for back button to return to the login panel
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.setContentPane(new LoginPanel(parentFrame));
                parentFrame.validate();
            }
        });
    }
    
    // Load all donors without filtering
    private void loadDonors() {
        DonorDAO donorDAO = new DonorDAO();
        List<Donor> donors = donorDAO.getAllDonors();
        refreshDonorTable(donors);
    }
    
    // Load donors filtered by blood group and location
    private void loadDonors(String bloodGroup, String location) {
        DonorDAO donorDAO = new DonorDAO();
        List<Donor> donors = donorDAO.searchDonors(bloodGroup, location);
        refreshDonorTable(donors);
    }
    
    // Refresh the donor table with a given list of donors
    private void refreshDonorTable(List<Donor> donors) {
        String[] columns = { "ID", "Name", "Blood Group", "Location", "Contact Info", "Available" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (Donor donor : donors) {
            Object[] row = {
                donor.getId(),
                donor.getName(),
                donor.getBloodGroup(),
                donor.getLocation(),
                donor.getContactInfo(),
                donor.isAvailable() ? "Yes" : "No"
            };
            model.addRow(row);
        }
        donorTable.setModel(model);
    }
}
