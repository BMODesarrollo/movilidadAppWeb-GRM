/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadCab;
import com.movilidad.model.NovedadTipoCab;
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
public class NovedadTipoCabFacade extends AbstractFacade<NovedadTipoCab> implements NovedadTipoCabFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadTipoCabFacade() {
        super(NovedadTipoCab.class);
    }

    @Override
    public NovedadTipoCab findByNombre(String nombre, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM novedad_tipo_cab where nombre = ?1 and id_novedad_tipo_cab <> ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, NovedadTipoCab.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);

            return (NovedadTipoCab) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<NovedadTipoCab> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("NovedadTipoCab.findByEstadoReg", NovedadTipoCab.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
