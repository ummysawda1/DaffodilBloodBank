package com.bloodbank;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonorDAO {

    // Register a new donor
    public void registerDonor(Donor donor) {
        String sql = "INSERT INTO donors (name, blood_group, location, contact_info, password, available) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, donor.getName());
            pstmt.setString(2, donor.getBloodGroup());
            pstmt.setString(3, donor.getLocation());
            pstmt.setString(4, donor.getContactInfo());
            pstmt.setString(5, donor.getPassword());
            pstmt.setBoolean(6, donor.isAvailable());

            pstmt.executeUpdate();
            System.out.println("Donor registered successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update donor information
    public void updateDonor(Donor donor) {
        String sql = "UPDATE donors SET name=?, blood_group=?, location=?, contact_info=?, password=?, available=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, donor.getName());
            pstmt.setString(2, donor.getBloodGroup());
            pstmt.setString(3, donor.getLocation());
            pstmt.setString(4, donor.getContactInfo());
            pstmt.setString(5, donor.getPassword());
            pstmt.setBoolean(6, donor.isAvailable());
            pstmt.setInt(7, donor.getId());

            pstmt.executeUpdate();
            System.out.println("Donor updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a donor record
    public void deleteDonor(int donorId) {
        String sql = "DELETE FROM donors WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, donorId);
            pstmt.executeUpdate();
            System.out.println("Donor deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all donors
    public List<Donor> getAllDonors() {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donors";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Donor donor = new Donor(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("blood_group"),
                    rs.getString("location"),
                    rs.getString("contact_info"),
                    rs.getString("password"),
                    rs.getBoolean("available")
                );
                donors.add(donor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donors;
    }

    // Search donors by blood group and location
    public List<Donor> searchDonors(String bloodGroup, String location) {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donors WHERE blood_group = ? AND location LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bloodGroup);
            pstmt.setString(2, "%" + location + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Donor donor = new Donor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("blood_group"),
                        rs.getString("location"),
                        rs.getString("contact_info"),
                        rs.getString("password"),
                        rs.getBoolean("available")
                    );
                    donors.add(donor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donors;
    }
    
    // Login method for donors: verifies credentials and returns the matching donor object, or null if not found.
    public Donor login(String username, String password) {
        String sql = "SELECT * FROM donors WHERE name = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Donor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("blood_group"),
                        rs.getString("location"),
                        rs.getString("contact_info"),
                        rs.getString("password"),
                        rs.getBoolean("available")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Login method for admins: verifies credentials from the admins table and returns an Admin object, or null if not found.
    public Admin loginAdmin(String username, String password) {
        String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Admin(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
