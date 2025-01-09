/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoTipoEstado;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author USUARIO
 */
@Stateless
public class VehiculoTipoEstadoFacade extends AbstractFacade<VehiculoTipoEstado> implements VehiculoTipoEstadoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VehiculoTipoEstadoFacade() {
        super(VehiculoTipoEstado.class);
    }

    @Override
    public List<VehiculoTipoEstado> findByEstadoReg() {
        Query q = em.createNamedQuery("VehiculoTipoEstado.findByEstadoReg", VehiculoTipoEstado.class);
        q.setParameter("estadoReg", 0);
        return q.getResultList();
    }

    @Override
    public VehiculoTipoEstado findEstadoCierraNovedad(int id) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    vehiculo_tipo_estado\n"
                    + "WHERE\n"
                    + "    id_vehiculo_tipo_estado <> ?1\n"
                    + "        AND cierra_novedad = 1\n"
                    + "        AND estado_reg = 0;", VehiculoTipoEstado.class);
            q.setParameter(1, id);
            return (VehiculoTipoEstado) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
}
