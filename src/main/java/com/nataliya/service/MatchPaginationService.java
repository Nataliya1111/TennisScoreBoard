package com.nataliya.service;

import com.nataliya.dao.MatchDao;
import com.nataliya.dto.MatchPageResult;
import com.nataliya.hibernate.SessionFactoryProvider;
import com.nataliya.hibernate.TransactionManager;
import com.nataliya.model.entity.Match;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class MatchPaginationService {

    private static final MatchPaginationService INSTANCE = new MatchPaginationService();
    private static final int PAGE_SIZE = 5;

    private final SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
    private final MatchDao matchDao = new MatchDao(sessionFactory);
    private final TransactionManager transactionManager = new TransactionManager();

    private MatchPaginationService() {
    }

    public static MatchPaginationService getInstance() {
        return INSTANCE;
    }

    public MatchPageResult getMatchesPageAndLastPageNumber(int pageNumber) {
        return transactionManager.runInTransaction(() -> {
            List<Match> matchesPage = matchDao.getPageOfMatches(pageNumber, PAGE_SIZE);
            int matchesQuantity = matchDao.countMatches();
            int lastPageNumber = (int) Math.ceil((double) matchesQuantity / PAGE_SIZE);
            return new MatchPageResult(matchesPage, lastPageNumber);
        });
    }

    public MatchPageResult getMatchesPageAndLastPageNumberByName(String name, int pageNumber) {
        return transactionManager.runInTransaction(() -> {
            List<Match> matchesPage = matchDao.getPageOfMatchesByName(name, pageNumber, PAGE_SIZE);
            int matchesQuantity = matchDao.countMatchesByName(name);
            int lastPageNumber = (int) Math.ceil((double) matchesQuantity / PAGE_SIZE);
            return new MatchPageResult(matchesPage, lastPageNumber);
        });
    }

    public List<Integer> getPagesToShow(int lastPage, int currentPage) {
        List<Integer> pagesToShow = new ArrayList<>();

        if (lastPage <= 5) {
            for (int i = 1; i <= lastPage; i++) pagesToShow.add(i);
        } else if (currentPage <= 3) {
            pagesToShow = List.of(1, 2, 3, -1, lastPage); // -1 will mean '...'
        } else if (currentPage >= lastPage - 2) {
            pagesToShow = List.of(1, -1, lastPage - 2, lastPage - 1, lastPage);
        } else {
            pagesToShow = List.of(1, -1, currentPage, -1, lastPage);
        }
        return pagesToShow;
    }

}
