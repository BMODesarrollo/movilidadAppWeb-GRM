/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.LavadoCalificacion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author soluciones-it
 */
@Stateless
public class LavadoCalificacionFacade extends AbstractFacade<LavadoCalificacion> implements LavadoCalificacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LavadoCalificacionFacade() {
        super(LavadoCalificacion.class);
    }

    @Override
    public List<LavadoCalificacion> findAllEstadoReg() {
        String sql = "SELECT * FROM lavado_calificacion WHERE estado_reg = 0";
        Query q = em.createNativeQuery(sql, LavadoCalificacion.class);
        return q.getResultList();
    }

}
