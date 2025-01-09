/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dao.impl;

import com.movilidad.dao.UsersDao;
import com.movilidad.model.UserRoles;
import com.movilidad.model.Users;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.ObjetoSigleton;
import com.movilidad.utils.SingletonConfigEmpresa;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.eclipse.persistence.internal.oxm.Constants;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author alexander
 */
public class UsersImpl implements UsersDao, Serializable {

    @PersistenceContext(unitName="rigel")
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Users findUser(String username) {
        try {
            Query createQuery = entityManager.createQuery("SELECT u FROM Users u"
                    + " WHERE u.username = :username");
            createQuery.setParameter("username", username);
            return (Users) createQuery.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void createUser(Users user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void updateUser(Users user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public boolean changePassword(String old, String newp) {

        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void asignarOpciones(UserRoles role) {
        //Query nativeQ = this.entityManager.createNativeQuery("Delete from opcion_role where idrole=?1 ");
        //nativeQ.setParameter(1, role.getUserRoleId());
        //nativeQ.executeUpdate();
        this.entityManager.merge(role);
    }

    @Override
    @Transactional
    public Collection<Users> retrieveAll() {
        Query query = this.entityManager.createQuery("Select u From Users u");
        return query.getResultList();

    }

    @Override
    @Transactional
    public Users retrieve(Integer id) {
        Query query = this.entityManager.createQuery("Select u From Users u Where u.userId=:id");
        query.setParameter("id", id);
        return (Users) query.getSingleResult();

    }

    @Override
    @Transactional
    public boolean isValid(String username, String pwd) {
        boolean sw = false;
        try {
            Query createQuery = entityManager.createQuery("SELECT u FROM Users u "
                    + " WHERE u.username = :username and u.password=:pass");
            createQuery.setParameter("username", username);
            createQuery.setParameter("pass", pwd);
            Users u = (Users) createQuery.getSingleResult();
            if (u != null) {
                sw = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return sw;
    }

    @Override
    public List<Users> findAllHabilidatosByIdGopUnidadFunc(int idGopUnidadFunc) {
        String sql = "SELECT \n"
                + "    u.*\n"
                + "FROM\n"
                + "    users u\n"
                + "        INNER JOIN\n"
                + "    empleado e ON u.id_empleado = e.id_empleado\n"
                + "WHERE\n"
                + "    u.enabled = 1\n"
                + "        AND u.id_gop_unidad_funcional = ?1\n"
                + "        AND e.id_empleado_cargo IN (" + SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_CARGOS_PANEL_PRINCIPAL) + ");";
        Query q = entityManager.createNativeQuery(sql, Users.class);
        q.setParameter(1, idGopUnidadFunc);
        return q.getResultList();
    }

}
