package com.lgbt.LGBTsCLUB.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OccupationDataModel {

    @Expose
    @SerializedName("data")
    private List<OccupationData> data;
    @Expose
    @SerializedName("response")
    private boolean response;

    public List<OccupationData> getData() {
        return data;
    }

    public void setData(List<OccupationData> data) {
        this.data = data;
    }

    public boolean getResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public static class OccupationData {
        @Expose
        @SerializedName("sortorder")
        private String sortorder;
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("occupation")
        private String occupation;
        @Expose
        @SerializedName("occupation_id")
        private String occupationId;

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

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public String getOccupationId() {
            return occupationId;
        }

        public void setOccupationId(String occupationId) {
            this.occupationId = occupationId;
        }
    }
}
