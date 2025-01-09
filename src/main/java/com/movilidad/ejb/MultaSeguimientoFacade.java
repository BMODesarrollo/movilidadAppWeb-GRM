/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MultaSeguimiento;
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
public class MultaSeguimientoFacade extends AbstractFacade<MultaSeguimiento> implements MultaSeguimientoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MultaSeguimientoFacade() {
        super(MultaSeguimiento.class);
    }

    @Override
    public List<MultaSeguimiento> obtenerMS(int i) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM multa_seguimiento WHERE id_multa = ? "
                    + "AND estado_reg = 0" , MultaSeguimiento.class)
                    .setParameter(1, i);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<MultaSeguimiento> estadoRegistro(){
            try {
            Query q = em.createQuery("SELECT m FROM MultaSeguimiento m WHERE m.estadoReg = :estadoReg", MultaSeguimiento.class)
                    .setParameter("estadoReg", 0);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
