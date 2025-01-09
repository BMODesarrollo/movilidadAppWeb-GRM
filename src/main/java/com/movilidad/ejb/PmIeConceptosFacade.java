/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmIeConceptos;
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
public class PmIeConceptosFacade extends AbstractFacade<PmIeConceptos> implements PmIeConceptosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmIeConceptosFacade() {
        super(PmIeConceptos.class);
    }

    @Override
    public PmIeConceptos findByConcepto(String concepto, Integer idRegistro, Integer idPmTipoConcepto) {
        String sql = "SELECT * FROM pm_ie_conceptos where concepto = ?1 and id_pm_ie_conceptos <> ?2 and id_pm_tipo_concepto = ?3 and estado_reg = 0 limit 1;";

        try {
            Query query = em.createNativeQuery(sql, PmIeConceptos.class);
            query.setParameter(1, concepto);
            query.setParameter(2, idRegistro);
            query.setParameter(3, idPmTipoConcepto);
            
            return (PmIeConceptos) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PmIeConceptos> findAllByEstadoReg() {
        String sql = "SELECT * FROM pm_ie_conceptos where estado_reg = 0;";

        try {
            Query query = em.createNativeQuery(sql, PmIeConceptos.class);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
