package com.nataliya.dao;

import com.nataliya.model.Player;
import com.nataliya.util.HibernateUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@RequiredArgsConstructor
public class PlayerDao implements Dao<Long, Player>{

    private final SessionFactory sessionFactory;

    @Override
    public Player save(Player player) {

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.persist(player);
            session.getTransaction().commit();
            return player;
        }
    }
}
