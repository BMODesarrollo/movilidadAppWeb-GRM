/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestorNovReqDet;
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
public class GestorNovReqDetFacade extends AbstractFacade<GestorNovReqDet> implements GestorNovReqDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GestorNovReqDetFacade() {
        super(GestorNovReqDet.class);
    }

    @Override
    public List<GestorNovReqDet> findAll(Date desde, Date hasta) {
        try {
            String sql = "SELECT \n"
                    + "    gd.*\n"
                    + "FROM\n"
                    + "    gestor_nov_req_det gd\n"
                    + "        INNER JOIN\n"
                    + "    gestor_novedad gn ON gd.id_gestor_novedad = gn.id_gestor_novedad\n"
                    + "WHERE\n"
                    + "    gn.fecha BETWEEN ?1 AND ?2\n"
                    + "        AND (gd.estado_reg = 0 AND gn.estado_reg = 0);";
            Query query = em.createNativeQuery(sql, GestorNovReqDet.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void llenarTablaSemanal() {
        try {
            Query q1 = em.createNativeQuery("{call spGestorNovReqDet()}");
            q1.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
