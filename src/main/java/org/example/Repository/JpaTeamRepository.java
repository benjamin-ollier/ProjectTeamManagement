package org.example.Repository;

import org.example.Model.Team;
import org.example.Model.TeamMember;
import org.example.Model.User;
import org.example.Repository.Interface.TeamRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;

public class JpaTeamRepository implements TeamRepository{
    private final SessionFactory sessionFactory;

    public JpaTeamRepository(SessionFactory sessionFactory) {
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

    public boolean deleteTeamMember(TeamMember teamMember) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            TeamMember existingTeamMember = session.get(TeamMember.class, teamMember.getId());
            if (existingTeamMember != null) {
                session.delete(existingTeamMember);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
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

    public List<User> getUsersByTeamId(Long teamId) {
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

    public List<TeamMember> getAllTeamMembers(Long idTeam) {
        try (Session session = sessionFactory.openSession()) {
            Query<TeamMember> query = session.createQuery("FROM TeamMember tm WHERE tm.teamId.id = :idTeam", TeamMember.class);
            query.setParameter("idTeam", idTeam);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public TeamMember findTeamMemberByNameOrEmail(String nameOrEmail) {
        try (Session session = sessionFactory.openSession()) {
            String queryString = "SELECT tm FROM TeamMember tm JOIN tm.userIdentity u WHERE u.userIdentifiant.name = :nameOrEmail OR u.userIdentifiant.email = :nameOrEmail";
            Query<TeamMember> query = session.createQuery(queryString, TeamMember.class);
            query.setParameter("nameOrEmail", nameOrEmail);
            List<TeamMember> results = query.list();

            return results.isEmpty() ? null : results.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
