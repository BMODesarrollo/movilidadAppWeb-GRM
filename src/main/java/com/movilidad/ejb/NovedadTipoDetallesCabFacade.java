/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadTipoDetallesCab;
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
public class NovedadTipoDetallesCabFacade extends AbstractFacade<NovedadTipoDetallesCab> implements NovedadTipoDetallesCabFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadTipoDetallesCabFacade() {
        super(NovedadTipoDetallesCab.class);
    }

    @Override
    public NovedadTipoDetallesCab findByNombre(String nombre, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM novedad_tipo_detalles_cab where nombre = ?1 and id_novedad_tipo_det_cab <> ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, NovedadTipoDetallesCab.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);
            return (NovedadTipoDetallesCab) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<NovedadTipoDetallesCab> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("NovedadTipoDetallesCab.findByEstadoReg", NovedadTipoDetallesCab.class);
            query.setParameter("estadoReg", 0);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
