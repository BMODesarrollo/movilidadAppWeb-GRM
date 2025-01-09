/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccDesplazaA;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class AccDesplazaAFacade extends AbstractFacade<AccDesplazaA> implements AccDesplazaAFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccDesplazaAFacade() {
        super(AccDesplazaA.class);
    }

    @Override
    public List<AccDesplazaA> findByEstadoReg() {
        try {
            Query query = em.createNamedQuery("AccDesplazaA.findByEstadoReg",AccDesplazaA.class);
            query.setParameter("estadoReg", 0);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public AccDesplazaA findByNombre(String nombre, Integer id) {
        try {
            String sql = "SELECT * FROM acc_desplaza_a where nombre = ?1 and id_acc_desplaza_a <> ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, AccDesplazaA.class);
            query.setParameter(1, nombre);
            query.setParameter(2, id);

            return (AccDesplazaA) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}
