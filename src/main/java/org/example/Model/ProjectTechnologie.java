package org.example.Model;

import jakarta.persistence.*;

public class ProjectTechnologie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "projectId", referencedColumnName = "projectId")
    private Project projectId;
    @ManyToOne
    @JoinColumn(name = "techId", referencedColumnName = "techId")
    private Technologie techId;


}
