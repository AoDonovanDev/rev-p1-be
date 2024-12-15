package com.example.entity;

public class AccInfoDto {
    private boolean success;
    private Account account;

    
    public AccInfoDto(){}

    public AccInfoDto(boolean success, Account account){
        this.success = success;
        this.account = account;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    
}
