package org.example.Repository.Interface;

import org.example.Model.User;
import org.example.Model.Dto.TeamMemberDto;
import java.time.LocalDate;
import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();
    User getUserByName(String name);
    User getUserByEmail(String email);
    boolean deleteUserByNameAndEmail(String name, String email);
    User update(User user);
    User create(User user);
    List<User> getAvailableUsersWithRequiredSkillsAndLevel(List<String> requiredSkills, String requiredLevel, LocalDate startDate, LocalDate endDate);
    User getByIdentifier(TeamMemberDto.MemberIdentifier memberId);
    User findByNameAndEmail(String name, String email);
}
