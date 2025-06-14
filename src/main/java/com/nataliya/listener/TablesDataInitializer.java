package com.nataliya.listener;

import com.nataliya.model.entity.Match;
import com.nataliya.model.entity.Player;
import com.nataliya.service.OngoingMatchService;
import com.nataliya.service.PersistentMatchService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@WebListener
public class TablesDataInitializer implements ServletContextListener {

    private static final int INITIAL_MATCHES_QUANTITY = 35;

    private final OngoingMatchService ongoingMatchService = OngoingMatchService.getInstance();
    private final PersistentMatchService persistentMatchService = PersistentMatchService.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        List<Player> players = Stream.of("John", "Paul", "George", "Richard")
                .map(ongoingMatchService::getPlayer).toList();

//        Player john = ongoingMatchService.getPlayer("John");
//        Player paul = ongoingMatchService.getPlayer("Paul");
//        Player george = ongoingMatchService.getPlayer("George");
//        Player richard = ongoingMatchService.getPlayer("Richard");
//        List<Player> players = List.of(john, paul, george, richard);

        Random random = new Random();
        for (int i = 0; i < INITIAL_MATCHES_QUANTITY; i++) {
            int randomPlayer1 = random.nextInt(players.size());
            int randomPlayer2 = random.nextInt(players.size());
            while (randomPlayer2 == randomPlayer1) {
                randomPlayer2 = random.nextInt(players.size());
            }
            int randomWinner = random.nextBoolean() ? randomPlayer1 : randomPlayer2;
            saveFinishedMatch(players.get(randomPlayer1), players.get(randomPlayer2), players.get(randomWinner));
        }
    }

    private void saveFinishedMatch(Player player1, Player player2, Player winner) {
        Match finishedMatch = Match.builder().player1(player1)
                .player2(player2).winner(winner).build();
        persistentMatchService.saveFinishedMatch(finishedMatch);
    }
}