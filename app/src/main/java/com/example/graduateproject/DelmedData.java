package com.example.graduateproject;

import com.google.gson.annotations.SerializedName;

public class DelmedData {

    @SerializedName("pos")
    private int pos;

    @SerializedName("patientname")
    private String patientname;


    public DelmedData(int pos, String patientname) {

        this.pos = pos;
        this.patientname=patientname;
    }
}
