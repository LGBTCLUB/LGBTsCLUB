package com.lgbt.LGBTsCLUB.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EducationDataModel {
    @Expose
    @SerializedName("data")
    private List<EducationData> data;
    @Expose
    @SerializedName("response")
    private boolean response;

    public List<EducationData> getData() {
        return data;
    }

    public void setData(List<EducationData> data) {
        this.data = data;
    }

    public boolean getResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public static class EducationData {
        @Expose
        @SerializedName("sortorder")
        private String sortorder;
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("education")
        private String education;
        @Expose
        @SerializedName("education_id")
        private String educationId;

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

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getEducationId() {
            return educationId;
        }

        public void setEducationId(String educationId) {
            this.educationId = educationId;
        }
    }
}
