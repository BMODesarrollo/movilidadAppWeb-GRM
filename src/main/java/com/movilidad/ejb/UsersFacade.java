/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Users;
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
public class UsersFacade extends AbstractFacade<Users> implements UsersFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }

    @Override
    public boolean userNameFind(String userName) {
        try {
            Query q = em.createQuery("SELECT u FROM Users u WHERE u.username = :username", Users.class)
                    .setParameter("username", "" + userName + "");
            return q.getResultList().isEmpty();
            //retorna verdadero si está vacio
        } catch (Exception e) {
            return false;
        }
    }

    // validar que el usuario no se registre dos veces
    @Override
    public boolean validarUnicoEmpleado(int i) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM users WHERE id_empleado = ?", Users.class)
                    .setParameter(1, i);
            return q.getResultList().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Users> findAllUsersActivos() {
        try {
            String sql = "SELECT * FROM users WHERE enabled = 1";
            Query q = em.createNativeQuery(sql, Users.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Users findUserForUsername(String username) {
        try {
            String sql = "SELECT u.* "
                    + "FROM users u "
                    + "WHERE u.username = ?1 "
                    + "AND u.enabled = 1";
            Query q = em.createNativeQuery(sql, Users.class);
            q.setParameter(1, username);
            return (Users) q.getSingleResult();
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Users> findAllUsersActivosByUnidadFunc(int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "     WHERE u.id_gop_unidad_funcional = ?1\n";

        String sql = "SELECT \n"
                + "    u.*\n"
                + "FROM\n"
                + "    users u"
                + sql_unida_func;
        Query q = em.createNativeQuery(sql, Users.class);
        q.setParameter(1, idGopUnidadFuncional);
        return q.getResultList();
    }
    
    @Override
    public Users findEmail(String username) {
        try {
            Query q = em.createNativeQuery("SELECT u.* FROM users u WHERE u.username = ?1"
                    , Users.class).setParameter(1, username);
            return (Users) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
