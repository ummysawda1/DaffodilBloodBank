package com.bloodbank;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set the Nimbus look and feel before creating any Swing components
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to the default look and feel
            e.printStackTrace();
        }
        
        // Launch the Swing application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
