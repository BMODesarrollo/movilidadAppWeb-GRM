/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccSiniestrado;
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
public class CableAccSiniestradoFacade extends AbstractFacade<CableAccSiniestrado> implements CableAccSiniestradoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableAccSiniestradoFacade() {
        super(CableAccSiniestrado.class);
    }

    /**
     * Permite consultar los registros asociados a un objeto CableAccidentalidad
     *
     * @param idCableAcc identificador unico CableAccidentalidad
     * @return objeto List de CableAccSiniestrado
     */
    @Override
    public List<CableAccSiniestrado> findAllEstadoReg(Integer idCableAcc) {
        try {
            String sql = "SELECT * "
                    + "FROM cable_acc_siniestrado "
                    + "WHERE id_cable_accidentalidad = ?1 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, CableAccSiniestrado.class);
            q.setParameter(1, idCableAcc);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
