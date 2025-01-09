/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccDocumento;
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
public class CableAccDocumentoFacade extends AbstractFacade<CableAccDocumento> implements CableAccDocumentoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableAccDocumentoFacade() {
        super(CableAccDocumento.class);
    }

    /**
     * Permite consultar los registros asociados a un objeto CableAccidentalidad
     *
     * @param idCableAccidentalidad identificador unico CableAccidentalidad
     * @return objeto List de CableAccTestigo
     */
    @Override
    public List<CableAccDocumento> findByAccidentalidadAndEstadoReg(Integer idCableAccidentalidad) {
        try {
            String sql = "SELECT * "
                    + "FROM cable_acc_documento "
                    + "WHERE id_cable_accidentalidad = ?1 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, CableAccDocumento.class);
            q.setParameter(1, idCableAccidentalidad);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
