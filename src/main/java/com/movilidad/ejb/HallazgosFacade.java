/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Hallazgo;
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
public class HallazgosFacade extends AbstractFacade<Hallazgo> implements HallazgosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HallazgosFacade() {
        super(Hallazgo.class);
    }

    @Override
    public List<Hallazgo> findAllByDateRangeAndEstadoReg(Date desde, Date hasta) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    hallazgos\n"
                    + "WHERE\n"
                    + "    DATE(fecha_identificacion) BETWEEN ?1 AND ?2\n"
                    + "        AND estado_reg = 0;";
            Query query = em.createNativeQuery(sql, Hallazgo.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Hallazgo findByConsecutivo(int consecutivo) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    hallazgos\n"
                    + "WHERE\n"
                    + "    consecutivo = ?1\n"
                    + "        AND estado_reg = 0 LIMIT 1;";
            Query query = em.createNativeQuery(sql, Hallazgo.class);
            query.setParameter(1, consecutivo);

            return (Hallazgo) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Hallazgo> findAllByDateRangeAndArea(Date desde, Date hasta, int idHallazgoParamArea) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    hallazgos\n"
                    + "WHERE\n"
                    + "    DATE(fecha_identificacion) BETWEEN ?1 AND ?2\n"
                    + "        AND id_hallazgos_param_area = ?3"
                    + "        AND estado_reg = 0;";
            Query query = em.createNativeQuery(sql, Hallazgo.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, idHallazgoParamArea);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
