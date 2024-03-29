package com.nihar.final_group4_groupproject.model;

public class UserModel {
    private String userId;
    private String mobilePrimary;
    private String firstname;
    private String middlename;
    private String lastname;
    private String mobileSecondary;
    private String email;
    private String dob;
    private String anniversary;
    private String bloodgroup;
    private String gender;
    private String country;
    private String state;
    private String city;
    private String zipcode;
    private String residentialAddress;
    private String position;
    private String category;

    public UserModel(String userId, String mobilePrimary, String firstname, String middlename, String lastname, String mobileSecondary, String email, String dob, String anniversary, String bloodgroup, String gender, String country, String state, String city, String zipcode, String residentialAddress, String position, String category) {
        this.userId = userId;
        this.mobilePrimary = mobilePrimary;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.mobileSecondary = mobileSecondary;
        this.email = email;
        this.dob = dob;
        this.anniversary = anniversary;
        this.bloodgroup = bloodgroup;
        this.gender = gender;
        this.country = country;
        this.state = state;
        this.city = city;
        this.zipcode = zipcode;
        this.residentialAddress = residentialAddress;
        this.position = position;
        this.category = category;
    }

    public String getUserId() {
        return userId;
    }

    public String getMobilePrimary() {
        return mobilePrimary;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMobileSecondary() {
        return mobileSecondary;
    }

    public String getEmail() {
        return email;
    }

    public String getDOB() {
        return dob;
    }

    public String getAnniversary() {
        return anniversary;
    }

    public String getBloodGroup() {
        return bloodgroup;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public String getPosition() {
        return position;
    }

    public String getCategory() {
        return category;
    }

}