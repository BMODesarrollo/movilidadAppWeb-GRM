/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmIngEgr;
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
public class PmIngEgrFacade extends AbstractFacade<PmIngEgr> implements PmIngEgrFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmIngEgrFacade() {
        super(PmIngEgr.class);
    }

    @Override
    public List<PmIngEgr> findAllByEstadoReg() {
        String sql = "SELECT * FROM pm_ing_egr where estado_reg = 0;";

        try {
            Query query = em.createNativeQuery(sql, PmIngEgr.class);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PmIngEgr> findAllByDateRange(Date fechaIni, Date fechaFin) {
        String sql = "SELECT * FROM pm_ing_egr where fecha between ?1 and ?2 and estado_reg = 0;";

        try {
            Query query = em.createNativeQuery(sql, PmIngEgr.class);
            query.setParameter(1, Util.dateFormat(fechaIni));
            query.setParameter(2, Util.dateFormat(fechaFin));
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PmIngEgr verificarRegistro(Integer idRegistro, Integer idEmpleado, Date fecha, Integer idConcepto) {
        String sql = "SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    pm_ing_egr\n"
                + "WHERE\n"
                + "    fecha = ?1 AND id_empleado = ?2\n"
                + "        AND id_pm_ie_conceptos = ?3\n"
                + "        AND id_pm_ing_egr <> ?4\n"
                + "        AND estado_reg = 0 LIMIT 1;";

        try {
            Query query = em.createNativeQuery(sql, PmIngEgr.class);
            query.setParameter(1, Util.dateFormat(fecha));
            query.setParameter(2, idEmpleado);
            query.setParameter(3, idConcepto);
            query.setParameter(4, idRegistro);
            
            return (PmIngEgr) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
