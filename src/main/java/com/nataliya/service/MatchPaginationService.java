package com.nataliya.service;

import com.nataliya.dao.MatchDao;
import com.nataliya.infrastructure.SessionFactoryProvider;
import com.nataliya.infrastructure.TransactionManager;
import com.nataliya.model.entity.Match;
import org.hibernate.SessionFactory;

import java.util.List;

public class MatchPaginationService {

    private static final MatchPaginationService INSTANCE = new MatchPaginationService();
    private static final int PAGE_SIZE = 5;

    private final SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
    private final MatchDao matchDao = new MatchDao(sessionFactory);
    private final TransactionManager transactionManager = new TransactionManager();

    private MatchPaginationService(){
    }

    public static MatchPaginationService getInstance(){
        return INSTANCE;
    }

    public List<Match> getOneMatchesPage(int pageNumber){
        return transactionManager.runInTransaction(() -> matchDao.getOnePageOfMatches(pageNumber, PAGE_SIZE));
    }

    public List<Match> getOneMatchesPageByName(String name, int pageNumber){
        return transactionManager.runInTransaction(() -> matchDao.getOnePageOfMatchesByName(name, pageNumber, PAGE_SIZE));
    }

    public int getLastPageNumber(){
        int matchesQuantity = transactionManager.runInTransaction(() -> matchDao.getMatchesQuantity());
        return (int) Math.ceil((double) matchesQuantity / PAGE_SIZE);
    }

    public int getLastPageNumberByName(String name){
        int matchesQuantity = transactionManager.runInTransaction(() -> matchDao.getMatchesByNameQuantity(name));
        return (int) Math.ceil((double) matchesQuantity / PAGE_SIZE);
    }

}
