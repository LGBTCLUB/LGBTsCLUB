package com.lgbt.LGBTsCLUB.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateDataModel {


    @Expose
    @SerializedName("data")
    private List<DataEntity> data;
    @Expose
    @SerializedName("response")
    private boolean response;

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public boolean getResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public static class DataEntity {
        @Expose
        @SerializedName("sortorder")
        private String sortorder;
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("country_id")
        private String countryId;
        @Expose
        @SerializedName("code")
        private String code;
        @Expose
        @SerializedName("state")
        private String state;
        @Expose
        @SerializedName("state_id")
        private String stateId;

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

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStateId() {
            return stateId;
        }

        public void setStateId(String stateId) {
            this.stateId = stateId;
        }
    }
}
