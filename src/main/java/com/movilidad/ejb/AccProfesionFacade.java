/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccProfesion;
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
public class AccProfesionFacade extends AbstractFacade<AccProfesion> implements AccProfesionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccProfesionFacade() {
        super(AccProfesion.class);
    }

    @Override
    public List<AccProfesion> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_profesion WHERE estado_reg = 0", AccProfesion.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Acc Profesion");
            return null;
        }
    }

}