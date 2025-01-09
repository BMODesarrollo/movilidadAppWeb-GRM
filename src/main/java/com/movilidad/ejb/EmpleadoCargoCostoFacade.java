/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.EmpleadoCargoCosto;
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
public class EmpleadoCargoCostoFacade extends AbstractFacade<EmpleadoCargoCosto> implements EmpleadoCargoCostoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpleadoCargoCostoFacade() {
        super(EmpleadoCargoCosto.class);
    }

    @Override
    public List<EmpleadoCargoCosto> findForDateAndCargo(Date d, Integer idCargo) {
        try {
            String sql = "SELECT ce.* "
                    + "FROM empleado_cargo_costo ce "
                    + "WHERE ?1 "
                    + "BETWEEN ce.desde AND ce.hasta "
                    + "AND ce.id_empleado_tipo_cargo = ?2 "
                    + "AND ce.estado_reg = 0";
            Query q = em.createNativeQuery(sql, EmpleadoCargoCosto.class);
            q.setParameter(1, d);
            q.setParameter(2, idCargo);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<EmpleadoCargoCosto> findMaxDateHasta(Integer idCargo) {
        try {
            String sql = "SELECT ce.* "
                    + "FROM empleado_cargo_costo ce "
                    + "WHERE ce.id_empleado_tipo_cargo = ?1 "
                    + "AND ce.estado_reg = 0 "
                    + "ORDER BY ce.hasta DESC "
                    + "LIMIT 1";
            Query q = em.createNativeQuery(sql, EmpleadoCargoCosto.class);
            q.setParameter(1, idCargo);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<EmpleadoCargoCosto> findAllEstadoReg() {
        try {
            String sql = "SELECT ce.* FROM empleado_cargo_costo ce WHERE  ce.estado_reg = 0";
            Query q = em.createNativeQuery(sql, EmpleadoCargoCosto.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<EmpleadoCargoCosto> findAllRangoFechaEstadoReg(Date desde, Date hasta) {
        try {
            desde = MovilidadUtil.dateSinHora(desde);
            hasta = MovilidadUtil.dateSinHora(hasta);
            String sql = "SELECT ce.* "
                    + "FROM empleado_cargo_costo ce "
                    + "WHERE ce.desde >= ?1 AND ce.hasta <= ?2 "
                    + "AND ce.estado_reg = 0 "
                    + "ORDER BY ce.desde";
            Query q = em.createNativeQuery(sql, EmpleadoCargoCosto.class);
            q.setParameter(1, desde);
            q.setParameter(2, hasta);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<EmpleadoCargoCosto> findAllByAreaEstadoReg(Integer idArea) {
        try {
            String sql = "SELECT * "
                    + "FROM empleado_cargo_costo e "
                    + "INNER JOIN param_area_cargo p "
                    + "ON e.id_empleado_tipo_cargo = p.id_empleado_tipo_cargo "
                    + "WHERE p.id_param_area = ?1 "
                    + "AND p.estado_reg = 0 "
                    + "AND e.estado_reg = 0";
            Query q = em.createNativeQuery(sql, EmpleadoCargoCosto.class);
            q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public EmpleadoCargoCosto findByFechaYTipoCargo(Date fecha, int idTipoCargo) {
        try {
            String sql = "SELECT\n"
                    + "	ce.*\n"
                    + "FROM\n"
                    + "	empleado_cargo_costo ce\n"
                    + "WHERE\n"
                    + "	?1 BETWEEN ce.desde AND ce.hasta\n"
                    + "	AND ce.id_empleado_tipo_cargo = ?2\n"
                    + "	AND ce.estado_reg = 0";
            Query q = em.createNativeQuery(sql, EmpleadoCargoCosto.class);
            q.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
            q.setParameter(2, idTipoCargo);
            return (EmpleadoCargoCosto) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
