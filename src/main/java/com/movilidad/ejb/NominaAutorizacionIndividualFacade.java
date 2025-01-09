/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NominaAutorizacionIndividual;
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
public class NominaAutorizacionIndividualFacade extends AbstractFacade<NominaAutorizacionIndividual> implements NominaAutorizacionIndividualFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NominaAutorizacionIndividualFacade() {
        super(NominaAutorizacionIndividual.class);
    }

    @Override
    public List<NominaAutorizacionIndividual> findAllByRangoFechasAndUF(Date desde, Date hasta, int idGopUnidadFuncional, int idEmpleado) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?3\n";
            String sql_empleado = idEmpleado == 0 ? "" : "       AND id_empleado = ?4\n";

            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    nomina_autorizacion_individual\n"
                    + "WHERE\n"
                    + "    ((?1 BETWEEN desde AND hasta\n"
                    + "        OR ?2 BETWEEN desde AND hasta)\n"
                    + "        OR (desde BETWEEN ?1 AND ?2\n"
                    + "        OR hasta BETWEEN ?1 AND ?2))\n"
                    + sql_unida_func
                    + sql_empleado
                    + "        AND estado_reg = 0;";
            Query q = em.createNativeQuery(sql, NominaAutorizacionIndividual.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            q.setParameter(3, idGopUnidadFuncional);
            q.setParameter(4, idEmpleado);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public NominaAutorizacionIndividual findAllByRangoFechasAndUFAndOperador(Date desde, Date hasta, int idGopUnidadFuncional, int idEmpleado) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?3\n";
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    nomina_autorizacion_individual\n"
                    + "WHERE\n"
                    + "    ((?1 BETWEEN desde AND hasta\n"
                    + "        OR ?2 BETWEEN desde AND hasta)\n"
                    + "        OR (desde BETWEEN ?1 AND ?2\n"
                    + "        OR hasta BETWEEN ?1 AND ?2))\n"
                    + sql_unida_func
                    + "        AND id_empleado = ?4 "
                    + "        AND estado_reg = 0 LIMIT 1;";
            Query q = em.createNativeQuery(sql, NominaAutorizacionIndividual.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            q.setParameter(3, idGopUnidadFuncional);
            q.setParameter(4, idEmpleado);
            return (NominaAutorizacionIndividual) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
