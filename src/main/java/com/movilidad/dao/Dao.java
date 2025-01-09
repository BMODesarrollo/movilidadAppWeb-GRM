/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dao;

import java.util.Collection;

/**
 *
 * @author alexander
 */
public interface Dao<T> {

    void save(T t);

    T retrieve(Long id);

    void update(T t);

    void delete(T t);

    Collection<T> retrieveAll();
}
