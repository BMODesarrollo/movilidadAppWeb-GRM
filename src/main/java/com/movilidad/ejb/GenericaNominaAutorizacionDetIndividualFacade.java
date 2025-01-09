/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaNominaAutorizacionDetIndividual;
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
public class GenericaNominaAutorizacionDetIndividualFacade extends AbstractFacade<GenericaNominaAutorizacionDetIndividual> implements GenericaNominaAutorizacionDetIndividualFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaNominaAutorizacionDetIndividualFacade() {
        super(GenericaNominaAutorizacionDetIndividual.class);
    }

    @Override
    public List<GenericaNominaAutorizacionDetIndividual> findByIdNominaAutorizacion(Integer idNominaAutorizacion) {
         try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    nad.*\n"
                    + "FROM\n"
                    + "    generica_nomina_autorizacion_det_individual nad\n"
                    + "WHERE\n"
                    + "    nad.id_generica_nomina_autorizacion_individual = ?1;", GenericaNominaAutorizacionDetIndividual.class);
            q.setParameter(1, idNominaAutorizacion);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GenericaNominaAutorizacionDetIndividual> findByIdNominaAutorizacionAndCodigoError(Integer idNominaAutorizacion, Integer codigoError) {
                String sql = "SELECT \n"
                + "    nad.*\n"
                + "FROM\n"
                + "    generica_nomina_autorizacion_det_individual nad\n"
                + "WHERE\n"
                + "    nad.id_generica_nomina_autorizacion_individual = ?1 AND \n"
                + "    nad.codigo_error = ?2;";

        Query q = em.createNativeQuery(sql, GenericaNominaAutorizacionDetIndividual.class);
        q.setParameter(1, idNominaAutorizacion);
        q.setParameter(2, codigoError);
        return q.getResultList();
    }

    @Override
    public Long obtenerCantidadErrores(Integer idNominaAutorizacion) {
        String sql = "SELECT count(*) FROM generica_nomina_autorizacion_det_individual where "
                + "id_generica_nomina_autorizacion_individual = ?1 and mensaje_error is not null;";
        Query q = em.createNativeQuery(sql);
        q.setParameter(1, idNominaAutorizacion);
        return (Long) q.getSingleResult();
    }
    
}
