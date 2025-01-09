/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstTipoLabor;
import java.util.ArrayList;
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
public class SstTipoLaborFacade extends AbstractFacade<SstTipoLabor> implements SstTipoLaborFacadeLocal {
    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SstTipoLaborFacade() {
        super(SstTipoLabor.class);
    }

    @Override
    public List<SstTipoLabor> findAllEstadoReg() {
        try {
            Query query = em.createNamedQuery("SstTipoLabor.findByEstadoReg", SstTipoLabor.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public SstTipoLabor findByTipoLabor(String tipo) {
        try {
            String sql = "select * from sst_tipo_labor where tipo_labor = ?1 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, SstTipoLabor.class);
            query.setParameter(1, tipo);

            return (SstTipoLabor) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}
