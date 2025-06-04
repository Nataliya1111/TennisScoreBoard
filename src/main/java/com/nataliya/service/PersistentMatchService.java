package com.nataliya.service;

import com.nataliya.dao.MatchDao;
import com.nataliya.infrastructure.SessionFactoryProvider;
import com.nataliya.infrastructure.TransactionManager;
import com.nataliya.model.entity.Match;
import com.nataliya.model.entity.Player;
import org.hibernate.SessionFactory;

import java.util.List;

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

    public List<Match> getPersistentMatches(){
        return transactionManager.runInTransaction(() -> matchDao.getAll());
    }

    public List<Match> getPersistentMatchesByPlayer(Player player){
        return transactionManager.runInTransaction(() -> matchDao.getByPlayer(player));
    }

}
