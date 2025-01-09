/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmGrupo;
import java.util.ArrayList;
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
public class GenericaPmGrupoFacade extends AbstractFacade<GenericaPmGrupo> implements GenericaPmGrupoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaPmGrupoFacade() {
        super(GenericaPmGrupo.class);
    }

    @Override
    public List<GenericaPmGrupo> getByIdArea(int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    g.*\n"
                    + "FROM\n"
                    + "    generica_pm_grupo g\n"
                    + "WHERE\n"
                    + "    g.id_param_area = ?1 AND estado_reg = 0;", GenericaPmGrupo.class);
            q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
