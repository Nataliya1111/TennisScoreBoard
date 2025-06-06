package com.nataliya.dao;

import com.nataliya.model.entity.Match;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class MatchDao implements Dao<Long, Match>{

    private static final String GET_ALL = "from Match order by id";
    private static final String GET_BY_PLAYER_NAME = "from Match where lower(player1.name) like :name " +
                                                     "or lower(player2.name) like :name escape '\\' order by id";
    private static final String GET_TOTAL_QUANTITY = "select count(id) from Match";
    private static final String GET_QUANTITY_BY_PLAYER_NAME = "select count(id) from Match where lower(player1.name) like :name or lower(player2.name) like :name escape '\\'";


    private final SessionFactory sessionFactory;

    @Override
    public Match save(Match match) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(match);
        log.info("Match between players {} and {} has been saved", match.getPlayer1().getName(), match.getPlayer2().getName());
        return match;
    }

    public List<Match> getOnePageOfMatches(int pageNumber, int pageSize){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(GET_ALL, Match.class);
        query.setFirstResult((pageNumber-1) * pageSize);
        query.setMaxResults(pageSize);
        List<Match> matches = query.getResultList();
        log.info("List of not filtering matches for page number {} has been gotten", pageNumber);
        return matches;
    }

    public List<Match> getOnePageOfMatchesByName(String name, int pageNumber, int pageSize){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(GET_BY_PLAYER_NAME, Match.class)
                .setParameter("name", name.toLowerCase() + "%");
        query.setFirstResult((pageNumber-1) * pageSize);
        query.setMaxResults(pageSize);
        List<Match> matches = query.getResultList();
        log.info("List of matches with player {} for page number {} has been gotten", name, pageNumber);
        return matches;
    }

    public int getMatchesQuantity(){
        Session session = sessionFactory.getCurrentSession();
        Query countQuery = session.createQuery(GET_TOTAL_QUANTITY, Long.class);
        long matchesQuantity = (long) countQuery.getSingleResult();
        log.info("Quantity of all matches has been gotten");
        return (int)matchesQuantity;
    }

    public int getMatchesByNameQuantity(String name){
        Session session = sessionFactory.getCurrentSession();
        Query countQuery = session.createQuery(GET_QUANTITY_BY_PLAYER_NAME, Long.class)
                .setParameter("name", name.toLowerCase() + "%");
        long matchesQuantity = (long) countQuery.getSingleResult();
        log.info("Quantity of matches with player {} has been gotten", name);
        return (int)matchesQuantity;
    }
}
