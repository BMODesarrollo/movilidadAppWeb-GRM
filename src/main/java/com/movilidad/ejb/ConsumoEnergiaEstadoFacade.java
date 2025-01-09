/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ConsumoEnergiaEstado;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author solucionesit
 */
@Stateless
public class ConsumoEnergiaEstadoFacade extends AbstractFacade<ConsumoEnergiaEstado> implements ConsumoEnergiaEstadoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConsumoEnergiaEstadoFacade() {
        super(ConsumoEnergiaEstado.class);
    }

    @Override
    public ConsumoEnergiaEstado findByNombre(Integer idRegistro, String nombre) {
        try {
            String sql = "SELECT * FROM consumo_energia_estado where id_consumo_energia_estado <> ?1 and nombre = ?2 and estado_reg = 0 limit 1;";
            Query query = em.createNativeQuery(sql, ConsumoEnergiaEstado.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, nombre);

            return (ConsumoEnergiaEstado) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ConsumoEnergiaEstado> findAllByEstadoReg() {
        try {
            String sql = "SELECT * FROM consumo_energia_estado where estado_reg = 0;";
            Query query = em.createNativeQuery(sql, ConsumoEnergiaEstado.class);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
