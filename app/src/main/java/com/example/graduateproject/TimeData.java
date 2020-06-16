package com.example.graduateproject;

import com.google.gson.annotations.SerializedName;

public class TimeData {
    @SerializedName("medName")
    String medName;

    @SerializedName("medTime")
    String medTime;

    @SerializedName("patientname")
    String patientname;

    @SerializedName("patient_id")
    String patient_id;


    public TimeData(String medName, String medTime, String patientname, String patient_id) {
        this.medName = medName;
        this.medTime = medTime;
        this.patientname = patientname;
        this.patient_id = patient_id;
    }
}
