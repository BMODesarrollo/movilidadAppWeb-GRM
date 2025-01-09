/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteVictima;
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
public class AccidenteVictimaFacade extends AbstractFacade<AccidenteVictima> implements AccidenteVictimaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccidenteVictimaFacade() {
        super(AccidenteVictima.class);
    }

    @Override
    public List<AccidenteVictima> estadoReg(int i_idAccidente) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM accidente_victima "
                    + "WHERE id_accidente = ?1 AND estado_reg = 0", AccidenteVictima.class);
            q.setParameter(1, i_idAccidente);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Accidente Victima Facade");
            return null;
        }
    }

    @Override
    public AccidenteVictima findAccidenteVictimaByCedulaAndIdAcc(String cedula, Integer idAccidente) {
        try {
            String sql = "SELECT * "
                    + "FROM accidente_victima "
                    + "WHERE id_accidente = ?1 "
                    + "AND cedula = ?2 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, AccidenteVictima.class);
            q.setParameter(1, idAccidente);
            q.setParameter(2, cedula);
            return (AccidenteVictima) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
