/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoDano;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author USUARIO
 */
@Stateless
public class VehiculoDanoFacade extends AbstractFacade<VehiculoDano> implements VehiculoDanoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VehiculoDanoFacade() {
        super(VehiculoDano.class);
    }

    @Override
    public List<VehiculoDano> findAll() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM vehiculo_dano where estado_reg = 0", VehiculoDano.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
