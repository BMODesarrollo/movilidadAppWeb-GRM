/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccTestigo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cesar
 */
@Stateless
public class CableAccTestigoFacade extends AbstractFacade<CableAccTestigo> implements CableAccTestigoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableAccTestigoFacade() {
        super(CableAccTestigo.class);
    }

    /**
     * Permite consultar los registros asociados a un objeto CableAccidentalidad
     *
     * @param idCableAcc identificador unico CableAccidentalidad
     * @return objeto List de CableAccTestigo
     */
    @Override
    public List<CableAccTestigo> findAllEstadoReg(Integer idCableAcc) {
        try {
            String sql = "SELECT * "
                    + "FROM cable_acc_testigo "
                    + "WHERE id_cable_accidentalidad = ?1 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, CableAccTestigo.class);
            q.setParameter(1, idCableAcc);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
