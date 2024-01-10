package org.example.Repository;

import org.example.Model.DevSkill;
import org.example.Model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.Collections;
import java.util.List;
public class TechnologyRepository {
    private final SessionFactory sessionFactory;

    public TechnologyRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


}
