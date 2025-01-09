/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MultaClasificacion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author HP
 */
@Stateless
public class MultaClasificacionFacade extends AbstractFacade<MultaClasificacion> implements MultaClasificacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MultaClasificacionFacade() {
        super(MultaClasificacion.class);
    }

    @Override
    public List<MultaClasificacion> obtenerTM(int i) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM multa_clasificacion "
                    + "where id_multa_tipo = ? AND estado_reg = 0", MultaClasificacion.class)
                    .setParameter(1, i);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<MultaClasificacion> findallEst() {

        try {
            Query q = em.createQuery("SELECT m FROM MultaClasificacion m WHERE m.estadoReg = :estadoReg", MultaClasificacion.class)
                    .setParameter("estadoReg", 0);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }

    }

}
