package org.example.Service;

import org.example.Model.DevSkill;
import org.example.Model.Technology;
import org.example.Model.User;
import org.example.Repository.JpaDevSkillRepository;
import org.example.Repository.JpaProjectRepository;
import org.example.Shared.Util;
import org.example.Repository.Interface.DevSkillRepository;
import org.example.Repository.JpaTechnologyRepository;
import org.example.Repository.JpaUserRepository;
import org.example.Model.Dto.AvailableDev;
import java.util.List;

public class DevSkillService {

    private final JpaDevSkillRepository jpaDevSkillRepository;
    private final JpaTechnologyRepository jpaTechnologyRepository;
    private final JpaUserRepository jpaUserRepository;


    public DevSkillService(JpaDevSkillRepository jpaDevSkillRepository, JpaTechnologyRepository jpaTechnologyRepository, JpaUserRepository jpaUserRepository) {
        this.jpaDevSkillRepository = jpaDevSkillRepository;
        this.jpaTechnologyRepository = jpaTechnologyRepository;
        this.jpaUserRepository = jpaUserRepository;
    }

    public List<DevSkill> getUserSkills(String identity){
        return jpaDevSkillRepository.getUserSkills(identity);
    }

    public DevSkill addOrUpdateSkill(String identity, String technology, int yearsOfExperience){
        User currentUser = getUserByIdentity(identity);
        if(currentUser == null) {
            throw new IllegalArgumentException("User not found for identity: " + identity);
        }

        Technology newTechnology = getOrCreateTechnology(technology);

        DevSkill devSkillExist = getDevSkillIdIfExists(currentUser.getUserIdentifiant().getName(), currentUser.getUserIdentifiant().getEmail(), newTechnology.getTechId());

        if(yearsOfExperience <0) {
            throw new IllegalArgumentException("Année d'experience pas correcte");
        }

        if (devSkillExist != null) {
            determineExperience(devSkillExist, yearsOfExperience);
            return jpaDevSkillRepository.update(devSkillExist);
        } else {
            devSkillExist =createDevSkill(currentUser, newTechnology, yearsOfExperience);
            determineExperience(devSkillExist, yearsOfExperience);
            return jpaDevSkillRepository.save(devSkillExist);
        }
    }

    public User getUserByIdentity(String identity) {
        if (Util.isEmail(identity)) {
            return jpaUserRepository.getUserByEmail(identity);
        } else {
            return jpaUserRepository.getUserByName(identity);
        }
    }

    public Technology getOrCreateTechnology(String technology) {
        Technology newTechnology = new Technology();
        newTechnology.setName(technology);

        if (!jpaTechnologyRepository.technologieExists(technology)) {
            jpaTechnologyRepository.addTechnology(newTechnology);
        } else {
            newTechnology = jpaTechnologyRepository.getTechnologyByName(technology);
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
        return jpaDevSkillRepository.getDevSkillIfExists(name, email, techId);
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
        return jpaDevSkillRepository.getDevsBySkillAndLevel(technology, level);
    }



    public List<DevSkill> getAllAvailableDeveloperSkills(AvailableDev devs) {
        List<DevSkill> availableDevSkills = jpaDevSkillRepository.getAvailableDevSkillsWithRequiredSkillsAndLevel(devs.getSkills(), devs.getLevel(), devs.getStartDate(), devs.getEndDate());
        if (availableDevSkills.isEmpty()) {
            throw new IllegalArgumentException("Aucune compétence trouvée pour cette tranche de date avec les critères spécifiés.");
        }
        return availableDevSkills;
    }



}
