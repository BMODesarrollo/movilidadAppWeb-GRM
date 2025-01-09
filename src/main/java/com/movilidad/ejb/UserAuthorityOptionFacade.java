/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccChecklist;
import com.movilidad.model.UserAuthorityOption;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class UserAuthorityOptionFacade extends AbstractFacade<UserAuthorityOption> implements UserAuthorityOptionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserAuthorityOptionFacade() {
        super(UserAuthorityOption.class);
    }

    @Override
    public List<UserAuthorityOption> findAll() {
        try {
            String sql = "select * from user_authority_option where estado_reg = 0 order by id_user_authority;";

            Query query = em.createNativeQuery(sql, UserAuthorityOption.class);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserAuthorityOption verificarRepetido(int authority, int opcion) {
        try {
            String sql = "select * from user_authority_option where id_user_authority = ?1 \n"
                    + "and id_opcion = ?2 LIMIT 1";

            Query query = em.createNativeQuery(sql, UserAuthorityOption.class);
            query.setParameter(1, authority);
            query.setParameter(2, opcion);

            return (UserAuthorityOption) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
