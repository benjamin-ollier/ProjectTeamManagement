package org.example.Repository.Interface;

import org.example.Model.Technology;

public interface TechnologyRepository {
    Technology getTechnologyByName(String name);
    boolean technologieExists(String technologieName);
    Technology addTechnology(Technology technology);
}
