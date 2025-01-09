/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.UserRoles;
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
public class UserRolesFacade extends AbstractFacade<UserRoles> implements UserRolesFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserRolesFacade() {
        super(UserRoles.class);
    }

    @Override
    public List findRoles() {
        try {
            Query q = em.createNativeQuery("SELECT DISTINCT authority FROM user_roles");
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Pilas que hay error");
            return null;
        }
    }

    @Override
    public UserRoles findByUserName(String username) {
        try {
            Query q = em.createNativeQuery("SELECT ur.* FROM user_roles ur "
                    + "inner join users u on u.user_id= ur.user_id "
                    + "where u.username= ?1;", UserRoles.class);
            q.setParameter(1, username);
            return (UserRoles) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("Pilas que hay error");
            return null;
        }
    }

    @Override
    public List<Integer> idOpcionRole(int i) {
        try {
            Query q = em.createNativeQuery("select idopc from "
                    + "opcion_role where idrole = ?1");
            q.setParameter(1, i);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Pilas que hay error --> Integer.Class");
            return null;
        }
    }

}
