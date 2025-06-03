package com.nataliya.infrastructure;

import com.nataliya.exception.DatabaseException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.function.Supplier;

public class TransactionManager {

    private final SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
    private Session session = null;

    public <T> T runInTransaction(Supplier<T> supplier){
        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            T result = supplier.get();
            session.getTransaction().commit();
            return result;
        }
        catch (HibernateException e){
            if(session != null &&session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw new DatabaseException("Execute operation error", e);
        }
    }

    //    public <T> void execute1(Consumer<T> action, T t){
//        try{
//            session = sessionFactory.getCurrentSession();
//            session.beginTransaction();
//            action.accept(t);
//            session.getTransaction().commit();
//        }
//        catch (HibernateException e){
//            if(session != null &&session.getTransaction().isActive()) {
//                session.getTransaction().rollback();
//            }
//            throw new DatabaseException("Execute operation error", e);
//        }
//    }
}
