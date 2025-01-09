/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ReporteSemanaMotivoPrg;
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
public class ReporteSemanaMotivoPrgFacade extends AbstractFacade<ReporteSemanaMotivoPrg> implements ReporteSemanaMotivoPrgFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReporteSemanaMotivoPrgFacade() {
        super(ReporteSemanaMotivoPrg.class);
    }

    @Override
    public List<ReporteSemanaMotivoPrg> getActivo() {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    reporte_semana_motivo_prg p\n"
                    + "WHERE\n"
                    + "    p.estado_reg = 0;", ReporteSemanaMotivoPrg.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public ReporteSemanaMotivoPrg findByIdPrgTc(Integer idPrgTc) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    reporte_semana_motivo_prg p\n"
                    + "WHERE\n"
                    + "    p.estado_reg = 0 AND p.id_prg_tc= ?1 LIMIT 1;", ReporteSemanaMotivoPrg.class)
                    .setParameter(1, idPrgTc);
            return (ReporteSemanaMotivoPrg) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
