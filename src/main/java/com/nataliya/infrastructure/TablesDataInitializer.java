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

        Player ivan = getPlayer("Ivan");
        Player petr = getPlayer("Petr");
        Player maria = getPlayer("Maria");
        Player irina = getPlayer("Irina");

        saveFinishedMatch(ivan, petr, petr);
        saveFinishedMatch(maria, irina, irina);
        saveFinishedMatch(ivan, maria, maria);
        saveFinishedMatch(ivan, irina, irina);
        saveFinishedMatch(maria, petr, petr);
        saveFinishedMatch(ivan, irina, ivan);
        saveFinishedMatch(irina, ivan, ivan);
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
