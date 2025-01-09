/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaNominaAutorizacion;
import com.movilidad.model.NominaAutorizacion;
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
public class GenericaNominaAutorizacionFacade extends AbstractFacade<GenericaNominaAutorizacion> implements GenericaNominaAutorizacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaNominaAutorizacionFacade() {
        super(GenericaNominaAutorizacion.class);
    }

    @Override
    public List<GenericaNominaAutorizacion> findAllByRangoFechasAndArea(Date desde, Date hasta, int idParamArea) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    generica_nomina_autorizacion\n"
                    + "WHERE\n"
                    + "    ((?1 BETWEEN desde AND hasta\n"
                    + "        OR ?2 BETWEEN desde AND hasta)\n"
                    + "        OR (desde BETWEEN ?1 AND ?2\n"
                    + "        OR hasta BETWEEN ?1 AND ?2))\n"
                    + "        AND id_param_area = ?3\n"
                    + "        AND estado_reg = 0;";
            Query q = em.createNativeQuery(sql, GenericaNominaAutorizacion.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            q.setParameter(3, idParamArea);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
