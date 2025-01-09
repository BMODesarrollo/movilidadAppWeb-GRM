/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccObjetoFijoInforme;
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
public class AccObjetoFijoInformeFacade extends AbstractFacade<AccObjetoFijoInforme> implements AccObjetoFijoInformeFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccObjetoFijoInformeFacade() {
        super(AccObjetoFijoInforme.class);
    }

    @Override
    public List<AccObjetoFijoInforme> getAccObjetoFijoInformeInformeOpe(int idAccObjetoFijoInforme) {
        try {
            Query q = em.createNativeQuery("select * from acc_objeto_fijo_informe "
                    + "where id_acc_informe_ope = ?1", AccObjetoFijoInforme.class);
            q.setParameter(1, idAccObjetoFijoInforme);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
