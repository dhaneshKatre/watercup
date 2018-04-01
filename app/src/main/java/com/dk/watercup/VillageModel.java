package com.dk.watercup;

public class VillageModel {
    public String name, taluka, phone;

    public VillageModel() {
    }

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

    public VillageModel(String name, String taluka, String phone) {
        this.name = name;
        this.taluka = taluka;
        this.phone = phone;
    }
}
