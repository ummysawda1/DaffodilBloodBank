package com.bloodbank;

public class Admin {
    private int id;
    private String username;
    private String password;
    private String email;

    // Constructor
    public Admin(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    // Getter and Setter for id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    // Getter and Setter for username
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    // Getter and Setter for password
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    // Getter and Setter for email
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    // Optional: Override toString() method for debugging or logging
    @Override
    public String toString() {
        return "Admin{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}
