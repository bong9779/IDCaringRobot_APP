package com.example.graduateproject;

import com.google.gson.annotations.SerializedName;

public class BoardGetData {
    @SerializedName("patient_id")
    private String patient_id;


    public BoardGetData(String patient_id) {

        this.patient_id=patient_id;
    }
}

