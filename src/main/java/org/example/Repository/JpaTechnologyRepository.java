package org.example.Repository;

import jakarta.persistence.PersistenceException;
import org.example.Model.Technology;
import org.example.Repository.Interface.TechnologyRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class JpaTechnologyRepository implements TechnologyRepository {
    private final SessionFactory sessionFactory;

    public JpaTechnologyRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Technology getTechnologyByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Technology> query = session.createQuery("FROM Technology t WHERE t.name = :name", Technology.class);
            query.setParameter("name", name);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean technologieExists(String technologieName) {
        try (Session session = sessionFactory.openSession()) {

            long count = session.createQuery("SELECT COUNT(t) FROM Technology t WHERE t.name = :technologieName", Long.class)
                    .setParameter("technologieName", technologieName)
                    .getSingleResult();

            return count > 0;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Technology addTechnology(Technology technology) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                session.persist(technology);
                transaction.commit();
                return technology;
            } catch (PersistenceException e) {
                transaction.rollback();
                e.printStackTrace();
                return null;
            }
        }
    }

}
