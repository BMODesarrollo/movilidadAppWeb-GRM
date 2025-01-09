/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MttoComponenteFalla;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author solucionesit
 */
@Stateless
public class MttoComponenteFallaFacade extends AbstractFacade<MttoComponenteFalla> implements MttoComponenteFallaFacadeLocal {


    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MttoComponenteFallaFacade() {
        super(MttoComponenteFalla.class);
    }

    @Override
    public List<MttoComponenteFalla> getCompFallaByIdComp(int id) {
        Query q = em.createNativeQuery("SELECT * FROM mtto_componente_falla WHERE id_mtto_componente=?1 ORDER BY falla asc;", MttoComponenteFalla.class);
        q.setParameter(1, id);
        return q.getResultList();
    }

}
