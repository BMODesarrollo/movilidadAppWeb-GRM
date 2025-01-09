/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestionVehiculo;
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
public class GestionVehiculoFacade extends AbstractFacade<GestionVehiculo> implements GestionVehiculoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GestionVehiculoFacade() {
        super(GestionVehiculo.class);
    }

    @Override
    public List<GestionVehiculo> findAllEstadoReg() {
        String sql = "SELECT * FROM gestion_vehiculo WHERE estado_reg = 0";
        Query q = em.createNativeQuery(sql, GestionVehiculo.class);
        return q.getResultList();
    }

}
