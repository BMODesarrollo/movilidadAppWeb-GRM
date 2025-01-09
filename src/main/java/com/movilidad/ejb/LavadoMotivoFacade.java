/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.LavadoMotivo;
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
public class LavadoMotivoFacade extends AbstractFacade<LavadoMotivo> implements LavadoMotivoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LavadoMotivoFacade() {
        super(LavadoMotivo.class);
    }

    @Override
    public List<LavadoMotivo> findAllEstadoReg() {
        try {
            String sql = "SELECT * FROM lavado_motivo WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, LavadoMotivo.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
