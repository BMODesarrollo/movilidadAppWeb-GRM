/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.EmpleadoDepartamento;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Soluciones IT
 */
@Stateless
public class EmpleadoDepartamentoFacade extends AbstractFacade<EmpleadoDepartamento> implements EmpleadoDepartamentoFacadeLocal {
    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpleadoDepartamentoFacade() {
        super(EmpleadoDepartamento.class);
    }
    
}
