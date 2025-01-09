/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.EmpleadoTipoCargo;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.ObjetoSigleton;
import com.movilidad.utils.SingletonConfigEmpresa;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Soluciones IT
 */
@Stateless
public class EmpleadoTipoCargoFacade extends AbstractFacade<EmpleadoTipoCargo> implements EmpleadoTipoCargoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpleadoTipoCargoFacade() {
        super(EmpleadoTipoCargo.class);
    }

    @Override
    public EmpleadoTipoCargo findByNombre(String value, int id) {
        try {
            if (id == 0) {
                TypedQuery<EmpleadoTipoCargo> query = em.createQuery(
                        "SELECT e FROM EmpleadoTipoCargo e WHERE e.nombreCargo= ?1",
                        EmpleadoTipoCargo.class);
                return query.setParameter(1, value).getSingleResult();
            }
            TypedQuery<EmpleadoTipoCargo> query = em.createQuery(
                    "SELECT e FROM EmpleadoTipoCargo e WHERE e.nombreCargo= ?1 AND"
                    + " e.idEmpleadoTipoCargo<>?2", EmpleadoTipoCargo.class);
            query.setParameter(1, value);
            query.setParameter(2, id);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<EmpleadoTipoCargo> findAllActivos() {
        TypedQuery<EmpleadoTipoCargo> query = em.createQuery(
                "SELECT e FROM EmpleadoTipoCargo e WHERE e.estadoReg= ?1",
                EmpleadoTipoCargo.class);
        query.setParameter(1, 0);
        return query.getResultList();
    }

    @Override
    public EmpleadoTipoCargo cargar(int i) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM empleado_tipo_cargo "
                    + "where id_empleado_tipo_cargo = ?", EmpleadoTipoCargo.class)
                    .setParameter(1, i);
            return (EmpleadoTipoCargo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<EmpleadoTipoCargo> getCargosPorArea(int idArea) {
        try {
            Query q = em.createNativeQuery("select etc.* \n"
                    + "from empleado_tipo_cargo etc\n"
                    + "inner join param_area_cargo pac \n"
                    + "on etc.id_empleado_tipo_cargo = pac.id_empleado_tipo_cargo\n"
                    + "where etc.estado_reg = 0\n"
                    + "and pac.id_param_area = ?1\n"
                    + "and pac.estado_reg = 0;", EmpleadoTipoCargo.class);
            q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<EmpleadoTipoCargo> obtenerCargosOperadores() {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    empleado_tipo_cargo\n"
                    + "WHERE\n"
                    + "    id_empleado_tipo_cargo IN (" + SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_CARGOS_OPERADORES) + ")\n"
                    + "        AND estado_reg = 0;", EmpleadoTipoCargo.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
