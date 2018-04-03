package com.dk.watercup;

public class VillageModel {
    public String name, taluka, phone, cName, cPhone;
    public int points;

    public VillageModel() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPoints() {
        return points;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcPhone() {
        return cPhone;
    }

    public void setcPhone(String cPhone) {
        this.cPhone = cPhone;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public VillageModel(String name, String taluka, String phone, int points, String cName, String cPhone) {
        this.name = name;
        this.taluka = taluka;
        this.phone = phone;
        this.points = points;
        this.cName = cName;
        this.cPhone = cPhone;
    }
}
