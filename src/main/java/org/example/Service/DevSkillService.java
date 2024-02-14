package org.example.Service;

import org.example.Model.DevSkill;
import org.example.Model.Technology;
import org.example.Model.User;
import org.example.Shared.Util;
import org.example.Repository.Interface.DevSkillRepository;
import org.example.Repository.TechnologyRepository;
import org.example.Repository.UserRepository;
import java.util.List;

public class DevSkillService {

    private final DevSkillRepository devSkillRepository;
    private final TechnologyRepository technologyRepository;
    private final UserRepository userRepository;


    public DevSkillService(DevSkillRepository devSkillRepository, TechnologyRepository technologyRepository, UserRepository userRepository) {
        this.devSkillRepository = devSkillRepository;
        this.technologyRepository = technologyRepository;
        this.userRepository = userRepository;
    }

    public List<DevSkill> getUserSkills(String identity){
        return devSkillRepository.getUserSkills(identity);
    }

    public DevSkill addOrUpdateSkill(String identity, String technology, int yearsOfExperience){
        User currentUser = getUserByIdentity(identity);
        if(currentUser == null) {
            throw new IllegalArgumentException("User not found for identity: " + identity);
        }

        Technology newTechnology = getOrCreateTechnology(technology);

        DevSkill devSkillExist = getDevSkillIdIfExists(currentUser.getUserIdentifiant().getName(), currentUser.getUserIdentifiant().getEmail(), newTechnology.getTechId());

        if (devSkillExist != null) {
            determineExperience(devSkillExist, yearsOfExperience);
            return devSkillRepository.update(devSkillExist);
        } else {
            devSkillExist =createDevSkill(currentUser, newTechnology, yearsOfExperience);
            determineExperience(devSkillExist, yearsOfExperience);
            return devSkillRepository.save(devSkillExist);
        }
    }

    private User getUserByIdentity(String identity) {
        if (Util.isEmail(identity)) {
            return userRepository.getUserByEmail(identity);
        } else {
            return userRepository.getUserByName(identity);
        }
    }

    private Technology getOrCreateTechnology(String technology) {
        Technology newTechnology = new Technology();
        newTechnology.setName(technology);

        if (!technologyRepository.technologieExists(technology)) {
            technologyRepository.addTechnology(newTechnology);
        } else {
            newTechnology = technologyRepository.getTechnologyByName(technology);
        }

        return newTechnology;
    }

    private DevSkill createDevSkill(User currentUser, Technology newTechnology, int yearsOfExperience) {
        DevSkill newDevSkill = new DevSkill();
        newDevSkill.setUserIdentity(currentUser);
        newDevSkill.setTechId(newTechnology);
        newDevSkill.setYearsOfExperience(yearsOfExperience);
        return newDevSkill;
    }

    private DevSkill getDevSkillIdIfExists(String name, String email, int techId) {
        return devSkillRepository.getDevSkillIfExists(name, email, techId);
    }

    private void determineExperience(DevSkill newDevSkill, int yearsOfExperience) {
        newDevSkill.setYearsOfExperience(yearsOfExperience);
        if (yearsOfExperience >= 0 && yearsOfExperience <= 3) {
            newDevSkill.setLevel(String.valueOf(Util.ExperienceCategory.JUNIOR));
        } else if (yearsOfExperience > 3 && yearsOfExperience <= 5) {
            newDevSkill.setLevel(String.valueOf(Util.ExperienceCategory.EXPERIENCED));
        } else if (yearsOfExperience > 5) {
            newDevSkill.setLevel(String.valueOf(Util.ExperienceCategory.EXPERT));
        }
    }


    public List<DevSkill> getUserByTechnoAndLevel(String level, String technology) {
        return devSkillRepository.getDevsBySkillAndLevel(technology, level);
    }




}
