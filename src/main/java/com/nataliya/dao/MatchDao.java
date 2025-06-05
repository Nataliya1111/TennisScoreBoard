package com.nataliya.dao;

import com.nataliya.model.entity.Match;
import com.nataliya.model.entity.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class MatchDao implements Dao<Long, Match>{

    private static final String GET_ALL = "from Match order by id";
    private static final String GET_BY_PLAYER_NAME = "from Match where lower(player1.name) like :name or lower(player2.name) like :name escape '\\'";
    private final SessionFactory sessionFactory;

    @Override
    public Match save(Match match) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(match);
        log.info("Match between players {} and {} has been saved", match.getPlayer1().getName(), match.getPlayer2().getName());
        return match;
    }

    public List<Match> getAll(){
        Session session = sessionFactory.getCurrentSession();
        List<Match> matches = session.createQuery(GET_ALL, Match.class).getResultList();
        log.info("List of all matches has been gotten");
        return matches;
    }

    public List<Match> getByName(String name){
        name = name.replace("%", "\\%").replace("_", "\\_");
        Session session = sessionFactory.getCurrentSession();
        List<Match> matches = session.createQuery(GET_BY_PLAYER_NAME, Match.class)
                .setParameter("name", name.toLowerCase() + "%").getResultList();
        log.info("List of matches with player {} has been gotten", name);
        return matches;
    }
}
