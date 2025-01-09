/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgTcResponsable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author solucionesit
 */
@Stateless
public class PrgTcResponsableFacade extends AbstractFacade<PrgTcResponsable> implements PrgTcResponsableFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgTcResponsableFacade() {
        super(PrgTcResponsable.class);
    }

    @Override
    public List<PrgTcResponsable> getPrgResponsables() {
        try {
            Query q = em.createNativeQuery("SELECT r.* FROM prg_tc_responsable r WHERE r.estado_reg=0;", PrgTcResponsable.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public PrgTcResponsable findByResponsable(Integer idRegistro, String responsable) {
        try {
            String sql = "SELECT * FROM prg_tc_responsable where id_prg_tc_responsable <> ?1 and responsable = ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PrgTcResponsable.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, responsable);

            return (PrgTcResponsable) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
