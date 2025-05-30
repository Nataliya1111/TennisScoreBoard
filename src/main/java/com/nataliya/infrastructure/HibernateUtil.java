package com.nataliya.infrastructure;

import com.nataliya.model.entity.Match;
import com.nataliya.model.entity.Player;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private HibernateUtil(){
    }

    public static SessionFactory getSessionFactory(){
        return SESSION_FACTORY;
    }

    public static void shutdown(){
        getSessionFactory().close();
    }

    private static SessionFactory buildSessionFactory(){
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Player.class);
        configuration.addAnnotatedClass(Match.class);
        configuration.configure();

        return configuration.buildSessionFactory();
    }
}

