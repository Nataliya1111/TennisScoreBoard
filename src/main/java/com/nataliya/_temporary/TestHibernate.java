package com.nataliya._temporary;

import com.nataliya.dao.PlayerDao;
import com.nataliya.dto.NewMatchDto;
import com.nataliya.model.OngoingMatch;
import com.nataliya.model.entity.Player;
import com.nataliya.infrastructure.HibernateUtil;
import com.nataliya.service.OngoingMatchService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
