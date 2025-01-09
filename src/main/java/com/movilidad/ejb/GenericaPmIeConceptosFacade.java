/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmIeConceptos;
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
public class GenericaPmIeConceptosFacade extends AbstractFacade<GenericaPmIeConceptos> implements GenericaPmIeConceptosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaPmIeConceptosFacade() {
        super(GenericaPmIeConceptos.class);
    }

    @Override
    public GenericaPmIeConceptos findByConcepto(String concepto, Integer idRegistro, Integer idPmTipoConcepto,Integer idArea) {
        String sql = "SELECT * FROM generica_pm_ie_conceptos where concepto = ?1 and id_generica_pm_ie_conceptos <> ?2 and id_generica_pm_tipo_concepto = ?3 and id_param_area = ?4 and estado_reg = 0 limit 1;";

        try {
            Query query = em.createNativeQuery(sql, GenericaPmIeConceptos.class);
            query.setParameter(1, concepto);
            query.setParameter(2, idRegistro);
            query.setParameter(3, idPmTipoConcepto);
            query.setParameter(4, idArea);

            return (GenericaPmIeConceptos) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GenericaPmIeConceptos> findAllByEstadoRegAndArea(Integer idArea) {
        String sql = "SELECT * FROM generica_pm_ie_conceptos where estado_reg = 0 AND id_param_area = ?1;";

        try {
            Query query = em.createNativeQuery(sql, GenericaPmIeConceptos.class);
            query.setParameter(1, idArea);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
