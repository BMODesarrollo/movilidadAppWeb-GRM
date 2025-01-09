/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaNominaAutorizacionDet;
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
public class GenericaNominaAutorizacionDetFacade extends AbstractFacade<GenericaNominaAutorizacionDet> implements GenericaNominaAutorizacionDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaNominaAutorizacionDetFacade() {
        super(GenericaNominaAutorizacionDet.class);
    }

    @Override
    public List<GenericaNominaAutorizacionDet> findByIdNominaAutorizacion(Integer idNominaAutorizacion) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    nad.*\n"
                + "FROM\n"
                + "    generica_nomina_autorizacion_det nad\n"
                + "WHERE\n"
                + "    nad.id_generica_nomina_autorizacion = ?1;", GenericaNominaAutorizacionDet.class);
        q.setParameter(1, idNominaAutorizacion);
        return q.getResultList();
    }

    @Override
    public List<GenericaNominaAutorizacionDet> findByIdNominaAutorizacionAndCodigoError(Integer idNominaAutorizacion, Integer codigoError) {
        String sql = "SELECT \n"
                + "    nad.*\n"
                + "FROM\n"
                + "    generica_nomina_autorizacion_det nad\n"
                + "WHERE\n"
                + "    nad.id_generica_nomina_autorizacion = ?1 AND \n"
                + "    nad.codigo_error = ?2;";

        Query q = em.createNativeQuery(sql, GenericaNominaAutorizacionDet.class);
        q.setParameter(1, idNominaAutorizacion);
        q.setParameter(2, codigoError);
        return q.getResultList();
    }

    @Override
    public Long obtenerCantidadErrores(Integer idNominaAutorizacion) {
        String sql = "SELECT count(*) FROM generica_nomina_autorizacion_det where "
                + "id_generica_nomina_autorizacion = ?1 and mensaje_error is not null;";
        Query q = em.createNativeQuery(sql);
        q.setParameter(1, idNominaAutorizacion);
        return (Long) q.getSingleResult();
    }

}
