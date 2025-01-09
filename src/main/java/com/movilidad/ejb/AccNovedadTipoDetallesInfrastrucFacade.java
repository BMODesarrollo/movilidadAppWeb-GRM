/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccNovedadTipoDetallesInfrastruc;
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
public class AccNovedadTipoDetallesInfrastrucFacade extends AbstractFacade<AccNovedadTipoDetallesInfrastruc> implements AccNovedadTipoDetallesInfrastrucFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccNovedadTipoDetallesInfrastrucFacade() {
        super(AccNovedadTipoDetallesInfrastruc.class);
    }

    @Override
    public AccNovedadTipoDetallesInfrastruc findByNombre(String nombre, Integer idRegistro, Integer idTipoNovedad) {
        try {
            String sql = "SELECT * FROM acc_novedad_tipo_detalles_infrastruc where nombre = ?1 and id_acc_novedad_tipo_detalle_infrastruc <> ?2 and id_novedad_tipo_infrastruc = ?3 and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, AccNovedadTipoDetallesInfrastruc.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);
            query.setParameter(3, idTipoNovedad);

            return (AccNovedadTipoDetallesInfrastruc) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<AccNovedadTipoDetallesInfrastruc> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("AccNovedadTipoDetallesInfrastruc.findByEstadoReg", AccNovedadTipoDetallesInfrastruc.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
