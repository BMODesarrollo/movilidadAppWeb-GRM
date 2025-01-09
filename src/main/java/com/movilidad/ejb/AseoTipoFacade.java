/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AseoTipo;
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
public class AseoTipoFacade extends AbstractFacade<AseoTipo> implements AseoTipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AseoTipoFacade() {
        super(AseoTipo.class);
    }

    @Override
    public List<AseoTipo> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("AseoTipo.findByEstadoReg", AseoTipo.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public AseoTipo findByNombre(String nombre, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM aseo_tipo where id_aseo_tipo <> ?1 and nombre = ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, AseoTipo.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, nombre);

            return (AseoTipo) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
