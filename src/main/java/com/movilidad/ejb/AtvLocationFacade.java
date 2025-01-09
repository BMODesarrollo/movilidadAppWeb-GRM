/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvLocation;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author soluciones-it
 */
@Stateless
public class AtvLocationFacade extends AbstractFacade<AtvLocation> implements AtvLocationFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AtvLocationFacade() {
        super(AtvLocation.class);
    }

    @Override
    public List<AtvLocation> findByIdNovedadAndInicioBetweenFin(Integer idNovedad, Date inicio, Date fin) {
        String sql = "SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    atv_location\n"
                + "WHERE\n"
                + "    id_novedad = ?1 \n"
                + "        AND fecha BETWEEN ?2 AND ?3 \n"
                + "ORDER BY fecha ASC";
        Query q = em.createNativeQuery(sql, AtvLocation.class);
        q.setParameter(1, idNovedad);
        q.setParameter(2, Util.dateTimeFormat(inicio));
        q.setParameter(3, Util.dateTimeFormat(fin));
        return q.getResultList();
    }

}
