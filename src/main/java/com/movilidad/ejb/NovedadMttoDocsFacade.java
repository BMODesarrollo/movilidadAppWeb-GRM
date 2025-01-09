/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadMttoDocs;
import java.util.ArrayList;
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
public class NovedadMttoDocsFacade extends AbstractFacade<NovedadMttoDocs> implements NovedadMttoDocsFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadMttoDocsFacade() {
        super(NovedadMttoDocs.class);
    }

    @Override
    public List<NovedadMttoDocs> findAllByNovMtto(Integer idNovMtto) {
        try {
            String sql = "SELECT * FROM novedad_mtto_docs where id_novedad_mtto = ?1 and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, NovedadMttoDocs.class);
            query.setParameter(1, idNovMtto);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
