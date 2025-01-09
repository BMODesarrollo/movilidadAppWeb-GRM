/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadMttoTipo;
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
public class NovedadMttoTipoFacade extends AbstractFacade<NovedadMttoTipo> implements NovedadMttoTipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadMttoTipoFacade() {
        super(NovedadMttoTipo.class);
    }

    @Override
    public List<NovedadMttoTipo> findAllByEstadoReg() {
        String sql = "SELECT * FROM novedad_mtto_tipo where estado_reg = 0;";

        try {
            Query query = em.createNativeQuery(sql, NovedadMttoTipo.class);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public NovedadMttoTipo findByNombre(Integer idRegistro, String nombre) {
        String sql = "SELECT * FROM novedad_mtto_tipo where nombre = ?1 "
                + "AND id_novedad_mtto_tipo <> ?2 AND estado_reg = 0 LIMIT 1;";

        try {
            Query query = em.createNativeQuery(sql, NovedadMttoTipo.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);

            return (NovedadMttoTipo) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
