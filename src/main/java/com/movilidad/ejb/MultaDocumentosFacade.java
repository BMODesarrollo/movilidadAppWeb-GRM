/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MultaDocumentos;
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
public class MultaDocumentosFacade extends AbstractFacade<MultaDocumentos> implements MultaDocumentosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MultaDocumentosFacade() {
        super(MultaDocumentos.class);
    }

    @Override
    public List<MultaDocumentos> estadoRegistro() {
        try {
            Query q = em.createQuery("SELECT m FROM MultaDocumentos m WHERE m.estadoReg = :estadoReg", MultaDocumentos.class)
                    .setParameter("estadoReg", 0);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<MultaDocumentos> idMultaEstadoRegistro(int i) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM multa_documentos "
                    + "WHERE id_multa = ? AND estado_reg = 0", MultaDocumentos.class)
                    .setParameter(1, i);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
