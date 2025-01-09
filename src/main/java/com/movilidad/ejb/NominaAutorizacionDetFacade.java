/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NominaAutorizacionDet;
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
public class NominaAutorizacionDetFacade extends AbstractFacade<NominaAutorizacionDet> implements NominaAutorizacionDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NominaAutorizacionDetFacade() {
        super(NominaAutorizacionDet.class);
    }

    @Override
    public List<NominaAutorizacionDet> findByIdNominaAutorizacion(Integer idNominaAutorizacion) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    nad.*\n"
                + "FROM\n"
                + "    nomina_autorizacion_det nad\n"
                + "WHERE\n"
                + "    nad.id_nomina_autorizacion = ?1;", NominaAutorizacionDet.class);
        q.setParameter(1, idNominaAutorizacion);
        return q.getResultList();
    }

    @Override
    public List<NominaAutorizacionDet> findByIdNominaAutorizacionAndCodigoError(Integer idNominaAutorizacion, Integer codigoError) {
        String sql = "SELECT \n"
                + "    nad.*\n"
                + "FROM\n"
                + "    nomina_autorizacion_det nad\n"
                + "WHERE\n"
                + "    nad.id_nomina_autorizacion = ?1 AND \n"
                + "    nad.codigo_error = ?2;";

        Query q = em.createNativeQuery(sql, NominaAutorizacionDet.class);
        q.setParameter(1, idNominaAutorizacion);
        q.setParameter(2, codigoError);
        return q.getResultList();
    }

    @Override
    public Long obtenerCantidadErrores(Integer idNominaAutorizacion) {
        String sql = "SELECT count(*) FROM nomina_autorizacion_det where "
                + "id_nomina_autorizacion = ?1 and mensaje_error is not null;";
        Query q = em.createNativeQuery(sql);
        q.setParameter(1, idNominaAutorizacion);
        return (Long) q.getSingleResult();
    }

}
