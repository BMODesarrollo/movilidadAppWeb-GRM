/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PdEstadoProceso;
import com.movilidad.model.PdEstadoProceso;
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
public class PdEstadoProcesoFacade extends AbstractFacade<PdEstadoProceso> implements PdEstadoProcesoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PdEstadoProcesoFacade() {
        super(PdEstadoProceso.class);
    }

    @Override
    public PdEstadoProceso findByNombre(Integer idRegistro, String nombre) {
        try {
            String sql = "SELECT * FROM pd_estado_proceso where id_pd_estado_proceso <> ?1 and nombre = ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PdEstadoProceso.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, nombre);

            return (PdEstadoProceso) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PdEstadoProceso verificarCierreProceso(Integer idRegistro) {
        try {
            String sql = "SELECT * FROM pd_estado_proceso where id_pd_estado_proceso <> ?1 and cierra_proceso = 1 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PdEstadoProceso.class);
            query.setParameter(1, idRegistro);

            return (PdEstadoProceso) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PdEstadoProceso> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("PdEstadoProceso.findByEstadoReg", PdEstadoProceso.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
