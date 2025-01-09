/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.OperacionPatios;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Soluciones IT
 */
@Stateless
public class OperacionPatiosFacade extends AbstractFacade<OperacionPatios> implements OperacionPatiosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OperacionPatiosFacade() {
        super(OperacionPatios.class);
    }

    @Override
    public OperacionPatios findByCodigo(String value, int id) {
        try {
            if (id == 0) {
                TypedQuery<OperacionPatios> query = em.createQuery(
                        "SELECT e FROM OperacionPatios e WHERE e.codigoPatio= ?1",
                        OperacionPatios.class);
                return query.setParameter(1, value).getSingleResult();
            }
            TypedQuery<OperacionPatios> query = em.createQuery(
                    "SELECT e FROM OperacionPatios e WHERE e.codigoPatio= ?1 AND"
                    + " e.idOperacionPatios<>?2", OperacionPatios.class);
            query.setParameter(1, value);
            query.setParameter(2, id);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<OperacionPatios> findAllActivos() {
        TypedQuery<OperacionPatios> query = em.createQuery(
                "SELECT e FROM OperacionPatios e WHERE e.estadoReg= ?1",
                OperacionPatios.class);
        query.setParameter(1, 0);
        return query.getResultList();
    }

    @Override
    public List<OperacionPatios> findAllActivosLatLong() {
        Query query = em.createNativeQuery(
                "SELECT \n"
                + "    op.*\n"
                + "FROM\n"
                + "    operacion_patios op\n"
                + "WHERE\n"
                + "    op.latitud IS NOT NULL\n"
                + "        AND op.longitud IS NOT NULL\n"
                + "        AND op.estado_reg = 0;",
                OperacionPatios.class);
        return query.getResultList();
    }

}
