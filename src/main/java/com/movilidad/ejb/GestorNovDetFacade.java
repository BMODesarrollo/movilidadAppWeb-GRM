/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestorNovDet;
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
public class GestorNovDetFacade extends AbstractFacade<GestorNovDet> implements GestorNovDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GestorNovDetFacade() {
        super(GestorNovDet.class);
    }

    @Override
    public List<GestorNovDet> findByIdGestorNovedad(Date desde, Date hasta) {
        try {
            String sql = "SELECT \n"
                    + "    gndt.*\n"
                    + "FROM\n"
                    + "    gestor_nov_det gndt\n"
                    + "        INNER JOIN\n"
                    + "    gestor_novedad gn ON gndt.id_gestor_novedad = gn.id_gestor_novedad\n"
                    + "WHERE\n"
                    + "    gn.fecha BETWEEN ?1 AND ?2\n"
                    + "        AND (gndt.estado_reg = 0 AND gn.estado_reg = 0);;";
            Query query = em.createNativeQuery(sql, GestorNovDet.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
