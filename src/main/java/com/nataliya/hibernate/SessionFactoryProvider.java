package com.nataliya.hibernate;

import com.nataliya.model.entity.Match;
import com.nataliya.model.entity.Player;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionFactoryProvider {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Player.class);
        configuration.addAnnotatedClass(Match.class);
        configuration.configure();

        return configuration.buildSessionFactory();
    }
}

