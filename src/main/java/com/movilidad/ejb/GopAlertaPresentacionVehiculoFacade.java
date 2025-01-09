/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GopAlertaPresentacionVehiculo;
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
public class GopAlertaPresentacionVehiculoFacade extends AbstractFacade<GopAlertaPresentacionVehiculo> implements GopAlertaPresentacionVehiculoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GopAlertaPresentacionVehiculoFacade() {
        super(GopAlertaPresentacionVehiculo.class);
    }

    /**
     * Obtener objeto List de GopAlertaPresentacionVehiculo por estadoReg = 0
     *
     * @return List GopAlertaPresentacionVehiculo
     */
    @Override
    public List<GopAlertaPresentacionVehiculo> findAllEstadoReg() {
        String sql = "SELECT * FROM gop_alerta_presentacion_vehiculo WHERE estado_reg = 0";
        Query q = em.createNativeQuery(sql, GopAlertaPresentacionVehiculo.class);
        return q.getResultList();
    }

}
