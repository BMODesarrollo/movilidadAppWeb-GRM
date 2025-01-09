/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccNovedadInfrastucEstado;
import com.movilidad.model.AccNovedadTipoInfrastruc;
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
public class AccNovedadTipoInfrastrucFacade extends AbstractFacade<AccNovedadTipoInfrastruc> implements AccNovedadTipoInfrastrucFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccNovedadTipoInfrastrucFacade() {
        super(AccNovedadTipoInfrastruc.class);
    }

    @Override
    public AccNovedadTipoInfrastruc findByNombre(String nombre, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM acc_novedad_tipo_infrastruc where nombre = ?1 and id_acc_novedad_tipo_infrastruc <> ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, AccNovedadTipoInfrastruc.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);

            return (AccNovedadTipoInfrastruc) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<AccNovedadTipoInfrastruc> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("AccNovedadTipoInfrastruc.findByEstadoReg", AccNovedadTipoInfrastruc.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
