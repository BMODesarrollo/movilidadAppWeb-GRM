/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccChecklist;
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
public class AccChecklistFacade extends AbstractFacade<AccChecklist> implements AccChecklistFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccChecklistFacade() {
        super(AccChecklist.class);
    }

    @Override
    public List<AccChecklist> findAll() {
        try {
            String sql = "select * from acc_checklist where estado_reg = 0 order by id_novedad_tipo_detalle;";

            Query query = em.createNativeQuery(sql, AccChecklist.class);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public AccChecklist verificarRepetido(int detalle, int tipoDocumento) {
        try {
            String sql = "select * from acc_checklist where id_novedad_tipo_detalle = ?1 \n"
                    + "and id_acc_tipo_documento = ?2 LIMIT 1";

            Query query = em.createNativeQuery(sql, AccChecklist.class);
            query.setParameter(1, detalle);
            query.setParameter(2, tipoDocumento);

            return (AccChecklist) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
