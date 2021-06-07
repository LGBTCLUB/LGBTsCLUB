package com.lgbt.LGBTsCLUB.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class ForgotPasswordModel {

    @Expose
    @SerializedName("data")
    private String data;
    @Expose
    @SerializedName("response")
    private boolean response;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean getResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }
}
