package org.example.Repository;

import org.example.Model.Dto.TeamMemberDto;
import org.example.Model.User;
import org.example.Repository.Interface.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.time.LocalDate;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JpaUserRepository implements UserRepository {
    private final SessionFactory sessionFactory;

    public JpaUserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public User getUserByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User U where U.userIdentifiant.name = :name", User.class)
                    .setParameter("name", name)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User U where U.userIdentifiant.email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteUserByNameAndEmail(String name, String email) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Query query = session.createQuery("DELETE FROM User U WHERE U.userIdentifiant.name = :name or U.userIdentifiant.email = :email");
            query.setParameter("name", name);
            query.setParameter("email", email);
            int result = query.executeUpdate();

            transaction.commit();
            return result > 0;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public User update(User user){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User create(User user){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> getAvailableUsersWithRequiredSkillsAndLevel(List<String> requiredSkills, String requiredLevel, LocalDate startDate, LocalDate endDate) {
        Transaction transaction = null;
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            String queryString = "SELECT user FROM User user " +
                    "WHERE user.userIdentifiant.name IN (" +
                    "SELECT devSkill.userIdentity.userIdentifiant.name FROM DevSkill devSkill " +
                    "WHERE devSkill.techId.name IN (:requiredSkills) " +
                    "AND devSkill.level = :requiredLevel)" +
                    "AND user.userIdentifiant.name NOT IN (" +
                    "SELECT teamMember.userIdentity.userIdentifiant.name FROM TeamMember teamMember " +
                    "JOIN Project project ON project.teamId.id = teamMember.teamId.id " +
                    "WHERE (project.startDate <= :endDate AND project.endDate >= :startDate))";

            Query<User> query = session.createQuery(queryString, User.class);
            query.setParameterList("requiredSkills", requiredSkills);
            query.setParameter("requiredLevel", requiredLevel);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            users = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return users;
    }



    public User getByIdentifier(TeamMemberDto.MemberIdentifier memberId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User U where U.userIdentifiant = :memberId", User.class)
                    .setParameter("memberId", memberId)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public User findByNameAndEmail(String name, String email) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User U WHERE U.userIdentifiant.name = :name OR U.userIdentifiant.email = :email", User.class)
                    .setParameter("name", name)
                    .setParameter("email", email)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

