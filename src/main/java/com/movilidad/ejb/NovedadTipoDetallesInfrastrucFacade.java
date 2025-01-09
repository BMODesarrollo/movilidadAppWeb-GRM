/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadTipoDetallesInfrastruc;
import java.util.ArrayList;
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
public class NovedadTipoDetallesInfrastrucFacade extends AbstractFacade<NovedadTipoDetallesInfrastruc> implements NovedadTipoDetallesInfrastrucFacadeLocal {
    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadTipoDetallesInfrastrucFacade() {
        super(NovedadTipoDetallesInfrastruc.class);
    }
    @Override
    public NovedadTipoDetallesInfrastruc findByNombre(String nombre, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM novedad_tipo_det_infrastruc where nombre = ?1 and id_novedad_tipo_det_infrastruc <> ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, NovedadTipoDetallesInfrastruc.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);
            return (NovedadTipoDetallesInfrastruc) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<NovedadTipoDetallesInfrastruc> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("NovedadTipoDetallesInfrastruc.findByEstadoReg", NovedadTipoDetallesInfrastruc.class);
            query.setParameter("estadoReg", 0);
            return query.getResultList();
        } catch (Exception e) {
            
            return new ArrayList<>();
        }
    }
    
}
