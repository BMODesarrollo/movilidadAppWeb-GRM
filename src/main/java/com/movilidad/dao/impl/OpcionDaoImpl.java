/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dao.impl;

import com.movilidad.dao.OpcionDao;
import com.movilidad.model.Opcion;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository("opcionDao")
public class OpcionDaoImpl implements OpcionDao {

    @PersistenceContext(unitName = "rigel")
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Opcion findOpcion(Integer id) {
        TypedQuery<Opcion> q = this.getEntityManager()
                .createQuery("Select o from Opcion o where o.id=:id", Opcion.class);
        q.setParameter("id", id);
        return q.getSingleResult();
    }

}
