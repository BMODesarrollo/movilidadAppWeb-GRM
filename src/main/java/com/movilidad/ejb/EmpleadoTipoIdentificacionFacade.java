/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.EmpleadoTipoIdentificacion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Soluciones IT
 */
@Stateless
public class EmpleadoTipoIdentificacionFacade extends AbstractFacade<EmpleadoTipoIdentificacion> implements EmpleadoTipoIdentificacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpleadoTipoIdentificacionFacade() {
        super(EmpleadoTipoIdentificacion.class);
    }

    @Override
    public EmpleadoTipoIdentificacion findByNombre(String value, int id) {
        try {
            if (id == 0) {
                TypedQuery<EmpleadoTipoIdentificacion> query = em.createQuery(
                        "SELECT e FROM EmpleadoTipoIdentificacion e WHERE e.nombre= ?1", EmpleadoTipoIdentificacion.class);
                return query.setParameter(1, value).getSingleResult();
            }
            TypedQuery<EmpleadoTipoIdentificacion> query = em.createQuery(
                    "SELECT e FROM EmpleadoTipoIdentificacion e WHERE e.nombre= ?1 AND e.idEmpleadoTipoIdentificacion<>?2", EmpleadoTipoIdentificacion.class);
            query.setParameter(1, value);
            query.setParameter(2, id);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<EmpleadoTipoIdentificacion> findAllActivos() {

        TypedQuery<EmpleadoTipoIdentificacion> query = em.createQuery(
                "SELECT e FROM EmpleadoTipoIdentificacion e WHERE e.estadoReg= ?1",
                EmpleadoTipoIdentificacion.class);
        query.setParameter(1, 0);
        return query.getResultList();
    }

}
