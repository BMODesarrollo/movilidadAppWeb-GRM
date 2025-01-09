/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PqrResponsable;
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
public class PqrResponsableFacade extends AbstractFacade<PqrResponsable> implements PqrResponsableFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PqrResponsableFacade() {
        super(PqrResponsable.class);
    }

    @Override
    public PqrResponsable verificarRegistro(Integer idRegistro, String responsable) {
        try {
            String sql = "SELECT * FROM pqr_responsable where id_pqr_responsable <> ?1 and responsable = ?2 and estado_reg = 0 LIMIT 1;";

            Query query = em.createNativeQuery(sql, PqrResponsable.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, responsable);

            return (PqrResponsable) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PqrResponsable> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("PqrResponsable.findByEstadoReg", PqrResponsable.class);
            query.setParameter("estadoReg", 0);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
