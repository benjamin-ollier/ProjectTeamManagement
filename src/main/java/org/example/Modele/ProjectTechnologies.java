package org.example.Modele;

public class ProjectTechnologies {
    private int projectId;
    private int techId;
    private int devsRequired;

    public ProjectTechnologies(int projectId, int techId, int devsRequired) {
        this.projectId = projectId;
        this.techId = techId;
        this.devsRequired = devsRequired;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getTechId() {
        return techId;
    }

    public void setTechId(int techId) {
        this.techId = techId;
    }

    public int getDevsRequired() {
        return devsRequired;
    }

    public void setDevsRequired(int devsRequired) {
        this.devsRequired = devsRequired;
    }
}
