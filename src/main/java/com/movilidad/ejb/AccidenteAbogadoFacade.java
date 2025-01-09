/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteAbogado;
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
public class AccidenteAbogadoFacade extends AbstractFacade<AccidenteAbogado> implements AccidenteAbogadoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccidenteAbogadoFacade() {
        super(AccidenteAbogado.class);
    }

    @Override
    public List<AccidenteAbogado> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM accidente_abogado WHERE estado_reg = 0", AccidenteAbogado.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
