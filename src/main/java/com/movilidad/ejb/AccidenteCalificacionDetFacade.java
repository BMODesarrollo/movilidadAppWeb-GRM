/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteCalificacionDet;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cesar
 */
@Stateless
public class AccidenteCalificacionDetFacade extends AbstractFacade<AccidenteCalificacionDet> implements AccidenteCalificacionDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccidenteCalificacionDetFacade() {
        super(AccidenteCalificacionDet.class);
    }

    @Override
    public List<AccidenteCalificacionDet> findByAccClasificacion(Integer idAccClasificacion) {
        try {
            String sql = "SELECT * "
                    + "FROM accidente_calificacion_det "
                    + "WHERE id_accidente_calificacion = ?1 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, AccidenteCalificacionDet.class);
            q.setParameter(1, idAccClasificacion);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Integer calcularevaluacion(Integer idAccClasificacion, Integer idAccAnalisis) {
        try {
            String sql = "SELECT SUM(calificacion) "
                    + "FROM accidente_calificacion_det "
                    + "WHERE id_accidente_calificacion = ?1 "
                    + "AND id_accidente_analisis = ?2 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, idAccClasificacion);
            q.setParameter(2, idAccAnalisis);
            Object singleResult = q.getSingleResult();
            if (singleResult != null) {
                return new Integer(singleResult.toString());
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
