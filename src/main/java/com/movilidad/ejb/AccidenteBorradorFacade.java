/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteBorrador;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.util.Date;
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
public class AccidenteBorradorFacade extends AbstractFacade<AccidenteBorrador> implements AccidenteBorradorFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccidenteBorradorFacade() {
        super(AccidenteBorrador.class);
    }

    @Override
    public List<AccidenteBorrador> findAllEstadoReg(Date ini, Date fin) {
        try {
            String sql = "SELECT ab.* "
                    + "FROM accidente_borrador ab "
                    + "INNER JOIN accidente a "
                    + "ON ab.id_accidente = a.id_accidente "
                    + "WHERE a.fecha_acc "
                    + "BETWEEN ?1 AND ?2 "
                    + "AND (a.estado_reg = 0 OR a.estado_reg = 3) "
                    + "AND ab.estado_reg = 0;";
            Query q = em.createNativeQuery(sql, AccidenteBorrador.class);
            q.setParameter(1, ini);
            q.setParameter(2, fin);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error Facade Accidente Borrador " + e.getMessage());
            return null;
        }
    }

}
