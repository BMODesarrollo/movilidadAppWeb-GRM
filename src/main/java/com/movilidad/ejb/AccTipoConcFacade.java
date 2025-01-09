/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccTipoConc;
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
public class AccTipoConcFacade extends AbstractFacade<AccTipoConc> implements AccTipoConcFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccTipoConcFacade() {
        super(AccTipoConc.class);
    }

    @Override
    public List<AccTipoConc> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_tipo_conc WHERE estado_reg = 0", AccTipoConc.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Tipo Conc");
            return null;
        }
    }

}
