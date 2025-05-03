package com.nataliya.dao;

import com.nataliya.model.entity.Player;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PlayerDao implements Dao<Long, Player>{

    private static final String GET_BY_NAME_QUERY = "from Player where name =:name";
    private final SessionFactory sessionFactory;

    @Override
    public Player save(Player player) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(player);
        return player;
    }

    public Optional<Player> findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(GET_BY_NAME_QUERY, Player.class)
                .setParameter("name", name).uniqueResultOptional();
    }
}
