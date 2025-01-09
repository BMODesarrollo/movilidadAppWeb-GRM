/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableEstacion;
import com.movilidad.model.CableUbicacion;
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
public class CableUbicacionFacade extends AbstractFacade<CableUbicacion> implements CableUbicacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableUbicacionFacade() {
        super(CableUbicacion.class);
    }

    @Override
    public List<CableUbicacion> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("CableUbicacion.findByEstadoReg", CableUbicacion.class);
            query.setParameter("estadoReg", 0);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public CableUbicacion findByCodigo(String codigo) {
        try {
            String sql = "select * from cable_ubicacion where codigo = ?1 and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, CableUbicacion.class);
            query.setParameter(1, codigo);
            return (CableUbicacion) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CableUbicacion findByNombre(String nombre) {
        try {
            String sql = "select * from cable_ubicacion where nombre = ?1 and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, CableUbicacion.class);
            query.setParameter(1, nombre);
            return (CableUbicacion) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CableUbicacion findByIdArmamento(Integer id) {
        try {
            String sql = "select * from cable_ubicacion where id_seg_registro_armamento=?1 and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, CableUbicacion.class);
            query.setParameter(1, id);
            return (CableUbicacion) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CableUbicacion findByIdMedioComunicacion(Integer id) {
        try {
            String sql = "select * from cable_ubicacion where id_seg_medio_comunicacion=?1 and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, CableUbicacion.class);
            query.setParameter(1, id);
            return (CableUbicacion) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CableUbicacion> getUbicacionesConArmamento() {
        try {
            String sql = "select * from cable_ubicacion where id_seg_registro_armamento <> 0 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, CableUbicacion.class);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
