package org.example.Model.Dto;

import org.example.Model.DevSkill;

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

    public static class MemberIdentifier {
        public Long devSkillId;
        private String name;
        private String email;

        // Constructeurs, getters et setters

        public MemberIdentifier() {
        }

        public MemberIdentifier(String name, String email,Long devSkillId) {
            this.name = name;
            this.email = email;
            this.devSkillId = devSkillId;
        }

        public Long getDevSkillId() {
            return devSkillId;
        }

        public void setDevSkillId(Long devSkillId) {
            this.devSkillId = devSkillId;
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
