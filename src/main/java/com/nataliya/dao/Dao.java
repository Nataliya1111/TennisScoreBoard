package com.nataliya.dao;

import java.io.Serializable;

public interface Dao<ID extends Serializable, E> {

    E save(E model);


}
