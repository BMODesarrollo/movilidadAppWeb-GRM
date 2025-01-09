/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccPre;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class AccPreFacade extends AbstractFacade<AccPre> implements AccPreFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccPreFacade() {
        super(AccPre.class);
    }

    @Override
    public boolean verificarAccidente(int idNovedad) {
        try {
            String sql = "SELECT * FROM acc_pre where id_novedad = ?1";
            Query query = em.createNativeQuery(sql, AccPre.class);
            query.setParameter(1, idNovedad);
            return query.getSingleResult() != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<AccPre> findByFecha(Date fechaInicio, Date fechaFin) {
        try {
            String sql = "SELECT \n"
                    + "    ac.*\n"
                    + "FROM\n"
                    + "    acc_pre ac\n"
                    + "        INNER JOIN\n"
                    + "    novedad n ON ac.id_novedad = n.id_novedad\n"
                    + "WHERE\n"
                    + "    DATE(n.fecha) BETWEEN ?1 AND ?2\n"
                    + "        AND ac.estado_reg = 0";
            Query query = em.createNativeQuery(sql, AccPre.class);
            query.setParameter(1, Util.toDate(Util.dateFormat(fechaInicio)));
            query.setParameter(2, Util.toDate(Util.dateFormat(fechaFin)));
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
