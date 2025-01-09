/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgDesasignarParam;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author soluciones-it
 */
@Stateless
public class PrgDesasignarParamFacade extends AbstractFacade<PrgDesasignarParam> implements PrgDesasignarParamFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgDesasignarParamFacade() {
        super(PrgDesasignarParam.class);
    }

    @Override
    public List<PrgDesasignarParam> findAllEstadoReg() {
        String sql = "SELECT * FROM prg_desasignar_param WHERE estado_reg = 0";
        Query q = em.createNativeQuery(sql, PrgDesasignarParam.class);
        return q.getResultList();
    }

    @Override
    public List<PrgDesasignarParam> findByIdNovedadTipoDetalle(Integer idPrgDesasignarParam, Integer idNovTpDet) {
        String sql = "SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    prg_desasignar_param\n"
                + "WHERE\n"
                + "    id_prg_desasignar_param <> ?1\n"
                + "        AND id_novedad_tipo_detalle = ?2\n"
                + "        AND estado_reg = 0";
        Query q = em.createNativeQuery(sql, PrgDesasignarParam.class);
        q.setParameter(1, idPrgDesasignarParam);
        q.setParameter(2, idNovTpDet);
        return q.getResultList();
    }

}
