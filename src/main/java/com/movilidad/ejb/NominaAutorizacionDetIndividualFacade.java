/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NominaAutorizacionDetIndividual;
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
public class NominaAutorizacionDetIndividualFacade extends AbstractFacade<NominaAutorizacionDetIndividual> implements NominaAutorizacionDetIndividualFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NominaAutorizacionDetIndividualFacade() {
        super(NominaAutorizacionDetIndividual.class);
    }

    @Override
    public List<NominaAutorizacionDetIndividual> findByIdNominaAutorizacion(Integer idNominaAutorizacion) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    nad.*\n"
                    + "FROM\n"
                    + "    nomina_autorizacion_det_individual nad\n"
                    + "WHERE\n"
                    + "    nad.id_nomina_autorizacion_individual = ?1;", NominaAutorizacionDetIndividual.class);
            q.setParameter(1, idNominaAutorizacion);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<NominaAutorizacionDetIndividual> findByIdNominaAutorizacionAndCodigoError(Integer idNominaAutorizacion, Integer codigoError) {
        String sql = "SELECT \n"
                + "    nad.*\n"
                + "FROM\n"
                + "    nomina_autorizacion_det_individual nad\n"
                + "WHERE\n"
                + "    nad.id_nomina_autorizacion_individual = ?1 AND \n"
                + "    nad.codigo_error = ?2;";

        Query q = em.createNativeQuery(sql, NominaAutorizacionDetIndividual.class);
        q.setParameter(1, idNominaAutorizacion);
        q.setParameter(2, codigoError);
        return q.getResultList();
    }

    @Override
    public Long obtenerCantidadErrores(Integer idNominaAutorizacion) {
        String sql = "SELECT count(*) FROM nomina_autorizacion_det_individual where "
                + "id_nomina_autorizacion_individual = ?1 and mensaje_error is not null;";
        Query q = em.createNativeQuery(sql);
        q.setParameter(1, idNominaAutorizacion);
        return (Long) q.getSingleResult();
    }

}
