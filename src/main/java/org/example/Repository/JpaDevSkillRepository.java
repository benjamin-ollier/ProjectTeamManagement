package org.example.Repository;

import org.example.Model.DevSkill;
import org.example.Model.User;
import org.example.Repository.Interface.DevSkillRepository;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JpaDevSkillRepository implements DevSkillRepository {
    private final SessionFactory sessionFactory;

    public JpaDevSkillRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



    public List<DevSkill> getUserSkills(String identity) {
        try (Session session = sessionFactory.openSession()) {
            Query<DevSkill> query = session.createQuery("FROM DevSkill ds WHERE ds.userIdentity.userIdentifiant.name = :identity OR ds.userIdentity.userIdentifiant.email = :identity", DevSkill.class);
            query.setParameter("identity", identity);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<DevSkill> getDevsBySkillAndLevel(String skillName, String level) {
        try (Session session = sessionFactory.openSession()) {
            Query<DevSkill> query = session.createQuery(
                    "FROM DevSkill ds WHERE ds.techId.name = :skillName AND ds.level = :level", DevSkill.class);
            query.setParameter("skillName", skillName);
            query.setParameter("level", level);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public DevSkill save(DevSkill devSkill) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(devSkill);
            transaction.commit();
            return devSkill;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public DevSkill getDevSkillIfExists(String name, String email, int techId) {
        try (Session session = sessionFactory.openSession()) {
            Query<DevSkill> query = session.createQuery(
                    "FROM DevSkill ds WHERE ds.userIdentity.userIdentifiant.email = :email AND ds.userIdentity.userIdentifiant.name = :name AND ds.techId.techId = :techId", DevSkill.class);
            query.setParameter("name", name);
            query.setParameter("email", email);
            query.setParameter("techId", techId);

            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public DevSkill update(DevSkill devSkill) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            DevSkill updatedDevSkill = (DevSkill) session.merge(devSkill);
            session.getTransaction().commit();
            return updatedDevSkill;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<DevSkill> getAvailableDevSkillsWithRequiredSkillsAndLevel(List<String> requiredSkills, String requiredLevel, LocalDate startDate, LocalDate endDate) {
        Transaction transaction = null;
        List<DevSkill> devSkills = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            String queryString = "SELECT devSkill FROM DevSkill devSkill " +
                    "JOIN FETCH devSkill.userIdentity user " +
                    "LEFT JOIN TeamMember teamMember ON user = teamMember.userIdentity " +
                    "LEFT JOIN Project project ON teamMember.teamId = project.teamId " +
                    "WHERE devSkill.techId.name IN (:requiredSkills) " +
                    "AND devSkill.level = :requiredLevel " +
                    "AND (project.startDate IS NULL OR project.startDate > :endDate OR project.endDate < :startDate) " +
                    "GROUP BY devSkill";

            Query<DevSkill> query = session.createQuery(queryString, DevSkill.class);
            query.setParameterList("requiredSkills", requiredSkills);
            query.setParameter("requiredLevel", requiredLevel);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            devSkills = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return devSkills;
    }

    public DevSkill findDevSkillById(Long devSkillId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(DevSkill.class, devSkillId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
