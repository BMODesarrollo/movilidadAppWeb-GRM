/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmNovedadIncluir;
import com.movilidad.model.PmPeriodosLiquidacion;
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
public class PmPeriodosLiquidacionFacade extends AbstractFacade<PmPeriodosLiquidacion> implements PmPeriodosLiquidacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmPeriodosLiquidacionFacade() {
        super(PmPeriodosLiquidacion.class);
    }

    @Override
    public List<PmPeriodosLiquidacion> getAllActivo() {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    pm_periodos_liquidacion p\n"
                    + "WHERE\n"
                    + "    p.estado_reg = 0 and p.activo = 1;", PmPeriodosLiquidacion.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
        @Override
    public PmPeriodosLiquidacion findProximoPeriodo(int id) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    pm_periodos_liquidacion p\n"
                    + "WHERE\n"
                    + "    p.estado_reg = 0 and p.activo = 1 and p.id_periodos_liquidacion > ?1 LIMIT 1;", PmPeriodosLiquidacion.class);
            q.setParameter(1, id);
            return (PmPeriodosLiquidacion) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
//
//    @Override
//    public PmNovedadIncluir getByIdNovedadTipoDet(int idDet, int idPmNovedadIncluir) {
//        try {
//            Query q = em.createNativeQuery("SELECT \n"
//                    + "    g.*\n"
//                    + "FROM\n"
//                    + "    pm_novedad_incluir g\n"
//                    + "WHERE\n"
//                    + "    g.id_novedad_tipo_detalle = ?1 AND "
//                    + "g.id_pm_novedad_incluir<>?2 AND "
//                    + "estado_reg = 0 LIMIT 1;", PmNovedadIncluir.class);
//            q.setParameter(1, idDet);
//            q.setParameter(2, idPmNovedadIncluir);
//            return (PmNovedadIncluir) q.getSingleResult();
//        } catch (Exception e) {
//            return null;
//        }
//    }

}
