package org.example.Modele;

public class Teams {
    private int teamId;
    private int projectId;
    private String status;

    public Teams(int teamId, int projectId, String status) {
        this.teamId = teamId;
        this.projectId = projectId;
        this.status = status;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
