/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PqrFranjaHoraria;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author  Jeisson Junco
 */
@Stateless
public class PqrFranjaHorariaFacade extends AbstractFacade<PqrFranjaHoraria> implements PqrFranjaHorariaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PqrFranjaHorariaFacade() {
        super(PqrFranjaHoraria.class);
    }

    @Override
    public List<PqrFranjaHoraria> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("PqrFranjaHoraria.findByEstadoReg", PqrFranjaHoraria.class);
            query.setParameter("estadoReg", 0);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
