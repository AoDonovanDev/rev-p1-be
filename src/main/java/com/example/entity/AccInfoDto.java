package com.example.entity;

public class AccInfoDto {
    private boolean success;
    private String accJson;

    public AccInfoDto(boolean success, String accJson){
        this.success = success;
        this.accJson = accJson;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getAccJson() {
        return accJson;
    }

    public void setAccJson(String accJson) {
        this.accJson = accJson;
    }
}
