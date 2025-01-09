/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaNominaAutorizacionIndividual;
import com.movilidad.utils.Util;
import java.util.Date;
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
public class GenericaNominaAutorizacionIndividualFacade extends AbstractFacade<GenericaNominaAutorizacionIndividual> implements GenericaNominaAutorizacionIndividualFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaNominaAutorizacionIndividualFacade() {
        super(GenericaNominaAutorizacionIndividual.class);
    }

    @Override
    public List<GenericaNominaAutorizacionIndividual> findAllByRangoFechasAndAreaAndEmpleado(Date desde, Date hasta, int idParamArea, int idEmpleado) {
        try {
            String sql_empleado = idEmpleado == 0 ? "" : "        AND id_empleado = ?4\n";
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    generica_nomina_autorizacion_individual\n"
                    + "WHERE\n"
                    + "    ((?1 BETWEEN desde AND hasta\n"
                    + "        OR ?2 BETWEEN desde AND hasta)\n"
                    + "        OR (desde BETWEEN ?1 AND ?2\n"
                    + "        OR hasta BETWEEN ?1 AND ?2))\n"
                    + "        AND id_param_area = ?3\n"
                    + sql_empleado
                    + "        AND estado_reg = 0;";
            Query q = em.createNativeQuery(sql, GenericaNominaAutorizacionIndividual.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            q.setParameter(3, idParamArea);
            q.setParameter(4, idEmpleado);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public GenericaNominaAutorizacionIndividual verificarRegistro(Date desde, Date hasta, int idParamArea, int idEmpleado) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    generica_nomina_autorizacion_individual\n"
                    + "WHERE\n"
                    + "    ((?1 BETWEEN desde AND hasta\n"
                    + "        OR ?2 BETWEEN desde AND hasta)\n"
                    + "        OR (desde BETWEEN ?1 AND ?2\n"
                    + "        OR hasta BETWEEN ?1 AND ?2))\n"
                    + "        AND id_param_area = ?3\n"
                    + "        AND id_empleado = ?4\n"
                    + "        AND estado_reg = 0 LIMIT 1;";
            Query q = em.createNativeQuery(sql, GenericaNominaAutorizacionIndividual.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            q.setParameter(3, idParamArea);
            q.setParameter(4, idEmpleado);
            return (GenericaNominaAutorizacionIndividual) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
