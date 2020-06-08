package com.example.graduateproject;

import com.google.gson.annotations.SerializedName;

public class TimeData {
    @SerializedName("medName")
    String medName;

    @SerializedName("medTime")
    String medTime;

    @SerializedName("patientname")
    String patientname;

    public TimeData(String medName, String medTime, String patientname) {
        this.medName = medName;
        this.medTime = medTime;
        this.patientname = patientname;
    }
}
