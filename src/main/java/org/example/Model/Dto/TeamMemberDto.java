package org.example.Model.Dto;

import java.util.List;

public class TeamMemberDto {

    private Long teamId;
    private List<MemberIdentifier> members;


    public TeamMemberDto() {
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public List<MemberIdentifier> getMembers() {
        return members;
    }

    public void setMembers(List<MemberIdentifier> members) {
        this.members = members;
    }

    // Classe interne pour représenter un identifiant de membre
    public static class MemberIdentifier {
        private String name;
        private String email;

        // Constructeurs, getters et setters

        public MemberIdentifier() {
        }

        public MemberIdentifier(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
