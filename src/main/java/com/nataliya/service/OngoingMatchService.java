package com.nataliya.service;

import com.nataliya.dao.PlayerDao;
import com.nataliya.dto.NewMatchDto;
import com.nataliya.exception.DatabaseException;
import com.nataliya.exception.InvalidRequestException;
import com.nataliya.infrastructure.HibernateUtil;
import com.nataliya.model.OngoingMatch;
import com.nataliya.model.Score;
import com.nataliya.model.entity.Player;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class OngoingMatchService {

    private static final OngoingMatchService INSTANCE = new OngoingMatchService();

    private OngoingMatchService(){
    }

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final PlayerDao playerDao = new PlayerDao(sessionFactory);
    private final Map<UUID, OngoingMatch> ongoingMatches = new HashMap<>();

    public OngoingMatch getOngoingMatch(UUID uuid){
        return Optional.ofNullable(ongoingMatches.get(uuid))
                .orElseThrow(() -> new InvalidRequestException(String.format("Match with UUID %s doesn't exist or is already finished", uuid)));
     }

    public OngoingMatch createOngoingMatch(NewMatchDto newMatchDto){

        Player player1 = getPlayer(newMatchDto.getPlayer1Name());
        Player player2 = getPlayer(newMatchDto.getPlayer2Name());

        OngoingMatch ongoingMatch = new OngoingMatch(player1, player2);
        ongoingMatches.put(ongoingMatch.getUuid(), ongoingMatch);

        return ongoingMatch;
    }

    public static OngoingMatchService getInstance(){
        return INSTANCE;
    }

    private Player getPlayer(String name){
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Player player = playerDao.findByName(name).orElseGet(() -> playerDao.save(new Player(name)));
            session.getTransaction().commit();
            return player;
        }
        catch (HibernateException e){
            if(session != null && session.getTransaction().isActive()){
                session.getTransaction().rollback();
            }
            throw new DatabaseException("Execute operation error", e);
        }
    }
}
