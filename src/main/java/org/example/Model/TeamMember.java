package org.example.Model;

public class TeamMember {
    private int teamId;
    private String userName;
    private String userEmail;
    private String roleInTeam;

    public TeamMember(int teamId, String userEmail, String roleInTeam) {
        this.teamId = teamId;
        this.userEmail = userEmail;
        this.roleInTeam = roleInTeam;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserId(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getRoleInTeam() {
        return roleInTeam;
    }

    public void setRoleInTeam(String roleInTeam) {
        this.roleInTeam = roleInTeam;
    }
}
