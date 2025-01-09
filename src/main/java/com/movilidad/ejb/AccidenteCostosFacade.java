/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteCostos;
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
public class AccidenteCostosFacade extends AbstractFacade<AccidenteCostos> implements AccidenteCostosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccidenteCostosFacade() {
        super(AccidenteCostos.class);
    }

    @Override
    public List<AccidenteCostos> estadoReg(int i_tipoCosto, int i_idAccidente) {
        try {
            Query q = em.createNativeQuery("SELECT accidente_costos.* FROM accidente_costos "
                    + "INNER JOIN acc_tipo_costos "
                    + "WHERE acc_tipo_costos.id_acc_tipo_costos = accidente_costos.id_acc_tipo_costos "
                    + "AND acc_tipo_costos.estado_reg = 0 "
                    + "AND acc_tipo_costos.directo = ?1 AND accidente_costos.id_accidente = ?2 "
                    + "AND accidente_costos.estado_reg = 0;", AccidenteCostos.class);
            q.setParameter(1, i_tipoCosto);
            q.setParameter(2, i_idAccidente);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Accidente Costos");
            return null;
        }
    }

}
