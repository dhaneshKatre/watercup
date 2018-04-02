package com.dk.watercup;

public class NewProjectModel {

    private String type, cost, desc;

    public NewProjectModel() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public NewProjectModel(String type, String cost, String desc) {

        this.type = type;
        this.cost = cost;
        this.desc = desc;
    }
}
