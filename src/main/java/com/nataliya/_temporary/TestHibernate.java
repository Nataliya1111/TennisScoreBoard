package com.nataliya._temporary;

import com.nataliya.dto.NewMatchDto;
import com.nataliya.model.MatchState;
import com.nataliya.model.OngoingMatch;
import com.nataliya.service.OngoingMatchService;
import com.nataliya.service.ScoreCountService;

import java.io.IOException;

public class TestHibernate {

    public static void main(String[] args) throws IOException {


        OngoingMatchService ongoingMatchService = new OngoingMatchService();
        NewMatchDto newMatchDto = new NewMatchDto("Olga", "Ivan");

        OngoingMatch ongoingMatch = ongoingMatchService.createOngoingMatch(newMatchDto);
        System.out.println(ongoingMatch);

        NewMatchDto newMatchDto1 = new NewMatchDto("Olga2", "Ivan");

        OngoingMatch ongoingMatch1 = ongoingMatchService.createOngoingMatch(newMatchDto1);
        System.out.println(ongoingMatch1);

        ScoreCountService scoreCountService = new ScoreCountService();
        System.out.println("1" + scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId()));
        System.out.println("2" + scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId()));
        System.out.println("3" + scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer2().getId()));
        System.out.println("4" + scoreCountService.updateScore(ongoingMatch, ongoingMatch.getPlayer1().getId()));


        MatchState matchState = ongoingMatch.getMatchState();
        System.out.println(matchState);
        matchState = MatchState.TIE_BREAK;
        System.out.println(matchState);
        matchState = MatchState.FINISHED;
        System.out.println(matchState);


//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        PlayerDao playerDao = new PlayerDao(sessionFactory);
//        String olga = "Olga";
//
//
//        try (Session session = sessionFactory.getCurrentSession()) {
//            session.beginTransaction();
//            Player player = playerDao.findByName(olga).orElse(playerDao.save(new Player(olga)));
//            System.out.println(player);
//            session.getTransaction().commit();
//        }


        //System.out.println("App is running... Press Enter to exit");
        //System.in.read();
    }
}
