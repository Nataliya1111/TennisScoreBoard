package com.nataliya.service;

import com.nataliya.dao.MatchDao;
import com.nataliya.infrastructure.SessionFactoryProvider;
import com.nataliya.infrastructure.TransactionManager;
import com.nataliya.model.entity.Match;
import org.hibernate.SessionFactory;

public class FinishedMatchPersistenceService {

    private static final FinishedMatchPersistenceService INSTANCE = new FinishedMatchPersistenceService();
    private final SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
    private final MatchDao matchDao = new MatchDao(sessionFactory);

    private FinishedMatchPersistenceService(){
    }

    public static FinishedMatchPersistenceService getInstance(){
        return INSTANCE;
    }

    public void saveFinishedMatch(Match finishedMatch) {
        TransactionManager transactionManager = new TransactionManager();
        transactionManager.runInTransaction(() -> matchDao.save(finishedMatch));
    }

}
