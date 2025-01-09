/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegMedioComunicacion;
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
public class SegMedioComunicacionFacade extends AbstractFacade<SegMedioComunicacion> implements SegMedioComunicacionFacadeLocal {
    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegMedioComunicacionFacade() {
        super(SegMedioComunicacion.class);
    }

    @Override
    public List<SegMedioComunicacion> findByEstadoReg() {
        try {
            String sql = "select * from seg_medio_comunicacion where estado_reg = 0;";
            
            Query query = em.createNativeQuery(sql, SegMedioComunicacion.class);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public SegMedioComunicacion findByCodigo(String codigo, Integer idRegistro) {
        try {
            String sql = "select * from seg_medio_comunicacion where id_seg_medio_comunicacion <> ?1 and codigo = ?2 LIMIT 1;";

            Query query = em.createNativeQuery(sql, SegMedioComunicacion.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, codigo);
            return (SegMedioComunicacion) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SegMedioComunicacion findBySerial(String serial, Integer idRegistro) {
        try {
            String sql = "select * from seg_medio_comunicacion where id_seg_medio_comunicacion <> ?1 and serial = ?2 LIMIT 1;";

            Query query = em.createNativeQuery(sql, SegMedioComunicacion.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, serial);
            return (SegMedioComunicacion) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SegMedioComunicacion findByImei(String imei, Integer idRegistro) {
        try {
            String sql = "select * from seg_medio_comunicacion where id_seg_medio_comunicacion <> ?1 and imei = ?2 LIMIT 1;";

            Query query = em.createNativeQuery(sql, SegMedioComunicacion.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, imei);
            return (SegMedioComunicacion) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}
