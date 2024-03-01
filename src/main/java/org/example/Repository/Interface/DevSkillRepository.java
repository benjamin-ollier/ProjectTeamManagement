package org.example.Repository.Interface;

import org.example.Model.DevSkill;
import org.example.Model.User;

import java.time.LocalDate;
import java.util.List;

public interface DevSkillRepository {
    public List<DevSkill> getUserSkills(String identity);

    public List<DevSkill> getDevsBySkillAndLevel(String skillName, String level);

    public DevSkill save(DevSkill devSkill);

    public DevSkill getDevSkillIfExists(String name, String email, int techId);

    public DevSkill update(DevSkill devSkill);

    public DevSkill findDevSkillById(Long devSkillId);

    public List<DevSkill> getAvailableDevSkillsWithRequiredSkillsAndLevel(List<String> requiredSkills, String requiredLevel, LocalDate startDate, LocalDate endDate);

}
