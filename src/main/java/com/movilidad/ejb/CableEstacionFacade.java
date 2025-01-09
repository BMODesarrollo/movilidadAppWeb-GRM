/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableEstacion;
import java.util.ArrayList;
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
public class CableEstacionFacade extends AbstractFacade<CableEstacion> implements CableEstacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableEstacionFacade() {
        super(CableEstacion.class);
    }

    @Override
    public List<CableEstacion> findByEstadoReg() {
        try {
            String sql = "select * from cable_estacion where estado_reg = 0;";

            Query query = em.createNativeQuery(sql, CableEstacion.class);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public CableEstacion findByCodigo(Integer idRegistro, String codigo) {
        try {
            String sql = "select * from cable_estacion where id_cable_estacion <> ?1 and codigo = ?2 and estado_reg = 0 LIMIT 1;";

            Query query = em.createNativeQuery(sql, CableEstacion.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, codigo);
            return (CableEstacion) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CableEstacion findByNombre(Integer idRegistro, String nombre) {
        try {
            String sql = "select * from cable_estacion where id_cable_estacion <> ?1 and nombre = ?2 and estado_reg = 0 LIMIT 1;";

            Query query = em.createNativeQuery(sql, CableEstacion.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, nombre);
            return (CableEstacion) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
