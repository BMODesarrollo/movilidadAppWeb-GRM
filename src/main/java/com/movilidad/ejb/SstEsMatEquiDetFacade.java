/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstEsMatEquiDet;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class SstEsMatEquiDetFacade extends AbstractFacade<SstEsMatEquiDet> implements SstEsMatEquiDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SstEsMatEquiDetFacade() {
        super(SstEsMatEquiDet.class);
    }

    @Override
    public void removeDetallesByEsMatEquiId(Integer sstEsMatEquiId) {
        try {
            String sql = "delete from sst_es_mat_equi_det where id_sst_es_mat_equi = ?1;";

            Query query = em.createNativeQuery(sql);
            query.setParameter(1, sstEsMatEquiId);

            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
