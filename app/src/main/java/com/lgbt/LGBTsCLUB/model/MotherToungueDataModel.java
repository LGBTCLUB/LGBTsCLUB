package com.lgbt.LGBTsCLUB.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MotherToungueDataModel {
    @Expose
    @SerializedName("data")
    private List<MotherToungueData> data;
    @Expose
    @SerializedName("response")
    private boolean response;

    public List<MotherToungueData> getData() {
        return data;
    }

    public void setData(List<MotherToungueData> data) {
        this.data = data;
    }

    public boolean getResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public static class MotherToungueData {
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("sortorder")
        private String sortorder;
        @Expose
        @SerializedName("mother_tongue")
        private String motherTongue;
        @Expose
        @SerializedName("mother_tongue_id")
        private String motherTongueId;

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

        public String getMotherTongue() {
            return motherTongue;
        }

        public void setMotherTongue(String motherTongue) {
            this.motherTongue = motherTongue;
        }

        public String getMotherTongueId() {
            return motherTongueId;
        }

        public void setMotherTongueId(String motherTongueId) {
            this.motherTongueId = motherTongueId;
        }
    }
}
