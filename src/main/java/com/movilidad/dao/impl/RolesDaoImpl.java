/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dao.impl;

import com.movilidad.dao.RolesDao;
import com.movilidad.model.Opcion;
import com.movilidad.model.UserRoles;
import com.movilidad.model.Users;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("rolesDao")
public class RolesDaoImpl implements RolesDao, Serializable {

    @PersistenceContext(unitName = "rigel")
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Collection<Opcion> getOpciones(UserRoles role) {
        try {
            Query query = this.getEntityManager().createQuery("Select o From Opcion o Where o.roles=:role ORDER BY o.descripcion ASC, o.nameop ASC");
            query.setParameter("role", role);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public UserRoles getRole(String autoridad, Users users) {
        Query query = this.getEntityManager().createQuery("Select o From UserRoles o Where o.authority=:auth  and o.userId=:usr");
        query.setParameter("auth", autoridad);
        query.setParameter("usr", users);
        return (UserRoles) query.getSingleResult();
    }

    @Override
    @Transactional
    public Collection<Opcion> getOpciones() {
        Query query = this.getEntityManager().createQuery("SELECT o FROM Opcion o");

        return query.getResultList();
    }

    @Override
    @Transactional
    public Opcion retrieve(Integer id) {
        Query query = this.getEntityManager().createQuery("Select o From Opcion o Where o.id=:id");
        query.setParameter("id", id);
        return (Opcion) query.getSingleResult();

    }

    @Override
    public Collection<Opcion> getOpciones(UserRoles role, Users user) {
        try {
            Query nativeq = getEntityManager().createNativeQuery("Select op.* From opcion op, opcion_role ord, user_roles"
                    + " ur where op.id=ord.idopc And ur.user_role_id=ord.idrole And ur.user_id=?1 And ur.authority=?2 ", Opcion.class);
            nativeq.setParameter(1, user.getUserId());
            nativeq.setParameter(2, role.getAuthority());

            return nativeq.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
