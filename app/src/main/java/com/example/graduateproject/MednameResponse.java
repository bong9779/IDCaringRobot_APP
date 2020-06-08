package com.example.graduateproject;

import com.google.gson.annotations.SerializedName;

public class MednameResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("medName")
    private String medName;

    @SerializedName("medTime")
    private String medTime;

    public int getCode() {
        return code;
    }
    public String getMedName() {
        return medName;
    }
    public String getMedTime() {
        return medTime;
    }

    public String getMessage() {
        return message;
    }
}
