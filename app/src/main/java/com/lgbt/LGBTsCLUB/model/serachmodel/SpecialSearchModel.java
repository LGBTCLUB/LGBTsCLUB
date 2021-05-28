package com.lgbt.LGBTsCLUB.model.serachmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpecialSearchModel {

    @Expose
    @SerializedName("data")
    private List<SpecialData> data;
    @Expose
    @SerializedName("response")
    private boolean response;

    public List<SpecialData> getData() {
        return data;
    }

    public void setData(List<SpecialData> data) {
        this.data = data;
    }

    public boolean getResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public static class SpecialData {
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

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

        @Expose
        @SerializedName("id")
        private String id;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("sortorder")
        private String sortorder;
        @Expose
        @SerializedName("status")
        private String status;



    }
}
