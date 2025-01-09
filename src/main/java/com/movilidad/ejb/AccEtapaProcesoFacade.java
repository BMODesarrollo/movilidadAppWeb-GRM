/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccEtapaProceso;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author HP
 */
@Stateless
public class AccEtapaProcesoFacade extends AbstractFacade<AccEtapaProceso> implements AccEtapaProcesoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccEtapaProcesoFacade() {
        super(AccEtapaProceso.class);
    }

    @Override
    public List<AccEtapaProceso> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_etapa_proceso WHERE estado_reg = 0", AccEtapaProceso.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<AccEtapaProceso> listPorTipoProceso(int i_idAccTipoProceso) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_etapa_proceso WHERE id_acc_tipo_proceso = ?1 AND estado_reg = 0", AccEtapaProceso.class);
            q.setParameter(1, i_idAccTipoProceso);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
