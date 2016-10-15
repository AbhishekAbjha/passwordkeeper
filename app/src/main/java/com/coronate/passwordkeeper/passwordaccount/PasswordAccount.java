package com.coronate.passwordkeeper.passwordaccount;

public class PasswordAccount {

    private Long accountId; //Not Null
    private String accountName; //Not null
    private String username; //Not Null
    private String password; //Not Null
    private String notes;
    private int isDeleted; //Not Null
    private String dateCreated; //Not Null
    private String lastUpdated; //Not Null

    public PasswordAccount(Long accountId, String accountName, String username, String password, String notes, int isDeleted, String dateCreated, String lastUpdated) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.username = username;
        this.password = password;
        this.notes = notes;
        this.isDeleted = isDeleted;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
