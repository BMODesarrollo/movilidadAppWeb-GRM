/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadTipoInfrastruc;
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
public class NovedadTipoInfrastrucFacade extends AbstractFacade<NovedadTipoInfrastruc> implements NovedadTipoInfrastrucFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadTipoInfrastrucFacade() {
        super(NovedadTipoInfrastruc.class);
    }

    @Override
    public NovedadTipoInfrastruc findByNombre(String nombre, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM novedad_tipo_infrastruc where nombre = ?1 and id_novedad_tipo_infrastruc <> ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, NovedadTipoInfrastruc.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);

            return (NovedadTipoInfrastruc) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<NovedadTipoInfrastruc> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("NovedadTipoInfrastruc.findByEstadoReg", NovedadTipoInfrastruc.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}