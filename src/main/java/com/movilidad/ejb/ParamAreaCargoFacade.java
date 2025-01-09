/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ParamAreaCargo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author solucionesit
 */
@Stateless
public class ParamAreaCargoFacade extends AbstractFacade<ParamAreaCargo> implements ParamAreaCargoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParamAreaCargoFacade() {
        super(ParamAreaCargo.class);
    }

    @Override
    public ParamAreaCargo getByCargoArea(int idCargoEmpleado, int idParamArea) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    pac.*\n"
                    + "FROM\n"
                    + "    param_area_cargo pac\n"
                    + "WHERE\n"
                    + "    pac.id_empleado_tipo_cargo = ?1\n"
                    + "        AND pac.id_param_area = ?2\n"
                    + "LIMIT 1;", ParamAreaCargo.class);
            q.setParameter(1, idCargoEmpleado);
            q.setParameter(2, idParamArea);
            return (ParamAreaCargo) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ParamAreaCargo> findAllEstadoReg() {
        try {
            String sql = "SELECT * FROM param_area_cargo WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, ParamAreaCargo.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<ParamAreaCargo> findAllArea(Integer idArea) {
        try {
            String sql = "SELECT * FROM param_area_cargo WHERE id_param_area = ?1 AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, ParamAreaCargo.class);
            q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<ParamAreaCargo> findByUsernameAllCargos(String cUsername) {
        try {
            String sql = "SELECT pc.* \n"
                    + "FROM param_area_cargo pc\n"
                    + "INNER JOIN param_area_usr pu\n"
                    + "ON pc.id_param_area = pu.id_param_area \n"
                    + "WHERE pu.id_param_usr = (SELECT us.user_id from users us where username = ?1)\n"
                    + "AND pc.estado_reg = 0\n"
                    + "AND pu.estado_reg = 0;";
            Query q = em.createNativeQuery(sql, ParamAreaCargo.class);
            q.setParameter(1, cUsername);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public ParamAreaCargo getCargoAreaByCargo(Integer idCargoEmpleado) {
        try {
            String sql = "SELECT * "
                    + "FROM param_area_cargo "
                    + "WHERE id_empleado_tipo_cargo = ?1 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, ParamAreaCargo.class);
            q.setParameter(1, idCargoEmpleado);
            return (ParamAreaCargo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
