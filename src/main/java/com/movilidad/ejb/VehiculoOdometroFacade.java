/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoOdometro;
import com.movilidad.utils.Util;
import java.util.Date;
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
public class VehiculoOdometroFacade extends AbstractFacade<VehiculoOdometro> implements VehiculoOdometroFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VehiculoOdometroFacade() {
        super(VehiculoOdometro.class);
    }

    @Override
    public List<VehiculoOdometro> findAll(Date fecha) {
        try {
            String sql = "select * from vehiculo_odometro where fecha = ?1 and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, VehiculoOdometro.class);
            query.setParameter(1, Util.toDate(Util.dateFormat(fecha)));

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean verificarSubida(Date fecha) {
        Query query = em.createQuery("SELECT k from VehiculoOdometro k WHERE k.fecha = :fecha");
        query.setParameter("fecha", Util.toDate(Util.dateFormat(fecha)));
        return query.getResultList().isEmpty();
    }

}
