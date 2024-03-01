package org.example.Repository;

import org.example.Model.*;
import org.example.Repository.Interface.ProjectRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class JpaProjectRepository implements ProjectRepository {
    private final SessionFactory sessionFactory;

    public JpaProjectRepository(SessionFactory sessionFactory) {
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

    public Project updateProject(String projectName, Project updatedProjectInfo) {
        Transaction transaction = null;
        Project existingProject = null;

        try (Session session = sessionFactory.openSession()) {
            existingProject = session.createQuery("FROM Project P WHERE P.name = :projectName", Project.class)
                    .setParameter("projectName", projectName)
                    .uniqueResult();

            if (existingProject == null) {
                throw new IllegalArgumentException("Projet non trouvé avec le nom : " + projectName);
            }

            transaction = session.beginTransaction();

            existingProject.setDescription(updatedProjectInfo.getDescription());
            existingProject.setPriority(updatedProjectInfo.getPriority());
            existingProject.setStartDate(updatedProjectInfo.getStartDate());
            existingProject.setEndDate(updatedProjectInfo.getEndDate());
            existingProject.setStatus(updatedProjectInfo.getStatus());
            existingProject.setTeamId(updatedProjectInfo.getTeamId());

            session.update(existingProject);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }

        return existingProject;
    }

    public Project getProjectbyTeamid(Long teamId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Project P where P.teamId.id = :teamId", Project.class)
                    .setParameter("teamId", teamId)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getRequiredSkills(Project project){
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT tech.name FROM ProjectTechnology pt JOIN pt.techId tech WHERE pt.projectName = :project", String.class)
                    .setParameter("project", project)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Project> getUserProjects(User userIdentity) {
        List<Project> userProjects = new ArrayList<>();
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Récupère tous les TeamMembers associés à l'identité de l'utilisateur
            String hqlTeamMembers = "FROM TeamMember WHERE userIdentity.userIdentifiant.name = :userName";
            List<TeamMember> userTeamMembers = session.createQuery(hqlTeamMembers, TeamMember.class)
                    .setParameter("userName", userIdentity.getUserIdentifiant().getName())
                    .getResultList();

            // trouve le projet associé via teamId
            for (TeamMember teamMember : userTeamMembers) {
                Project project = session.createQuery("FROM Project P WHERE P.teamId = :teamId", Project.class)
                        .setParameter("teamId", teamMember.getTeamId())
                        .uniqueResult();

                if (project != null) {
                    userProjects.add(project);
                }
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return userProjects;
    }

}
