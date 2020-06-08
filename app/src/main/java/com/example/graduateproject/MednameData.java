package com.example.graduateproject;

import com.google.gson.annotations.SerializedName;

public class MednameData {
    @SerializedName("medName")
    String medName;

    @SerializedName("medTime")
    String medTime;

    @SerializedName("patientName")
    String patientName;

    public MednameData(String medName, String medTime, String patientName) {
        this.medName = medName;
        this.medTime = medTime;
        this.patientName = patientName;
    }
}
