/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgSolicitud;
import com.movilidad.model.PrgSolicitudCambio;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class PrgSolicitudCambioFacade extends AbstractFacade<PrgSolicitudCambio> implements PrgSolicitudCambioFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgSolicitudCambioFacade() {
        super(PrgSolicitudCambio.class);
    }

    @Override
    public PrgSolicitudCambio findBySolicitud(Integer idSolicitud) {
        try {
            String sql = "select * from prg_solicitud_cambio where id_prg_solicitud = ?1 and estado_reg=0;";

            Query query = em.createNativeQuery(sql, PrgSolicitudCambio.class);
            query.setParameter(1, idSolicitud);

            return (PrgSolicitudCambio) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
