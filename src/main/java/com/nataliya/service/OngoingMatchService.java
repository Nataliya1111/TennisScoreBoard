package com.nataliya.service;

import com.nataliya.dao.PlayerDao;
import com.nataliya.dto.NewMatchDto;
import com.nataliya.exception.DatabaseException;
import com.nataliya.exception.NotFoundException;
import com.nataliya.hibernate.SessionFactoryProvider;
import com.nataliya.hibernate.TransactionManager;
import com.nataliya.model.OngoingMatch;
import com.nataliya.model.entity.Player;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class OngoingMatchService {

    private static final OngoingMatchService INSTANCE = new OngoingMatchService();

    private final SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
    private final PlayerDao playerDao = new PlayerDao(sessionFactory);
    private final Map<UUID, OngoingMatch> ongoingMatches = new ConcurrentHashMap<>();

    private OngoingMatchService() {
    }

    public static OngoingMatchService getInstance() {
        return INSTANCE;
    }

    public OngoingMatch createOngoingMatch(NewMatchDto newMatchDto) {

        Player player1 = getPlayer(newMatchDto.getPlayer1Name());
        Player player2 = getPlayer(newMatchDto.getPlayer2Name());

        OngoingMatch ongoingMatch = new OngoingMatch(player1, player2);
        log.info("Ongoing match between players {} and {} has been created, UUID: {}", player1.getName(), player2.getName(), ongoingMatch.getUuid());
        ongoingMatches.put(ongoingMatch.getUuid(), ongoingMatch);

        return ongoingMatch;
    }

    public OngoingMatch getOngoingMatch(UUID uuid) {
        return Optional.ofNullable(ongoingMatches.get(uuid))
                .orElseThrow(() -> new NotFoundException(String.format("Match with UUID %s doesn't exist or is already finished", uuid)));
    }

    public void deleteOngoingMatch(UUID uuid) {
        if (ongoingMatches.remove(uuid) == null) {
            throw new NotFoundException(String.format("Match with UUID %s doesn't exist or is already finished", uuid));
        }
        log.info("Ongoing match with UUID {} has been deleted from ongoingMatches", uuid);
    }

    public Player getPlayer(String name) {
        TransactionManager transactionManager = new TransactionManager();
        try {
            return transactionManager.runInTransaction(() -> playerDao.save(new Player(name)));
        } catch (DatabaseException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                return playerDao.findByName(name).orElseThrow(() -> new NotFoundException(String.format("Player %s could not be saved or retrieved", name)));
            } else {
                throw e;
            }
        }
    }

}