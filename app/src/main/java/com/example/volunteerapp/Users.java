package com.example.volunteerapp;

public class Users {
    private String name, surname, middlename, phone, gender, email, city, dob, pass;
    public Users() {}
    public Users(String name, String surname, String middlename, String phone, String gender, String email, String city, String dob, String pass) {
        this.name = name;
        this.surname = surname;
        this.middlename = middlename;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
        this.city = city;
        this.dob = dob;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPass(String string) {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
