package com.dk.watercup;

public class NewProjectModel {

    private String type, cost, desc, name;

    public NewProjectModel() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public NewProjectModel(String name, String type, String cost, String desc) {
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.desc = desc;
    }
}
