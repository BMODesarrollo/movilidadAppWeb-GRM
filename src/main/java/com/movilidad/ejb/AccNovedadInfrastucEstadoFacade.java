/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccNovedadInfrastucEstado;
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
public class AccNovedadInfrastucEstadoFacade extends AbstractFacade<AccNovedadInfrastucEstado> implements AccNovedadInfrastucEstadoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccNovedadInfrastucEstadoFacade() {
        super(AccNovedadInfrastucEstado.class);
    }

    @Override
    public AccNovedadInfrastucEstado findByNombre(String nombre, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM acc_novedad_infrastuc_estado where nombre = ?1 and id_acc_novedad_infrastuc_estado <> ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, AccNovedadInfrastucEstado.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);

            return (AccNovedadInfrastucEstado) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<AccNovedadInfrastucEstado> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("AccNovedadInfrastucEstado.findByEstadoReg", AccNovedadInfrastucEstado.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
