package org.example.Repository.Interface;

import org.example.Model.ProjectTechnology;
import java.util.List;

public interface ProjectTechnologyRepository {
    List<ProjectTechnology> getTechnologyByProjectName(String projectName);
}

