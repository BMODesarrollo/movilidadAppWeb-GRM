/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ActividadInfraDiaria;
import com.movilidad.utils.Util;
import java.util.Date;
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
public class ActividadInfraDiariaFacade extends AbstractFacade<ActividadInfraDiaria> implements ActividadInfraDiariaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActividadInfraDiariaFacade() {
        super(ActividadInfraDiaria.class);
    }

    /**
     * Permite retornar List ActividadInfraDiaria con los parametros de fecha
     * suministrados
     *
     * @param ini Objeto Date
     * @param fin Objeto Date
     * @return Objeto List ActividadInfraDiaria
     */
    @Override
    public List<ActividadInfraDiaria> findAllByFechaHora(Date ini, Date fin) {
        try {
            String sql = "SELECT * "
                    + "FROM actividad_infra_diaria "
                    + "WHERE DATE(fecha_hora) BETWEEN ?1 AND ?2 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, ActividadInfraDiaria.class);
            q.setParameter(1, Util.dateFormat(ini));
            q.setParameter(2, Util.dateFormat(fin));
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
