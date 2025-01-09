/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Empleado;
import com.movilidad.model.Lavado;
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
public class LavadoFacade extends AbstractFacade<Lavado> implements LavadoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LavadoFacade() {
        super(Lavado.class);
    }

    @Override
    public List<Lavado> findAll() {
        try {
            String sql = "select * from lavado where estado_reg = 0 order by fecha_hora desc;";

            Query query = em.createNativeQuery(sql, Lavado.class);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<Empleado> findEmployee() {
        try {
            String sql = "SELECT * FROM empleado WHERE id_empleado_cargo IN (1050,1051,1052,1062);";

            Query query = em.createNativeQuery(sql, Empleado.class);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<String> getNameListEmployee(){
        try {
            String sql = "SELECT concat(e.nombres,' ',e.apellidos) FROM empleado e WHERE e.id_empleado_cargo IN (1050,1051,1052,1062);";

            Query query = em.createNativeQuery(sql);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
