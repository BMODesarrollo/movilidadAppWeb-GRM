/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PqrMaestroRespaldo;
import com.movilidad.utils.Util;
import java.util.ArrayList;
import java.util.Date;
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
public class PqrMaestroRespaldoFacade extends AbstractFacade<PqrMaestroRespaldo> implements PqrMaestroRespaldoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PqrMaestroRespaldoFacade() {
        super(PqrMaestroRespaldo.class);
    }

    @Override
    public List<PqrMaestroRespaldo> findByIdPqr(Integer idPqr) {
        try {
            String sql = "SELECT * FROM rgmo.pqr_maestro_respaldo WHERE id_pqr_maestro = " + idPqr;
            Query query = em.createNativeQuery(sql, PqrMaestroRespaldo.class);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
}
