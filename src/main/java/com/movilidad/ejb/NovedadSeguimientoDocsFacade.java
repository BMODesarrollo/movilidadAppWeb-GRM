/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadSeguimientoDocs;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Alberto
 */
@Stateless
public class NovedadSeguimientoDocsFacade extends AbstractFacade<NovedadSeguimientoDocs> implements NovedadSeguimientoDocsFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadSeguimientoDocsFacade() {
        super(NovedadSeguimientoDocs.class);
    }

    @Override
    public List<NovedadSeguimientoDocs> findByIdSeguimiento(Integer idSeguimiento) {
        try {
            String sql = "SELECT * FROM novedad_seguimiento_docs WHERE "
                    + "id_novedad_seguimiento = ?1 AND estado_reg = 0;";

            Query query = em.createNamedQuery(sql, NovedadSeguimientoDocs.class);
            query.setParameter(1, idSeguimiento);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
