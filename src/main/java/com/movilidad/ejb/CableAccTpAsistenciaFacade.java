/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccTpAsistencia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author soluciones-it
 */
@Stateless
public class CableAccTpAsistenciaFacade extends AbstractFacade<CableAccTpAsistencia> implements CableAccTpAsistenciaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableAccTpAsistenciaFacade() {
        super(CableAccTpAsistencia.class);
    }

    /**
     * Permite retornar objeto List CableAccTpAsistencia que su atributo
     * estado_reg sea igual a 0
     *
     * @return Objeto List CableAccTpAsistencia
     */
    @Override
    public List<CableAccTpAsistencia> findAllEstadoReg() {
        try {
            String sql = "SELECT * "
                    + "FROM cable_acc_tp_asistencia "
                    + "WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, CableAccTpAsistencia.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
