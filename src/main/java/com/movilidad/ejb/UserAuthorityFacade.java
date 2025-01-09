/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.UserAuthority;
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
public class UserAuthorityFacade extends AbstractFacade<UserAuthority> implements UserAuthorityFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserAuthorityFacade() {
        super(UserAuthority.class);
    }

    @Override
    public List<UserAuthority> findAllEstadoReg() {
        try {
            String sql = "SELECT * FROM user_authority WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, UserAuthority.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

}
