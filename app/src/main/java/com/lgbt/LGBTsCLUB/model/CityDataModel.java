package com.lgbt.LGBTsCLUB.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityDataModel {

    @Expose
    @SerializedName("data")
    private List<DataCity> data;
    @Expose
    @SerializedName("response")
    private boolean response;

    public List<DataCity> getData() {
        return data;
    }

    public void setData(List<DataCity> data) {
        this.data = data;
    }

    public boolean getResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }



    public static class DataCity {
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("sortorder")
        private String sortorder;
        @Expose
        @SerializedName("country")
        private String country;
        @Expose
        @SerializedName("state")
        private String state;
        @Expose
        @SerializedName("city")
        private String city;
        @Expose
        @SerializedName("id")
        private String id;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSortorder() {
            return sortorder;
        }

        public void setSortorder(String sortorder) {
            this.sortorder = sortorder;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
