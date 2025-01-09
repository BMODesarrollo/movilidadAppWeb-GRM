/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadMttoTipoActividad;
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
public class NovedadMttoTipoActividadFacade extends AbstractFacade<NovedadMttoTipoActividad> implements NovedadMttoTipoActividadFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadMttoTipoActividadFacade() {
        super(NovedadMttoTipoActividad.class);
    }

    @Override
    public List<NovedadMttoTipoActividad> findAllEstadoReg() {
        try {
            String sql = "SELECT * "
                    + "FROM novedad_mtto_tipo_actividad "
                    + "WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, NovedadMttoTipoActividad.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
