/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GmoNovedadTipoInfrastruc;
import com.movilidad.model.NovedadTipoInfrastruc;
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
public class GmoNovedadTipoInfrastrucFacade extends AbstractFacade<GmoNovedadTipoInfrastruc> implements GmoNovedadTipoInfrastrucFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GmoNovedadTipoInfrastrucFacade() {
        super(GmoNovedadTipoInfrastruc.class);
    }

    @Override
    public GmoNovedadTipoInfrastruc findByNombre(String nombre, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM gmo_novedad_tipo_infrastruc where nombre = ?1 and id_gmo_novedad_tipo_infrastruc <> ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, GmoNovedadTipoInfrastruc.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);

            return (GmoNovedadTipoInfrastruc) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GmoNovedadTipoInfrastruc> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("GmoNovedadTipoInfrastruc.findByEstadoReg", GmoNovedadTipoInfrastruc.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
