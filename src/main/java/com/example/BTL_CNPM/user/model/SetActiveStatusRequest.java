package com.example.BTL_CNPM.user.model;

public class SetActiveStatusRequest {
    private String username;
    private boolean isActive;

    public SetActiveStatusRequest(String username, boolean isActive) {
        this.username = username;
        this.isActive = isActive;
    }

    public SetActiveStatusRequest() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
