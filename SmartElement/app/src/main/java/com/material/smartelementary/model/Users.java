package com.material.smartelementary.model;

public class Users {
    public String fullname, dob, position, nip, npsn, phone, status, key;

    public Users() {
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public void setNpsn(String npsn) { this.npsn = npsn;}

    public void setPhone(String phone) { this.phone = phone;}

    public void setStatus(String status) { this.status = status;}

    public void setKeyUser(String key) { this.key = key;}

    public String getFullname() {
        return fullname;
    }

    public String getDob() {
        return dob;
    }

    public String getPosition() {
        return position;
    }

    public String getNip() {
        return nip;
    }

    public String getNpsn() { return npsn;}

    public String getPhone() { return phone;}

    public String getStatus() { return status;}

    public String getKeyUser() { return key;}
}
