package org.example.Repository;

import org.example.Model.DevSkill;
import org.example.Model.Project;
import org.example.Model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;

public class ProjectRepository {
    private final SessionFactory sessionFactory;

    public ProjectRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public Project create(Project projectInformation) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(projectInformation);
            transaction.commit();
            return projectInformation;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Project> getProjectInProgress() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Project P where P.status = 'inProgress'", Project.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Project> getProjectFinished() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Project P where P.status = 'finished'", Project.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Project> getProjectPlanned() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Project P where P.status = 'planned'", Project.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Project getProjectByName(String projectName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Project P where P.name = :projectName", Project.class)
                    .setParameter("projectName", projectName)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean existsProjectByName(String projectName) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            String hql = "SELECT count(p.id) FROM Project p WHERE p.name = :projectName";
            Query query = session.createQuery(hql);
            query.setParameter("projectName", projectName);

            Long count = (Long) query.uniqueResult();

            transaction.commit();

            return count != null && count > 0;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

}
