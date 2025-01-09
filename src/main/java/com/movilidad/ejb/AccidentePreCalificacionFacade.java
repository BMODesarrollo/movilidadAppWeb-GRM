/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidentePreCalificacion;
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
public class AccidentePreCalificacionFacade extends AbstractFacade<AccidentePreCalificacion> implements AccidentePreCalificacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccidentePreCalificacionFacade() {
        super(AccidentePreCalificacion.class);
    }

    @Override
    public AccidentePreCalificacion findByAccCla(Integer idAccClasificacion, Integer idAccCausaSub, Integer idAccCausaRaiz) {
        try {
            String sql = "SELECT * "
                    + "FROM accidente_pre_calificacion "
                    + "WHERE id_accidente_calificacion = ?1 "
                    + "AND id_causasub = ?2 "
                    + (idAccCausaRaiz != null ? "AND id_causaraiz = ?3 " : " ") + " ";
            Query q = em.createNativeQuery(sql, AccidentePreCalificacion.class);
            q.setParameter(1, idAccClasificacion);
            q.setParameter(2, idAccCausaSub);
            if (idAccCausaRaiz != null) {
                q.setParameter(3, idAccCausaRaiz);
            }
            List resultList = q.getResultList();
            if (resultList != null && !resultList.isEmpty()) {
                return (AccidentePreCalificacion) resultList.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<AccidentePreCalificacion> findCausaSubByCalificaion(Integer idAccClasificacion) {
        try {
            String sql = "SELECT * "
                    + "FROM accidente_pre_calificacion "
                    + "WHERE id_accidente_calificacion = ?1 ";
            Query q = em.createNativeQuery(sql, AccidentePreCalificacion.class);
            q.setParameter(1, idAccClasificacion);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
