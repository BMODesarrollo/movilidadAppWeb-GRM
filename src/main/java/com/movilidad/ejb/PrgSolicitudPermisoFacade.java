/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgSolicitud;
import com.movilidad.model.PrgSolicitudCambio;
import com.movilidad.model.PrgSolicitudPermiso;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class PrgSolicitudPermisoFacade extends AbstractFacade<PrgSolicitudPermiso> implements PrgSolicitudPermisoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgSolicitudPermisoFacade() {
        super(PrgSolicitudPermiso.class);
    }

    @Override
    public PrgSolicitudPermiso findBySolicitud(Integer idSolicitud) {
        try {
            String sql = "select * from prg_solicitud_permiso where id_prg_solicitud = ?1 and estado_reg=0;";

            Query query = em.createNativeQuery(sql, PrgSolicitudPermiso.class);
            query.setParameter(1, idSolicitud);
            return (PrgSolicitudPermiso) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
