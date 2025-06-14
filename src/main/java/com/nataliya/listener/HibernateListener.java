package com.nataliya.listener;

import com.nataliya.hibernate.SessionFactoryProvider;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebListener
public class HibernateListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SessionFactoryProvider.getSessionFactory();
        log.info("Hibernate SessionFactory initialized successfully during application startup");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        SessionFactoryProvider.shutdown();
        log.info("Hibernate SessionFactory shut down gracefully during application shutdown");
    }
}
