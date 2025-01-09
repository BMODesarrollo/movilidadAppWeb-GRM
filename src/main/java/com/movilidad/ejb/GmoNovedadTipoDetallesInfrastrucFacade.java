/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GmoNovedadTipoDetallesInfrastruc;
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
public class GmoNovedadTipoDetallesInfrastrucFacade extends AbstractFacade<GmoNovedadTipoDetallesInfrastruc> implements GmoNovedadTipoDetallesInfrastrucFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GmoNovedadTipoDetallesInfrastrucFacade() {
        super(GmoNovedadTipoDetallesInfrastruc.class);
    }

    @Override
    public GmoNovedadTipoDetallesInfrastruc findByNombre(String nombre, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM gmo_novedad_tipo_detalles_infrastruc where nombre = ?1 and id_gmo_novedad_tipo_det_infrastruc <> ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, GmoNovedadTipoDetallesInfrastruc.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);
            return (GmoNovedadTipoDetallesInfrastruc) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GmoNovedadTipoDetallesInfrastruc> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("GmoNovedadTipoDetallesInfrastruc.findByEstadoReg", GmoNovedadTipoDetallesInfrastruc.class);
            query.setParameter("estadoReg", 0);
            return query.getResultList();
        } catch (Exception e) {

            return new ArrayList<>();
        }
    }

}
