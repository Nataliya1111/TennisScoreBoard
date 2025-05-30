package com.nataliya.dao;

import com.nataliya.model.entity.Match;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@RequiredArgsConstructor
@Slf4j
public class MatchDao implements Dao<Long, Match>{

    private final SessionFactory sessionFactory;

    @Override
    public Match save(Match match) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(match);
        log.info("Match between players {} and {} has been saved", match.getPlayer1().getName(), match.getPlayer2().getName());
        return match;
    }
}
