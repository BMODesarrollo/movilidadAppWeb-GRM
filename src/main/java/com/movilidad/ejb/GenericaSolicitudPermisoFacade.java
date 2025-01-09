/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaSolicitudPermiso;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class GenericaSolicitudPermisoFacade extends AbstractFacade<GenericaSolicitudPermiso> implements GenericaSolicitudPermisoFacadeLocal {
    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaSolicitudPermisoFacade() {
        super(GenericaSolicitudPermiso.class);
    }

    @Override
    public GenericaSolicitudPermiso findBySolicitud(Integer idSolicitud) {
        try {
            String sql = "select * from generica_solicitud_permiso where id_generica_solicitud = ?1 and estado_reg=0;";

            Query query = em.createNativeQuery(sql, GenericaSolicitudPermiso.class);
            query.setParameter(1, idSolicitud);
            return (GenericaSolicitudPermiso) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
