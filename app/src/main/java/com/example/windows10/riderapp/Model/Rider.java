package com.example.windows10.riderapp.Model;

public class Rider {

    private String name;
    private String gender;
    private String contact;
    private String city;
    private String email;
    private String DOB;
    private String password;
    private String guardianName;
    private String guardianWho;
    private String guardianContact;
    private String status;

    public Rider(String name, String gender, String contact, String city, String email, String DOB, String password, String guardianName, String guardianWho, String guardianContact, String status) {
        this.name = name;
        this.gender = gender;
        this.contact = contact;
        this.city = city;
        this.email = email;
        this.DOB = DOB;
        this.password = password;
        this.guardianName = guardianName;
        this.guardianWho = guardianWho;
        this.guardianContact = guardianContact;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getGuardianWho() {
        return guardianWho;
    }

    public void setGuardianWho(String guardianWho) {
        this.guardianWho = guardianWho;
    }

    public String getGuardianContact() {
        return guardianContact;
    }

    public void setGuardianContact(String guardianContact) {
        this.guardianContact = guardianContact;
    }
}
