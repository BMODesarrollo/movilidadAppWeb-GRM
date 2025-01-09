/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgSolicitudMotivo;
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
public class PrgSolicitudMotivoFacade extends AbstractFacade<PrgSolicitudMotivo> implements PrgSolicitudMotivoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgSolicitudMotivoFacade() {
        super(PrgSolicitudMotivo.class);
    }

    @Override
    public List<PrgSolicitudMotivo> findAllEstadoReg() {
        try {
            String sql = "SELECT * FROM prg_solicitud_motivo WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, PrgSolicitudMotivo.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
