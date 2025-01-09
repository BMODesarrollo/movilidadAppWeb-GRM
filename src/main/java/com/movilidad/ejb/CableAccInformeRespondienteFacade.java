/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccInformeRespondiente;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author soluciones-it
 */
@Stateless
public class CableAccInformeRespondienteFacade extends AbstractFacade<CableAccInformeRespondiente> implements CableAccInformeRespondienteFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableAccInformeRespondienteFacade() {
        super(CableAccInformeRespondiente.class);
    }

    /**
     * Permite consultar el objeto CableAccInformeRespondiente de acuerdo al
     * identificador unico CableAccidentalidad
     *
     * @param idCableAccidentalidad identificador unico objeto
     * CableAccidentalidad
     * @return objeto List de CableAccInformeRespondiente
     */
    @Override
    public CableAccInformeRespondiente findByCableAccidentalidad(Integer idCableAccidentalidad) {
        try {
            String sql = "SELECT * "
                    + "FROM cable_acc_informe_respondiente "
                    + "WHERE id_cable_accidentalidad = ?1 "
                    + "AND estado_reg = 0 LIMIT 1";
            Query q = em.createNativeQuery(sql, CableAccInformeRespondiente.class);
            q.setParameter(1, idCableAccidentalidad);
            return (CableAccInformeRespondiente) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
