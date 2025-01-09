/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.EmpleadoEstado;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Soluciones IT
 */
@Stateless
public class EmpleadoEstadoFacade extends AbstractFacade<EmpleadoEstado> implements EmpleadoEstadoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpleadoEstadoFacade() {
        super(EmpleadoEstado.class);
    }

    @Override
    public List<EmpleadoEstado> findAllActivos() {
        TypedQuery<EmpleadoEstado> query = em.createQuery(
                "SELECT e FROM EmpleadoEstado e WHERE e.estadoReg= ?1",
                EmpleadoEstado.class);
        query.setParameter(1, 0);
        return query.getResultList();
    }

}
