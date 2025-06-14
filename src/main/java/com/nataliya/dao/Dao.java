package com.nataliya.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;

@RequiredArgsConstructor
@Slf4j
public abstract class Dao<ID extends Serializable, E> {

    protected final SessionFactory sessionFactory;

    public E save(E model) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(model);
        log.info("{} has been saved", model.getClass().getSimpleName());
        return model;
    }


}
