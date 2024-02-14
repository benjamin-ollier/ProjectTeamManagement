package org.example.Repository;

import jakarta.persistence.PersistenceException;
import org.example.Model.Project;
import org.example.Model.ProjectTechnology;
import org.example.Model.Technology;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProjectTechnologyRepository {
    private final SessionFactory sessionFactory;

    public ProjectTechnologyRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public List<ProjectTechnology> getTechnologyByProjectName(String projectName) {
            try (Session session = sessionFactory.openSession()) {
                return session.createQuery("FROM ProjectTechnology P where P.projectName.name = :projectName", ProjectTechnology.class)
                .setParameter("projectName", projectName)
                .list();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
        }
    }
}
