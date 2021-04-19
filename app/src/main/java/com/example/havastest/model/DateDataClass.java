package com.example.havastest.model;

import com.google.gson.annotations.SerializedName;

public class DateDataClass {
    @SerializedName("datetime")
    private String date;

    public String getDate() {
        return date;
    }
}
