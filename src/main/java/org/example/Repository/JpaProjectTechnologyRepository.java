package org.example.Repository;

import org.example.Model.ProjectTechnology;
import org.example.Repository.Interface.ProjectTechnologyRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class JpaProjectTechnologyRepository implements ProjectTechnologyRepository {
    private final SessionFactory sessionFactory;

    public JpaProjectTechnologyRepository(SessionFactory sessionFactory) {
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
