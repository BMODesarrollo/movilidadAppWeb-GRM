/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgDistance;
import com.movilidad.model.PrgStopPoint;
import java.math.BigDecimal;
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
public class PrgDistanceFacade extends AbstractFacade<PrgDistance> implements PrgDistanceFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgDistanceFacade() {
        super(PrgDistance.class);
    }

    @Override
    public BigDecimal getDistance(PrgStopPoint prgFromStop, PrgStopPoint prgToStop) {
        try {

            Query query = em.createQuery("SELECT d.distance FROM PrgDistance d WHERE "
                    + "d.idPrgFromStop = :from "
                    + "AND d.idPrgToStop = :stop AND d.vigente = 1");
            query.setParameter("from", prgFromStop);
            query.setParameter("stop", prgToStop);

            return (BigDecimal) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PrgDistance validarDistancia(PrgStopPoint prgFromStop, PrgStopPoint prgToStop, Integer id) {
        try {
            Query q;
            if (id == 0) {
                q = em.createNativeQuery("SELECT p.* FROM prg_distance p "
                        + "WHERE p.propia = 1 AND p.id_prg_from_stop =?1 "
                        + "AND p.id_prg_to_stop=?2 ", PrgDistance.class);
            } else {
                q = em.createNativeQuery("SELECT p.* FROM prg_distance p "
                        + "WHERE p.propia = 1 AND p.id_prg_from_stop = ?1 "
                        + "AND p.id_prg_to_stop = ?2 AND p.id_prg_distance <> ?3;", PrgDistance.class);
            }

            q.setParameter(1, prgFromStop.getIdPrgStoppoint());
            q.setParameter(2, prgToStop.getIdPrgStoppoint());
            q.setParameter(3, id);

            return (PrgDistance) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgDistance> findAllPropias(int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "         pd.id_gop_unidad_funcional = ?1 AND\n";

        Query q = em.createNativeQuery("SELECT \n"
                + "    pd.*\n"
                + "FROM\n"
                + "    prg_distance pd\n"
                + "WHERE\n"
                + sql_unida_func
                + "    pd.propia = 1;", PrgDistance.class);
        q.setParameter(1, idGopUnidadFuncional);
        return q.getResultList();
    }
}
