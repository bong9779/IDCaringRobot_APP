package com.example.graduateproject;

import com.google.gson.annotations.SerializedName;

public class TimeGetData {
    @SerializedName("patientname")
    String patientname;

    public TimeGetData(String patientname) {
        this.patientname = patientname;
    }
}
