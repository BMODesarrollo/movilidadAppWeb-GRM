/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccTipoServ;
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
public class AccTipoServFacade extends AbstractFacade<AccTipoServ> implements AccTipoServFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccTipoServFacade() {
        super(AccTipoServ.class);
    }

    @Override
    public List<AccTipoServ> estadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM acc_tipo_serv WHERE estado_reg = 0", AccTipoServ.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade TipoServ");
            return null;
        }
    }

}
