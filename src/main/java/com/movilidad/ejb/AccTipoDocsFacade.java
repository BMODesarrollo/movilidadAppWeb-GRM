/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccTipoDocs;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author HP
 */
@Stateless
public class AccTipoDocsFacade extends AbstractFacade<AccTipoDocs> implements AccTipoDocsFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccTipoDocsFacade() {
        super(AccTipoDocs.class);
    }

    @Override
    public List<AccTipoDocs> estadoReg() {
        try {
            Query q = em.createNativeQuery("select * from acc_tipo_docs where estado_reg = 0", AccTipoDocs.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
