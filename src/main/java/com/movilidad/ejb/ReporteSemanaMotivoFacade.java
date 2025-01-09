/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ReporteSemanaActualMotivo;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Julián Arévalo
 */
@Stateless
public class ReporteSemanaMotivoFacade extends AbstractFacade<ReporteSemanaActualMotivo> implements ReporteSemanaMotivoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReporteSemanaMotivoFacade() {
        super(ReporteSemanaActualMotivo.class);
    }

    @Override
    public List<ReporteSemanaActualMotivo> getAllActivo() {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    reporte_semana_motivo p\n"
                    + "WHERE\n"
                    + "    p.estado_reg = 0 and p.activo = 1;", ReporteSemanaActualMotivo.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
