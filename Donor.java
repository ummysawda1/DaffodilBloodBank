package com.bloodbank;

public class Donor {
    private int id;
    private String name;
    private String bloodGroup;
    private String location;
    private String contactInfo;
    private String password;
    private boolean available;

    // Constructor without ID (for new donors, where ID is auto-generated)
    public Donor(String name, String bloodGroup, String location, String contactInfo, String password, boolean available) {
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.location = location;
        this.contactInfo = contactInfo;
        this.password = password;
        this.available = available;
    }

    // Constructor with ID (for existing donors)
    public Donor(int id, String name, String bloodGroup, String location, String contactInfo, String password, boolean available) {
        this.id = id;
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.location = location;
        this.contactInfo = contactInfo;
        this.password = password;
        this.available = available;
    }

    // Getters and setters for all fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
