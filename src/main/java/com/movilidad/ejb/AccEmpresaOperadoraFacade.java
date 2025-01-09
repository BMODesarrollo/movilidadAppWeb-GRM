/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccEmpresaOperadora;
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
public class AccEmpresaOperadoraFacade extends AbstractFacade<AccEmpresaOperadora> implements AccEmpresaOperadoraFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccEmpresaOperadoraFacade() {
        super(AccEmpresaOperadora.class);
    }

    @Override
    public List<AccEmpresaOperadora> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_empresa_operadora WHERE estado_reg = 0", AccEmpresaOperadora.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Empresa Operadora");
            return null;
        }
    }

}
