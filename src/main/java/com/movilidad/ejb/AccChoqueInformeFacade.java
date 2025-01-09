/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccChoqueInforme;
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
public class AccChoqueInformeFacade extends AbstractFacade<AccChoqueInforme> implements AccChoqueInformeFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccChoqueInformeFacade() {
        super(AccChoqueInforme.class);
    }

    @Override
    public List<AccChoqueInforme> getAccChoqueInformeOpe(int idAccChoqueInforme) {
        try {
            Query q = em.createNativeQuery("select * from acc_choque_informe "
                    + "where id_acc_informe_ope = ?1", AccChoqueInforme.class);
            q.setParameter(1, idAccChoqueInforme);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
