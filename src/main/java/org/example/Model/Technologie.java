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

}
