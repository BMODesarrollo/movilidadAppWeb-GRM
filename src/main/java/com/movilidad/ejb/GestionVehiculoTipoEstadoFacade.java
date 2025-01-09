/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestionVehiculo;
import com.movilidad.model.GestionVehiculoTipoEstado;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jjunco
 */
@Stateless
public class GestionVehiculoTipoEstadoFacade extends AbstractFacade<GestionVehiculoTipoEstado> implements GestionVehiculoTipoEstadoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GestionVehiculoTipoEstadoFacade() {
        super(GestionVehiculoTipoEstado.class);
    }

    @Override
    public List<GestionVehiculoTipoEstado> findAllEstadoReg() {
        String sql = "SELECT * FROM gestion_vehiculo_tipo_estado WHERE estado_reg = 0";
        Query q = em.createNativeQuery(sql, GestionVehiculoTipoEstado.class);
        return q.getResultList();
    }

}
