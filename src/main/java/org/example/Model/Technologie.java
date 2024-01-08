package org.example.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "technologie")
public class Technologie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int techId;
    @Column(name = "name")
    private String name;

    public Technologie(int techId, String name) {
        this.techId = techId;
        this.name = name;
    }

    public int getTechId() {
        return techId;
    }

    public void setTechId(int techId) {
        this.techId = techId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
