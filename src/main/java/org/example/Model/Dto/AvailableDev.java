package org.example.Model.Dto;

import java.time.LocalDate;
import java.util.List;

public class AvailableDev {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> skills;
    private String level;

    public AvailableDev(){}

    public AvailableDev(LocalDate startDate, LocalDate endDate, List<String> skills, String level) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.skills = skills;
        this.level = level;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}



