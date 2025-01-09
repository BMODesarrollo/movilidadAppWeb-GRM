/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstLaborTipoDocs;
import com.movilidad.model.SstTipoLabor;
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
public class SstLaborTipoDocsFacade extends AbstractFacade<SstLaborTipoDocs> implements SstLaborTipoDocsFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SstLaborTipoDocsFacade() {
        super(SstLaborTipoDocs.class);
    }

    @Override
    public SstLaborTipoDocs findByTipoLaborAndDocTercero(Integer idTipoDocTercero, Integer idTipoLabor) {
        try {
            String sql = "select\n"
                    + "	*\n"
                    + "from\n"
                    + "	sst_labor_tipo_docs\n"
                    + "where\n"
                    + "	id_tipo_doc_tercero = ?1\n"
                    + "	and id_tipo_labor = ?2\n"
                    + "	and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, SstLaborTipoDocs.class);
            query.setParameter(1, idTipoDocTercero);
            query.setParameter(2, idTipoLabor);

            return (SstLaborTipoDocs) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<SstLaborTipoDocs> findAllEstadoReg() {
        try {
            Query query = em.createNamedQuery("SstLaborTipoDocs.findByEstadoReg", SstLaborTipoDocs.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<SstLaborTipoDocs> findByTipoLabor(Integer idTipoLabor) {
        try {
            String sql = "SELECT * FROM sst_labor_tipo_docs where id_tipo_labor = ?1 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, SstLaborTipoDocs.class);
            query.setParameter(1, idTipoLabor);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
