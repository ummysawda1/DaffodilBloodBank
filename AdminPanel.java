package com.bloodbank;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AdminPanel extends JPanel {
    private MainFrame parentFrame;
    private JTable donorTable;
    private DefaultTableModel tableModel;
    
    public AdminPanel(MainFrame frame) {
        this.parentFrame = frame;
        setLayout(new BorderLayout());
        
        // -----------------------------
        // Top Banner Panel
        // -----------------------------
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(204, 0, 0)); // Rich red background
        topPanel.setPreferredSize(new Dimension(800, 80));
        JLabel bankNameLabel = new JLabel("Daffodil Blood Bank", SwingConstants.CENTER);
        bankNameLabel.setFont(new Font("Arial", Font.BOLD, 36));
        bankNameLabel.setForeground(Color.WHITE);
        topPanel.add(bankNameLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        
        // -----------------------------
        // Center Panel: Donor Table
        // -----------------------------
        String[] columns = { "ID", "Name", "Blood Group", "Location", "Contact Info", "Available" };
        tableModel = new DefaultTableModel(columns, 0);
        donorTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(donorTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // -----------------------------
        // Bottom Panel: Control Buttons
        // -----------------------------
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(Color.WHITE);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 16));
        refreshButton.setBackground(new Color(0, 153, 76));
        refreshButton.setForeground(Color.WHITE);
        
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 16));
        deleteButton.setBackground(new Color(204, 0, 0));
        deleteButton.setForeground(Color.WHITE);
        
        JButton updateButton = new JButton("Update");
        updateButton.setFont(new Font("Arial", Font.BOLD, 16));
        updateButton.setBackground(new Color(51, 153, 255));
        updateButton.setForeground(Color.WHITE);
        
        JButton addButton = new JButton("Add Donor");
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        addButton.setBackground(new Color(255, 153, 51));
        addButton.setForeground(Color.WHITE);
        
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(100, 100, 100));
        backButton.setForeground(Color.WHITE);
        
        bottomPanel.add(refreshButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(updateButton);
        bottomPanel.add(addButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Load donor data initially
        loadDonorData();
        
        // -----------------------------
        // Action Listeners
        // -----------------------------
        // Refresh Button
        refreshButton.addActionListener(e -> loadDonorData());
        
        // Delete Button
        deleteButton.addActionListener(e -> {
            int selectedRow = donorTable.getSelectedRow();
            if (selectedRow >= 0) {
                int donorId = (int) tableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(AdminPanel.this, 
                        "Are you sure you want to delete donor ID " + donorId + "?", 
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new DonorDAO().deleteDonor(donorId);
                    loadDonorData();
                }
            } else {
                JOptionPane.showMessageDialog(AdminPanel.this, "Please select a donor to delete.");
            }
        });
        
        // Update Button
        updateButton.addActionListener(e -> {
            int selectedRow = donorTable.getSelectedRow();
            if (selectedRow >= 0) {
                int donorId = (int) tableModel.getValueAt(selectedRow, 0);
                // Retrieve the donor from the database
                List<Donor> donors = new DonorDAO().getAllDonors();
                Donor selectedDonor = null;
                for (Donor d : donors) {
                    if (d.getId() == donorId) {
                        selectedDonor = d;
                        break;
                    }
                }
                if (selectedDonor != null) {
                    // Navigate to the update donor panel with the selected donor details.
                    parentFrame.setContentPane(new UpdateDonorPanel(parentFrame, selectedDonor));
                    parentFrame.validate();
                }
            } else {
                JOptionPane.showMessageDialog(AdminPanel.this, "Please select a donor to update.");
            }
        });
        
        // Add Button
        addButton.addActionListener(e -> {
            // Navigate to the registration panel to add a new donor.
            parentFrame.setContentPane(new RegistrationPanel(parentFrame));
            parentFrame.validate();
        });
        
        // Back Button
        backButton.addActionListener(e -> {
            // Return to the main dashboard.
            parentFrame.setContentPane(new DashboardPanel(parentFrame));
            parentFrame.validate();
        });
    }
    
    // Load donor data from the database into the table.
    private void loadDonorData() {
        List<Donor> donors = new DonorDAO().getAllDonors();
        tableModel.setRowCount(0); // Clear existing rows
        for (Donor donor : donors) {
            Object[] row = {
                donor.getId(),
                donor.getName(),
                donor.getBloodGroup(),
                donor.getLocation(),
                donor.getContactInfo(),
                donor.isAvailable() ? "Yes" : "No"
            };
            tableModel.addRow(row);
        }
    }
}
