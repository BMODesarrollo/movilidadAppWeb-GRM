/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadDanoLiq;
import com.movilidad.model.VehiculoDanoCosto;
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
public class NovedadDanoLiqFacade extends AbstractFacade<NovedadDanoLiq> implements NovedadDanoLiqFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadDanoLiqFacade() {
        super(NovedadDanoLiq.class);
    }

    @Override
    public List<NovedadDanoLiq> findByRangoFechas(Date desde, Date hasta, int idGopUnidadFunc) {
        String sql = "SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    novedad_dano_liq\n"
                + "WHERE\n"
                + "    ((?1 BETWEEN desde AND hasta\n"
                + "        OR ?2 BETWEEN desde AND hasta)\n"
                + "        OR (desde BETWEEN ?1 AND ?2\n"
                + "        OR hasta BETWEEN ?1 AND ?2))\n"
                + "        AND estado_reg = 0\n"
                + "        AND id_gop_unidad_funcional = ?3;";
        Query query = em.createNativeQuery(sql, NovedadDanoLiq.class);
        query.setParameter(1, Util.dateFormat(desde));
        query.setParameter(2, Util.dateFormat(hasta));
        query.setParameter(3, idGopUnidadFunc);

        return query.getResultList();
    }

}
