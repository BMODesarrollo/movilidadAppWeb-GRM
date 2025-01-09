/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegReportePilona;
import com.movilidad.utils.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author solucionesit
 */
@Stateless
public class SegReportePilonaFacade extends AbstractFacade<SegReportePilona> implements SegReportePilonaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegReportePilonaFacade() {
        super(SegReportePilona.class);
    }

    @Override
    public List<SegReportePilona> findRanfoFechaEstadoReg(Date desde, Date hasta) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    rp.*\n"
                    + "FROM\n"
                    + "    seg_reporte_pilona rp\n"
                    + "WHERE\n"
                    + "    DATE(rp.fecha_hora) BETWEEN ?1 AND ?2\n"
                    + "        AND estado_reg = 0;", SegReportePilona.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            return q.getResultList();

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
