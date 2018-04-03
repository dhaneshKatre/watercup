package com.dk.watercup;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class VillageHolder extends RecyclerView.ViewHolder {

    private TextView tName,textPhone, textTaluka, textPoints;
    public VillageHolder (final View itemView) {
        super(itemView);
        tName = itemView.findViewById(R.id.tName);
        textPhone = itemView.findViewById(R.id.textPhone);
        textTaluka = itemView.findViewById(R.id.textTaluka);
        textPoints = itemView.findViewById(R.id.textPoints);
    }

    public void setTName(String tName) {
        this.tName.setText(tName);
    }

    public void setTextPhone(String textPhone) {
        this.textPhone.setText(textPhone);
    }

    public void setTextTaluka(String textTaluka) {
        this.textTaluka.setText(textTaluka);
    }

    public void setTextPoints (String textPoints) {this.textPoints.setText(textPoints);}
}
