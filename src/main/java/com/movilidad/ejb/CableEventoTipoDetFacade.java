/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableEventoTipoDet;
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
public class CableEventoTipoDetFacade extends AbstractFacade<CableEventoTipoDet> implements CableEventoTipoDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableEventoTipoDetFacade() {
        super(CableEventoTipoDet.class);
    }

    @Override
    public CableEventoTipoDet findByCodigo(String codigo, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM cable_evento_tipo_det where codigo = ?1 and id_cable_evento_tipo_det <> ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, CableEventoTipoDet.class);
            query.setParameter(1, codigo);
            query.setParameter(2, idRegistro);

            return (CableEventoTipoDet) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CableEventoTipoDet findByNombre(String nombre, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM cable_evento_tipo_det where nombre = ?1 and id_cable_evento_tipo_det <> ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, CableEventoTipoDet.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);

            return (CableEventoTipoDet) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CableEventoTipoDet> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("CableEventoTipoDet.findByEstadoReg", CableEventoTipoDet.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<CableEventoTipoDet> findAllByTipoEvento(Integer idCableEventoTipo) {
        try {
            String sql = "SELECT * FROM cable_evento_tipo_det where id_cable_evento_tipo = ?1 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, CableEventoTipoDet.class);
            query.setParameter(1, idCableEventoTipo);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CableEventoTipoDet findByClaseEvento(Integer claseEvento) {
        try {
            String sql = "SELECT * FROM cable_evento_tipo_det where clase_evento = ?1 and estado_reg = 0 limit 1;";
            Query query = em.createNativeQuery(sql, CableEventoTipoDet.class);
            query.setParameter(1, claseEvento);

            return (CableEventoTipoDet) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CableEventoTipoDet verifyByClaseEvento(int claseEvento, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM cable_evento_tipo_det where clase_evento = ?1 and id_cable_evento_tipo_det <> ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, CableEventoTipoDet.class);
            query.setParameter(1, claseEvento);
            query.setParameter(2, idRegistro);

            return (CableEventoTipoDet) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
