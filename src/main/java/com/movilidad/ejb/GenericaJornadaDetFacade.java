/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaJornadaDet;
import com.movilidad.utils.Util;
import java.util.Date;
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
public class GenericaJornadaDetFacade extends AbstractFacade<GenericaJornadaDet> implements GenericaJornadaDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaJornadaDetFacade() {
        super(GenericaJornadaDet.class);
    }

    @Override
    public List<GenericaJornadaDet> findByRangoFecha(Date desde, Date hasta) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    gjd.*\n"
                + "FROM\n"
                + "    generica_jornada_det gjd\n"
                + "        INNER JOIN\n"
                + "    generica_jornada gj ON gj.id_generica_jornada = gjd.id_generica_jornada\n"
                + "        INNER JOIN\n"
                + "    empleado e ON e.id_empleado = gj.id_empleado\n"
                + "WHERE\n"
                + "    gj.fecha BETWEEN ?1 AND ?2\n"
                + "        AND gj.estado_reg = 0\n"
                + "        AND gjd.estado_reg = 0\n"
                + "ORDER BY e.identificacion ASC , gj.fecha ASC, gjd.timeorigin ASC;", GenericaJornadaDet.class);
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        return q.getResultList();
    }
}
