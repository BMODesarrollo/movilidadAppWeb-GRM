/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.EmpleadoEmpleador;
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
public class EmpleadoEmpleadorFacade extends AbstractFacade<EmpleadoEmpleador> implements EmpleadoEmpleadorFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpleadoEmpleadorFacade() {
        super(EmpleadoEmpleador.class);
    }

    @Override
    public List<EmpleadoEmpleador> findAllEstadoReg() {
        try {
            String sql = "SELECT * FROM empleado_empleador WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, EmpleadoEmpleador.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

}
