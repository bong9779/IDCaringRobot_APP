package com.example.graduateproject;

import com.google.gson.annotations.SerializedName;

public class JoinData {
    @SerializedName("userName")
    private String userName;

    @SerializedName("userId")
    private String userId;

    @SerializedName("userPwd")
    private String userPwd;

    @SerializedName("patient_id")
    private String patient_id;

    @SerializedName("userEmail")
    private String userEmail;

    @SerializedName("userPhone")
    private String userPhone;

    @SerializedName("userMac")
    private String userMac;

    public JoinData(String userName, String userId, String userPwd, String patient_id, String userEmail, String userPhone, String userMac) {
        this.userId = userId;
        this.userPwd = userPwd;
        this.userName = userName;
        this.patient_id = patient_id;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userMac = userMac;

    }
}
