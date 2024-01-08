package org.example.Model;

import java.time.LocalDate;

public class DevDetail {
    private String devName;
    private String devEmail;
    private String level;
    private LocalDate lastTeamChangeDate;

    public DevDetail(String devName, String devEmail, String level, LocalDate lastTeamChangeDate) {
        this.devName = devName;
        this.devEmail = devEmail;
        this.level = level;
        this.lastTeamChangeDate = lastTeamChangeDate;
    }


    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevEmail() {
        return devEmail;
    }

    public void setDevEmail(String devEmail) {
        this.devEmail = devEmail;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public LocalDate getLastTeamChangeDate() {
        return lastTeamChangeDate;
    }

    public void setLastTeamChangeDate(LocalDate lastTeamChangeDate) {
        this.lastTeamChangeDate = lastTeamChangeDate;
    }
}
