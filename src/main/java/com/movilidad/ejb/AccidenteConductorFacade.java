/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteConductor;
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
public class AccidenteConductorFacade extends AbstractFacade<AccidenteConductor> implements AccidenteConductorFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccidenteConductorFacade() {
        super(AccidenteConductor.class);
    }

    @Override
    public List<AccidenteConductor> estadoReg(int i_idAccidente) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM accidente_conductor "
                    + "WHERE id_accidente = ?1 AND estado_reg = 0", AccidenteConductor.class);
            q.setParameter(1, i_idAccidente);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Accidente Conductor Facade Local");
            return null;
        }
    }

    @Override
    public AccidenteConductor findByVehiculo(int idAccidente, int idAccidenteVehiculo, String cedula) {
        try {
            String sql = "select * from accidente_conductor where id_accidente = ?1 and id_accidente_vehiculo = ?2 and cedula = ?3;";
            Query query = em.createNativeQuery(sql, AccidenteConductor.class);
            query.setParameter(1, idAccidente);
            query.setParameter(2, idAccidenteVehiculo);
            query.setParameter(3, cedula);
            return (AccidenteConductor) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
