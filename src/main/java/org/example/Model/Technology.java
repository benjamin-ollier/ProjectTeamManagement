package org.example.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "technologie")
public class Technology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int techId;
    @Column(name = "name")
    private String name;

    public Technology(int techId, String name) {
        this.techId = techId;
        this.name = name;
    }

    public Technology() {

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
