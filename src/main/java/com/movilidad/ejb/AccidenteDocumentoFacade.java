/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteDocumento;
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
public class AccidenteDocumentoFacade extends AbstractFacade<AccidenteDocumento> implements AccidenteDocumentoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccidenteDocumentoFacade() {
        super(AccidenteDocumento.class);
    }

    @Override
    public List<AccidenteDocumento> estadoReg(int i_idAccidente) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM accidente_documento "
                    + "WHERE id_accidente = ?1 AND estado_reg = 0", AccidenteDocumento.class);
            q.setParameter(1, i_idAccidente);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Accidente Documento Facade");
            return null;
        }
    }

}