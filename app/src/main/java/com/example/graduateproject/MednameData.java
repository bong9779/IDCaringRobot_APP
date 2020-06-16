package com.example.graduateproject;

import com.google.gson.annotations.SerializedName;

public class MednameData {
    @SerializedName("medName")
    String medName;

    @SerializedName("medTime")
    String medTime;

    @SerializedName("patientName")
    String patientName;

    @SerializedName("patient_id")
    String patient_id;

    public MednameData(String medName, String medTime, String patientName, String patient_id) {
        this.medName = medName;
        this.medTime = medTime;
        this.patientName = patientName;
        this.patient_id = patient_id;
    }
}
