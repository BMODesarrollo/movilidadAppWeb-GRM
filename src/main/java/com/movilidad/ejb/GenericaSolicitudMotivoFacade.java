/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaSolicitudMotivo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cesar
 */
@Stateless
public class GenericaSolicitudMotivoFacade extends AbstractFacade<GenericaSolicitudMotivo> implements GenericaSolicitudMotivoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaSolicitudMotivoFacade() {
        super(GenericaSolicitudMotivo.class);
    }

    @Override
    public List<GenericaSolicitudMotivo> findAllEstadoReg() {
        try {
            String sql = "SELECT * FROM generica_solicitud_motivo WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, GenericaSolicitudMotivo.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
