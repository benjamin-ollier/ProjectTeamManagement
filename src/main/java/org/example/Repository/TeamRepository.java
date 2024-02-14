package org.example.Repository;

import org.example.Model.Team;
import org.example.Model.TeamMember;
import org.example.Model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;

public class TeamRepository {
    private final SessionFactory sessionFactory;

    public TeamRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



    public Team findTeamById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Team> query = session.createQuery("FROM Team t WHERE t.id = :id", Team.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public TeamMember addTeamMember(TeamMember teamMember) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(teamMember);
            transaction.commit();
            return teamMember;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    public Team saveTeam(Team team) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(team);
            transaction.commit();
            return team;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    public List<Team> getAllTeams() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Team ", Team.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<User> getMembersByTeamId(Long teamId) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                    "SELECT tm.userIdentity FROM TeamMember tm WHERE tm.teamId.id = :teamId", User.class);
            query.setParameter("teamId", teamId);

            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
