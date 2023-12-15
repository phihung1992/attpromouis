package com.attlib.attpromouis.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApplicationResult {
    @SerializedName("data")
    @Expose
    private List<ApplicationInfo> data = null;

    public List<ApplicationInfo> getData() {
        return data;
    }

    public void setData(List<ApplicationInfo> data) {
        this.data = data;
    }

    public class ApplicationInfo {
        @SerializedName("applicationId")
        @Expose
        private Integer applicationId;
        @SerializedName("key")
        @Expose
        private String key;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("packetName")
        @Expose
        private String packetName;
        @SerializedName("logo")
        @Expose
        private String logo;

        public Integer getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(Integer applicationId) {
            this.applicationId = applicationId;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPacketName() {
            return packetName;
        }

        public void setPacketName(String packetName) {
            this.packetName = packetName;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
}
