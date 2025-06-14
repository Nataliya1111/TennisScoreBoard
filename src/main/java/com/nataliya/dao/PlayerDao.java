package com.nataliya.dao;

import com.nataliya.model.entity.Player;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

@Slf4j
public class PlayerDao extends Dao<Long, Player> {

    private static final String GET_BY_NAME_QUERY = "from Player where name =:name";

    public PlayerDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Player> findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(GET_BY_NAME_QUERY, Player.class)
                .setParameter("name", name).uniqueResultOptional();
    }
}
