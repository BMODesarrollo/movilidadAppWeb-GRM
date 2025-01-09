/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccVisualInforme;
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
public class AccVisualInformeFacade extends AbstractFacade<AccVisualInforme> implements AccVisualInformeFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccVisualInformeFacade() {
        super(AccVisualInforme.class);
    }

    @Override
    public List<AccVisualInforme> getAccVisualInformeOpe(int idAccInformeOpe) {
        try {
            Query q = em.createNativeQuery("select * from acc_visual_informe "
                    + "where id_acc_informe_ope = ?1", AccVisualInforme.class);
            q.setParameter(1, idAccInformeOpe);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
