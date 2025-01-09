/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoTipoEstadoDet;
import com.movilidad.model.VehiculoTipoEstadoDet;
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
public class VehiculoTipoEstadoDetFacade extends AbstractFacade<VehiculoTipoEstadoDet> implements VehiculoTipoEstadoDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VehiculoTipoEstadoDetFacade() {
        super(VehiculoTipoEstadoDet.class);
    }

    @Override
    public VehiculoTipoEstadoDet findByNombre(String nombre, Integer idRegistro, Integer idTipoEstado) {
        try {
            String sql = "SELECT * FROM vehiculo_tipo_estado_det where nombre = ?1 and id_vehiculo_tipo_estado_det <> ?2 and id_vehiculo_tipo_estado = ?3 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, VehiculoTipoEstadoDet.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);
            query.setParameter(3, idTipoEstado);

            return (VehiculoTipoEstadoDet) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<VehiculoTipoEstadoDet> findAllByEstadoReg() {
        Query query = em.createNamedQuery("VehiculoTipoEstadoDet.findByEstadoReg", VehiculoTipoEstadoDet.class);
        query.setParameter("estadoReg", 0);

        return query.getResultList();
    }

    @Override
    public List<VehiculoTipoEstadoDet> findByIdVehiculoTipoEstado(int idVehiculoTipoEstado) {
        Query query = em.createNamedQuery("VehiculoTipoEstadoDet.findByIdVehiculoTipoEstado", VehiculoTipoEstadoDet.class);
        query.setParameter("idVehiculoTipoEstado", idVehiculoTipoEstado);
        query.setParameter("estadoReg", 0);
        return query.getResultList();
    }

    @Override
    public VehiculoTipoEstadoDet findEstadoDiferir(int id) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    vehiculo_tipo_estado_det\n"
                    + "WHERE\n"
                    + "    id_vehiculo_tipo_estado_det <> ?1\n"
                    + "        AND por_defecto_diferir = 1\n"
                    + "        AND estado_reg = 0;", VehiculoTipoEstadoDet.class);
            q.setParameter(1, id);
            return (VehiculoTipoEstadoDet) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

}
