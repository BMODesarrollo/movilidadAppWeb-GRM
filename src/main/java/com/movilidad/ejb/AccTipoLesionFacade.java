/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccTipoLesion;
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
public class AccTipoLesionFacade extends AbstractFacade<AccTipoLesion> implements AccTipoLesionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccTipoLesionFacade() {
        super(AccTipoLesion.class);
    }

    @Override
    public List<AccTipoLesion> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_tipo_lesion WHERE estado_reg = 0;", AccTipoLesion.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en el Facade de Tipo Lesión");
            return null;
        }
    }

}
