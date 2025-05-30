package com.nataliya.service;

import com.nataliya.dao.MatchDao;
import com.nataliya.exception.DatabaseException;
import com.nataliya.infrastructure.HibernateUtil;
import com.nataliya.model.OngoingMatch;
import com.nataliya.model.entity.Match;
import com.nataliya.model.entity.Player;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class FinishedMatchPersistenceService {

    private static final FinishedMatchPersistenceService INSTANCE = new FinishedMatchPersistenceService();
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final MatchDao matchDao = new MatchDao(sessionFactory);

    private FinishedMatchPersistenceService(){
    }

    public static FinishedMatchPersistenceService getInstance(){
        return INSTANCE;
    }

    public void saveFinishedMatch(OngoingMatch ongoingMatch, Player winner) {
        Match finishedMatch = Match.builder().player1(ongoingMatch.getPlayer1())
                .player2(ongoingMatch.getPlayer2()).winner(winner).build();
        Session session = null;
        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            matchDao.save(finishedMatch);
            session.getTransaction().commit();
        }
        catch (HibernateException e){
            if(session != null &&session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw new DatabaseException("Execute operation error", e);
        }
    }

}
