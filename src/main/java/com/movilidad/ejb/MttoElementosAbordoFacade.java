/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MttoElementosAbordo;
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
public class MttoElementosAbordoFacade extends AbstractFacade<MttoElementosAbordo> implements MttoElementosAbordoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MttoElementosAbordoFacade() {
        super(MttoElementosAbordo.class);
    }

    /**
     * Obtener objeto List de MttoElementosAbordo por estadoReg = 0
     *
     * @return List MttoElementosAbordo
     */
    @Override
    public List<MttoElementosAbordo> findAllEstadoReg() {
        String sql = "SELECT * FROM mtto_elementos_abordo WHERE estado_reg = 0";
        Query q = em.createNativeQuery(sql, MttoElementosAbordo.class);
        return q.getResultList();
    }

}
