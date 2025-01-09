/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstEps;
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
public class SstEpsFacade extends AbstractFacade<SstEps> implements SstEpsFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SstEpsFacade() {
        super(SstEps.class);
    }

    /**
     * Permite obtener la lista de objetos donde su atributo estado_reg sea
     * igual a 0
     *
     * @return Lista de objetos SstEps, null en caso de error
     */
    @Override
    public List<SstEps> findAllEstadoReg() {
        try {
            String sql = "SELECT * "
                    + "FROM sst_eps "
                    + "WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, SstEps.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
