package com.nataliya.hibernate;

import com.nataliya.exception.DatabaseException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.function.Supplier;

public class TransactionManager {

    private final SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    public <T> T runInTransaction(Supplier<T> supplier) {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            T result = supplier.get();
            session.getTransaction().commit();
            return result;
        } catch (HibernateException e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw new DatabaseException("Execute operation error", e);
        }
    }

}
