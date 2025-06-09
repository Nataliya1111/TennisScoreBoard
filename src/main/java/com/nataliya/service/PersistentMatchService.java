package com.nataliya.service;

import com.nataliya.dao.MatchDao;
import com.nataliya.hibernate.SessionFactoryProvider;
import com.nataliya.hibernate.TransactionManager;
import com.nataliya.model.entity.Match;
import org.hibernate.SessionFactory;


public class PersistentMatchService {

    private static final PersistentMatchService INSTANCE = new PersistentMatchService();
    private final SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
    private final MatchDao matchDao = new MatchDao(sessionFactory);
    private final TransactionManager transactionManager = new TransactionManager();

    private PersistentMatchService(){
    }

    public static PersistentMatchService getInstance(){
        return INSTANCE;
    }

    public void saveFinishedMatch(Match finishedMatch) {
        transactionManager.runInTransaction(() -> matchDao.save(finishedMatch));
    }

}
