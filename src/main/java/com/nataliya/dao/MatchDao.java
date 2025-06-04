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
    private static final String GET_BY_PLAYER1_NAME = "from Match where player1 =:player or player2 =:player";
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

    public List<Match> getByPlayer(Player player){
        Session session = sessionFactory.getCurrentSession();
        List<Match> matches = session.createQuery(GET_BY_PLAYER1_NAME, Match.class)
                .setParameter("player", player).getResultList();
        log.info("List of matches with player {} has been gotten", player);
        return matches;
    }
}
