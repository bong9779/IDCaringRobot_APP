package com.example.graduateproject;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("userid")
    private String userid;

    @SerializedName("patient_id")
    private String patient_id;

    public int getCode() {
        return code;
    }

    public String getPatient_id() { return patient_id; }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userid;
    }
}
