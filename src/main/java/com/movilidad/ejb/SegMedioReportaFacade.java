/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegMedioReporta;
import java.util.ArrayList;
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
public class SegMedioReportaFacade extends AbstractFacade<SegMedioReporta> implements SegMedioReportaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegMedioReportaFacade() {
        super(SegMedioReporta.class);
    }

    @Override
    public List<SegMedioReporta> findAllByEstadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    seg_medio_reporta\n"
                    + "WHERE\n"
                    + "    estado_reg = 0 ORDER BY nombre ASC;", SegMedioReporta.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public SegMedioReporta findByNombre(String nombre, int idSegMedioReporta) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    seg_medio_reporta\n"
                    + "WHERE\n"
                    + "    nombre = ?1 AND id_seg_medio_reporta<>?2\n"
                    + "        AND estado_reg = 0 LIMIT 1;", SegMedioReporta.class);
            q.setParameter(1, nombre);
            q.setParameter(2, idSegMedioReporta);
            return (SegMedioReporta) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
