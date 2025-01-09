/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableEventoTipo;
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
public class CableEventoTipoFacade extends AbstractFacade<CableEventoTipo> implements CableEventoTipoFacadeLocal {
    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableEventoTipoFacade() {
        super(CableEventoTipo.class);
    }

    @Override
    public CableEventoTipo findByCodigo(String codigo, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM cable_evento_tipo where codigo = ?1 and id_cable_evento_tipo <> ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, CableEventoTipo.class);
            query.setParameter(1, codigo);
            query.setParameter(2, idRegistro);

            return (CableEventoTipo) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CableEventoTipo findByNombre(String nombre, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM cable_evento_tipo where nombre = ?1 and id_cable_evento_tipo <> ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, CableEventoTipo.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);

            return (CableEventoTipo) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CableEventoTipo> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("CableEventoTipo.findByEstadoReg", CableEventoTipo.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
