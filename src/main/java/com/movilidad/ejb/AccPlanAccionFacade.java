/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccPlanAccion;
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
public class AccPlanAccionFacade extends AbstractFacade<AccPlanAccion> implements AccPlanAccionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccPlanAccionFacade() {
        super(AccPlanAccion.class);
    }

    @Override
    public List<AccPlanAccion> findAll() {
        try {
            String sql = "select * from acc_plan_accion where estado_reg = 0;";

            Query query = em.createNativeQuery(sql, AccPlanAccion.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AccPlanAccion findByPlan(String plan) {
        try {
            String sql = "select * from acc_plan_accion where plan = ?1;";

            Query query = em.createNativeQuery(sql, AccPlanAccion.class);
            query.setParameter(1, plan);
            return (AccPlanAccion) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
