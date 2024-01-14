package org.example.Repository;

import org.example.Model.DevSkill;
import org.hibernate.SessionFactory;
import org.example.Model.DevSkill;
import org.example.Model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.Collections;
import java.util.List;
public class DevSkillRepository {
    private final SessionFactory sessionFactory;

    public DevSkillRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



    public List<DevSkill> getUserSkills(String identity) {
        try (Session session = sessionFactory.openSession()) {
            Query<DevSkill> query = session.createQuery("FROM DevSkill ds WHERE ds.userEmail.name = :identity OR ds.userEmail.email = :identity", DevSkill.class);
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
                    "FROM DevSkill ds WHERE ds.userIdentity.userId.email = :email AND ds.userIdentity.userId.name = :name AND ds.techId.techId = :techId", DevSkill.class);
            query.setParameter("name", name);
            query.setParameter("email", email);
            query.setParameter("techId", techId);

            return query.uniqueResult(); // Retourne null si aucun résultat n'est trouvé
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
}
