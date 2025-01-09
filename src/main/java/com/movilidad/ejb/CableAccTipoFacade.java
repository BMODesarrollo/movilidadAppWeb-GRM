/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccTipo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cesar
 */
@Stateless
public class CableAccTipoFacade extends AbstractFacade<CableAccTipo> implements CableAccTipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableAccTipoFacade() {
        super(CableAccTipo.class);
    }

    @Override
    public List<CableAccTipo> findAllEstadoReg() {
        try {
            String sql = "SELECT * "
                    + "FROM cable_acc_tipo "
                    + "WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, CableAccTipo.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
