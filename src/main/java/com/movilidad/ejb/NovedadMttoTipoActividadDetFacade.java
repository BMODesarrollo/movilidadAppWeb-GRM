/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadMttoTipoActividadDet;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cesar
 */
@Stateless
public class NovedadMttoTipoActividadDetFacade extends AbstractFacade<NovedadMttoTipoActividadDet> implements NovedadMttoTipoActividadDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadMttoTipoActividadDetFacade() {
        super(NovedadMttoTipoActividadDet.class);
    }

    @Override
    public List<NovedadMttoTipoActividadDet> findAllEstadoRegByIdNovMttoTpAct(Integer idNovMttoTpAct) {
        try {
            String sql = "SELECT * "
                    + "FROM novedad_mtto_tipo_actividad_det "
                    + "WHERE id_novedad_mtto_tipo_actividad = ?1 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, NovedadMttoTipoActividadDet.class);
            q.setParameter(1, idNovMttoTpAct);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<NovedadMttoTipoActividadDet> findAllEstadoReg() {
        try {
            String sql = "SELECT * "
                    + "FROM novedad_mtto_tipo_actividad_det "
                    + "WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, NovedadMttoTipoActividadDet.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
