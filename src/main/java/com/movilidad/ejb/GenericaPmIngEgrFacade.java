/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmIngEgr;
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
public class GenericaPmIngEgrFacade extends AbstractFacade<GenericaPmIngEgr> implements GenericaPmIngEgrFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaPmIngEgrFacade() {
        super(GenericaPmIngEgr.class);
    }

    @Override
    public GenericaPmIngEgr verificarRegistro(Integer idRegistro, Integer idEmpleado, Date fecha, Integer idConcepto, Integer idArea) {
        String sql = "SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    generica_pm_ing_egr\n"
                + "WHERE\n"
                + "    fecha = ?1 AND id_empleado = ?2\n"
                + "        AND id_generica_pm_ie_conceptos = ?3\n"
                + "        AND id_generica_pm_ing_egr <> ?4\n"
                + "        AND id_param_area = ?5\n"
                + "        AND estado_reg = 0 LIMIT 1;";

        try {
            Query query = em.createNativeQuery(sql, GenericaPmIngEgr.class);
            query.setParameter(1, Util.dateFormat(fecha));
            query.setParameter(2, idEmpleado);
            query.setParameter(3, idConcepto);
            query.setParameter(4, idRegistro);
            query.setParameter(5, idArea);

            return (GenericaPmIngEgr) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GenericaPmIngEgr> findAllByDateRange(Date fechaIni, Date fechaFin, Integer idArea) {
        String sql = "SELECT * FROM generica_pm_ing_egr where fecha between ?1 and ?2 and id_param_area = ?3 and estado_reg = 0;";

        try {
            Query query = em.createNativeQuery(sql, GenericaPmIngEgr.class);
            query.setParameter(1, Util.dateFormat(fechaIni));
            query.setParameter(2, Util.dateFormat(fechaFin));
            query.setParameter(3, idArea);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GenericaPmIngEgr> findAllByEstadoReg(Integer idArea) {
        String sql = "SELECT * FROM generica_pm_ing_egr where estado_reg = 0 and id_param_area = ?1;";

        try {
            Query query = em.createNativeQuery(sql, GenericaPmIngEgr.class);
            query.setParameter(1, idArea);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
