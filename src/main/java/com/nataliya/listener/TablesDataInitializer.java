package com.nataliya.listener;

import com.nataliya.dao.PlayerDao;
import com.nataliya.hibernate.SessionFactoryProvider;
import com.nataliya.hibernate.TransactionManager;
import com.nataliya.model.entity.Match;
import com.nataliya.model.entity.Player;
import com.nataliya.service.PersistentMatchService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Random;

@WebListener
public class TablesDataInitializer implements ServletContextListener {

    private final SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
    private final PlayerDao playerDao = new PlayerDao(sessionFactory);
    private final PersistentMatchService persistentMatchService = PersistentMatchService.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        Player john = getPlayer("John");
        Player paul = getPlayer("Paul");
        Player george = getPlayer("George");
        Player richard = getPlayer("Richard");
        List<Player> players = List.of(john, paul, george, richard);

        Random random = new Random();
        for (int i = 0; i < 35; i++) {
            int randomPlayer1 = random.nextInt(players.size());
            int randomPlayer2;
            while (true){
                randomPlayer2 = random.nextInt(players.size());
                if (randomPlayer2 != randomPlayer1){
                    break;
                }
            }
            int randomWinner = random.nextInt(2) == 0 ? randomPlayer1 : randomPlayer2;
            saveFinishedMatch(players.get(randomPlayer1), players.get(randomPlayer2), players.get(randomWinner));
        }
    }

    private Player getPlayer(String name){
        TransactionManager transactionManager = new TransactionManager();
        return transactionManager.runInTransaction(()  -> {
            Player player = playerDao.findByName(name).orElseGet(() -> playerDao.save(new Player(name)));
            return player;
        });
    }

    private void saveFinishedMatch(Player player1, Player player2, Player winner) {
        Match finishedMatch = Match.builder().player1(player1)
                .player2(player2).winner(winner).build();
        persistentMatchService.saveFinishedMatch(finishedMatch);
    }
}
