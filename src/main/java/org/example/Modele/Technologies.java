package org.example.Modele;

public class Technologies {
    private int tech_id;
    private String name;

    public Technologies(int tech_id, String name) {
        this.tech_id = tech_id;
        this.name = name;
    }

    public int getTech_id() {
        return tech_id;
    }

    public void setTech_id(int tech_id) {
        this.tech_id = tech_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
