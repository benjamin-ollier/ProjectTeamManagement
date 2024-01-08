package org.example.Modele;

import java.util.Date;

public class DevSkills {
    private String devName;
    private String devEmail;
    private int techId;
    private int years_of_experience;

    public DevSkills(String devName, String devEmail, int techId, int years_of_experience) {
        this.devName = devName;
        this.devEmail = devEmail;
        this.techId = techId;
        this.years_of_experience = years_of_experience;
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

    public int getTechId() {
        return techId;
    }

    public void setTechId(int techId) {
        this.techId = techId;
    }

    public int getYears_of_experience() {
        return years_of_experience;
    }

    public void setYears_of_experience(int years_of_experience) {
        this.years_of_experience = years_of_experience;
    }
}
