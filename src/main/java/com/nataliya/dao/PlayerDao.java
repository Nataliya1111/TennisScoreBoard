package com.nataliya.dao;

import com.nataliya.model.entity.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class PlayerDao implements Dao<Long, Player>{

    private static final String GET_BY_NAME_QUERY = "from Player where name =:name";
    private final SessionFactory sessionFactory;

    @Override
    public Player save(Player player) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(player);
        log.info("Player with name {} has been saved", player.getName());
        return player;
    }

    public Optional<Player> findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(GET_BY_NAME_QUERY, Player.class)
                .setParameter("name", name).uniqueResultOptional();
    }
}
