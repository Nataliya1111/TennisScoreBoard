package com.nataliya.infrastructure;

import com.nataliya.dao.PlayerDao;
import com.nataliya.model.entity.Match;
import com.nataliya.model.entity.Player;
import com.nataliya.service.PersistentMatchService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;

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

        saveFinishedMatch(john, paul, paul);
        saveFinishedMatch(george, richard, richard);
        saveFinishedMatch(john, george, george);
        saveFinishedMatch(john, richard, richard);
        saveFinishedMatch(george, paul, paul);
        saveFinishedMatch(john, richard, john);
        saveFinishedMatch(richard, paul, paul);
        saveFinishedMatch(paul, john, paul);
        saveFinishedMatch(george, john, george);
        saveFinishedMatch(richard, john, richard);
        saveFinishedMatch(john, paul, paul);
        saveFinishedMatch(george, richard, richard);
        saveFinishedMatch(john, george, george);
        saveFinishedMatch(john, richard, richard);
        saveFinishedMatch(george, paul, paul);
        saveFinishedMatch(john, richard, john);
        saveFinishedMatch(richard, paul, paul);
        saveFinishedMatch(paul, john, paul);
        saveFinishedMatch(george, john, george);
        saveFinishedMatch(richard, john, richard);
        saveFinishedMatch(john, paul, paul);
        saveFinishedMatch(george, richard, richard);
        saveFinishedMatch(john, george, george);
        saveFinishedMatch(john, richard, richard);
        saveFinishedMatch(george, paul, paul);
        saveFinishedMatch(john, richard, john);
        saveFinishedMatch(richard, paul, paul);
        saveFinishedMatch(paul, john, paul);
        saveFinishedMatch(george, john, george);
        saveFinishedMatch(richard, john, richard);
        saveFinishedMatch(richard, john, richard);
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
