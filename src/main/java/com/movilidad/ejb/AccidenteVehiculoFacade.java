/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteVehiculo;
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
public class AccidenteVehiculoFacade extends AbstractFacade<AccidenteVehiculo> implements AccidenteVehiculoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccidenteVehiculoFacade() {
        super(AccidenteVehiculo.class);
    }

    @Override
    public List<AccidenteVehiculo> estadoReg(int id_accidente) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM accidente_vehiculo "
                    + "WHERE id_accidente = ?1 AND estado_reg = 0", AccidenteVehiculo.class);
            q.setParameter(1, id_accidente);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Accidente Vehículo Facade Local");
            return null;
        }
    }

    @Override
    public AccidenteVehiculo findByVehiculo(int idAccidente, String codigoVehiculo) {
        try {
            String sql = "select * from accidente_vehiculo where id_accidente = ?1 "
                    + "and codigo_vehiculo = ?2";
            Query query = em.createNativeQuery(sql,AccidenteVehiculo.class);
            query.setParameter(1, idAccidente);
            query.setParameter(2, codigoVehiculo);
            return (AccidenteVehiculo) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
