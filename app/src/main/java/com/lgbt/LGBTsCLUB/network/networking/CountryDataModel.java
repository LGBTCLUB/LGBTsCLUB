package com.lgbt.LGBTsCLUB.network.networking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class CountryDataModel {


    @Expose
    @SerializedName("data")
    private List<DataBean> data;
    @Expose
    @SerializedName("response")
    private boolean response;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public boolean getResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public static class DataBean {
        @Expose
        @SerializedName("sortorder")
        private String sortorder;
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("code")
        private String code;
        @Expose
        @SerializedName("country")
        private String country;
        @Expose
        @SerializedName("country_id")
        private String countryId;

        public String getSortorder() {
            return sortorder;
        }

        public void setSortorder(String sortorder) {
            this.sortorder = sortorder;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }
    }
}