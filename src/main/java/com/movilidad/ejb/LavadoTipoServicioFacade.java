/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.LavadoTipoServicio;
import java.util.ArrayList;
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
public class LavadoTipoServicioFacade extends AbstractFacade<LavadoTipoServicio> implements LavadoTipoServicioFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LavadoTipoServicioFacade() {
        super(LavadoTipoServicio.class);
    }

    @Override
    public LavadoTipoServicio findByNombreAndTipoVehiculo(Integer idRegistro, String nombre, Integer idVehiculoTipo) {
        try {
            String sql = "SELECT * FROM lavado_tipo_servicio where "
                    + "id_lavado_tipo_servicio <> ?1 and \n"
                    + "id_vehiculo_tipo = ?3 and \n"
                    + "nombre = ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, LavadoTipoServicio.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, nombre);
            query.setParameter(3, idVehiculoTipo);

            return (LavadoTipoServicio) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<LavadoTipoServicio> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("LavadoTipoServicio.findByEstadoReg", LavadoTipoServicio.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public LavadoTipoServicio findByNombre(Integer idRegistro, String nombre) {
        try {
            String sql = "SELECT * FROM lavado_tipo_servicio where "
                    + "id_lavado_tipo_servicio <> ?1 and \n"
                    + "nombre = ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, LavadoTipoServicio.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, nombre);

            return (LavadoTipoServicio) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
