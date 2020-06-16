package com.example.graduateproject;

import com.google.gson.annotations.SerializedName;

public class BoardGetResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("contents")
    private String contents;

    public int getCode() {
        return code;
    }

    public String getContents() {
        return contents;
    }

    public String getMessage() {
        return message;
    }
}
