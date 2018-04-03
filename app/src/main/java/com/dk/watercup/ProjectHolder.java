package com.dk.watercup;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ProjectHolder extends RecyclerView.ViewHolder {

    private TextView textName, textCost, textDesc,textType;

    public ProjectHolder(final View itemView) {
        super(itemView);
        textName = itemView.findViewById(R.id.textName);
        textCost = itemView.findViewById(R.id.textCost);
        textDesc = itemView.findViewById(R.id.textDesc);
        textType = itemView.findViewById(R.id.textType);
    }

    public void setTextName(String textName) {
        this.textName.setText(textName);
    }

    public void setTextCost(String textCost) {
        this.textCost.setText(textCost);
    }

    public void setTextDesc(String textDesc) {
        this.textDesc.setText(textDesc);
    }

    public void setTextType(String textType) { this.textType.setText(textType);}
}
