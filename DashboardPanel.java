package com.bloodbank;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DashboardPanel extends JPanel {
    private MainFrame parentFrame;
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private JPanel tablePanel;         // Panel that holds the search results table
    private JTextField bloodGroupField; // Moved to class scope for refresh usage
    private JTextField locationField;   // Moved to class scope for refresh usage

    public DashboardPanel(MainFrame frame) {
        this.parentFrame = frame;
        setLayout(new BorderLayout());

        // -----------------------------
        // Top Navigation Bar
        // -----------------------------
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(new Color(204, 0, 0));
        navBar.setPreferredSize(new Dimension(800, 50));

        // Left side: Logo
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(new Color(204, 0, 0));
        JLabel logoLabel = new JLabel("Daffodil Blood Bank");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 28));
        logoLabel.setForeground(Color.WHITE);
        logoPanel.add(logoLabel);
        navBar.add(logoPanel, BorderLayout.WEST);

        // Right side: Navigation Buttons (Admin, Donor, Refresh)
        JPanel navButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navButtonsPanel.setBackground(new Color(204, 0, 0));

        JButton adminPanelButton = new JButton("Admin Panel");
        styleNavButton(adminPanelButton, new Color(51, 153, 255));
        navButtonsPanel.add(adminPanelButton);

        JButton donorButton = new JButton("Donor");
        styleNavButton(donorButton, new Color(0, 153, 76));
        navButtonsPanel.add(donorButton);

        // New: Refresh button
        JButton refreshButton = new JButton("Refresh");
        styleNavButton(refreshButton, new Color(153, 153, 153));
        navButtonsPanel.add(refreshButton);

        navBar.add(navButtonsPanel, BorderLayout.EAST);
        add(navBar, BorderLayout.NORTH);

        // -----------------------------
        // Center Panel
        // -----------------------------
        JPanel centerPanel = new JPanel(new BorderLayout());

        // (1) Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel bloodGroupLabel = new JLabel("Blood Group:");
        bloodGroupLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        bloodGroupField = new JTextField(10); // store in class scope

        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        locationField = new JTextField(10);   // store in class scope

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 16));

        searchPanel.add(bloodGroupLabel);
        searchPanel.add(bloodGroupField);
        searchPanel.add(locationLabel);
        searchPanel.add(locationField);
        searchPanel.add(searchButton);
        centerPanel.add(searchPanel, BorderLayout.NORTH);

        // (2) Results + News container with vertical BoxLayout
        JPanel resultsAndNewsPanel = new JPanel();
        resultsAndNewsPanel.setLayout(new BoxLayout(resultsAndNewsPanel, BoxLayout.Y_AXIS));

        // --- Donor Results Table Panel ---
        tableModel = new DefaultTableModel(
                new String[] { "ID", "Name", "Blood Group", "Location", "Contact", "Available" }, 
                0
        );
        resultsTable = new JTable(tableModel);
        JScrollPane resultsScrollPane = new JScrollPane(resultsTable);

        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder(
                null, 
                "Search Results", 
                TitledBorder.LEFT, 
                TitledBorder.TOP, 
                new Font("Arial", Font.BOLD, 14))
        );
        tablePanel.add(resultsScrollPane, BorderLayout.CENTER);

        // Initially hide the table panel by setting its preferred height to 0
        tablePanel.setPreferredSize(new Dimension(800, 0));
        resultsAndNewsPanel.add(tablePanel);

        // --- News Panel ---
        JPanel newsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        newsPanel.setBorder(BorderFactory.createTitledBorder(
                null, 
                "Latest News", 
                TitledBorder.LEFT, 
                TitledBorder.TOP, 
                new Font("Arial", Font.BOLD, 14))
        );
        newsPanel.add(createNewsButton("Blood Drive Event: Jiboner Jonno Rokto", 
                "https://www.thedailystar.net/star-youth/news/jiboner-jonno-rokto-every-drop-counts-1645687", 
                "/com/bloodbank/images/image1.jpg"));
        newsPanel.add(createNewsButton("Donor Appreciation", 
                "https://clubs.daffodilvarsity.edu.bd/club/bdc", 
                "/com/bloodbank/images/image2.jpg"));
        newsPanel.add(createNewsButton("Health Tips", 
                "https://daffodilvarsity.edu.bd/csr-activity/anti-drug-campaign-and-blood-donation-program", 
                "/com/bloodbank/images/image3.jpg"));
        newsPanel.add(createNewsButton("Community Outreach", 
                "https://www.facebook.com/daffodilvarsity.edu.bd/posts/donate-bloodsave-life-diu-voluntary-service-club-in-association-with-daffodil-in/10155945174342203/", 
                "/com/bloodbank/images/image4.jpg"));

        resultsAndNewsPanel.add(Box.createVerticalStrut(10)); // small spacing
        resultsAndNewsPanel.add(newsPanel);

        centerPanel.add(resultsAndNewsPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // -----------------------------
        // Footer Panel
        // -----------------------------
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(Color.LIGHT_GRAY);
        JLabel footerLabel = new JLabel("© ummysawda.swe");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);

        // -----------------------------
        // Action Listeners
        // -----------------------------
        adminPanelButton.addActionListener(e -> {
            parentFrame.setContentPane(new AdminLoginPanel(parentFrame));
            parentFrame.validate();
        });

        donorButton.addActionListener(e -> {
            parentFrame.setContentPane(new LoginPanel(parentFrame));
            parentFrame.validate();
        });

        searchButton.addActionListener(e -> {
            String bloodGroup = bloodGroupField.getText().trim();
            String location = locationField.getText().trim();
            if (bloodGroup.isEmpty() && location.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, 
                        "Please enter a blood group and/or location.", 
                        "Input Required", 
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            List<Donor> results = new DonorDAO().searchDonors(bloodGroup, location);
            updateResultsTable(results);
        });

        refreshButton.addActionListener(e -> {
            // Clear the search fields
            bloodGroupField.setText("");
            locationField.setText("");
            // Hide the table again
            tableModel.setRowCount(0);
            animateTablePanel(0);
        });
    }

    // Helper method to update the results table and animate its appearance.
    private void updateResultsTable(List<Donor> donors) {
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
        if (donors.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, 
                    "No donors found for the given criteria.", 
                    "No Results", 
                    JOptionPane.INFORMATION_MESSAGE
            );
            animateTablePanel(0);
        } else {
            // Animate the table panel to a desired height, e.g., 300 pixels.
            animateTablePanel(300);
        }
    }

    // Animate the table panel's height from its current value to the target height.
    private void animateTablePanel(int targetHeight) {
        // Use a Swing Timer for animation.
        int delay = 30; // milliseconds per step
        Timer timer = new Timer(delay, null);
        timer.addActionListener(new ActionListener() {
            int currentHeight = tablePanel.getPreferredSize().height;
            @Override
            public void actionPerformed(ActionEvent e) {
                // Increase or decrease the height gradually.
                if (currentHeight < targetHeight) {
                    currentHeight += 10;
                    if (currentHeight > targetHeight) {
                        currentHeight = targetHeight;
                    }
                } else if (currentHeight > targetHeight) {
                    currentHeight -= 10;
                    if (currentHeight < targetHeight) {
                        currentHeight = targetHeight;
                    }
                } else {
                    timer.stop();
                    return;
                }
                tablePanel.setPreferredSize(new Dimension(tablePanel.getWidth(), currentHeight));
                tablePanel.revalidate();
                tablePanel.repaint();
            }
        });
        timer.start();
    }

    // Helper method to style navigation buttons
    private void styleNavButton(JButton button, Color bgColor) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
    }

    // Helper method to create a news button with a scaled image
    private JButton createNewsButton(String title, String link, String imagePath) {
        java.net.URL resourceURL = getClass().getResource(imagePath);
        if (resourceURL == null) {
            System.err.println("Resource not found: " + imagePath);
            return new JButton(title);
        }
        ImageIcon originalIcon = new ImageIcon(resourceURL);
        int desiredWidth = 150;
        int desiredHeight = 90;
        Image scaledImage = originalIcon.getImage().getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JButton button = new JButton(title, scaledIcon);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setIconTextGap(5);
        button.addActionListener(e -> {
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI(link));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        return button;
    }
}
