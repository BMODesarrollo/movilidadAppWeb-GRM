/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.service.impl;

import com.movilidad.dao.RolesDao;
import com.movilidad.service.RolesService;
import com.movilidad.model.Opcion;
import com.movilidad.model.UserRoles;
import com.movilidad.model.Users;
import java.io.Serializable;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rolesService")
public class RolesServiceImpl implements RolesService, Serializable {

    @Autowired
    private RolesDao rolesDao;

    public RolesDao getRolesDao() {
        return rolesDao;
    }

    public void setRolesDao(RolesDao rolesDao) {
        this.rolesDao = rolesDao;
    }

    @Override
    public Collection<Opcion> getOpciones(UserRoles role) {
        return this.getRolesDao().getOpciones(role);
    }

    @Override
    public UserRoles getRole(String autoridad, Users users) {
        return this.getRolesDao().getRole(autoridad, users);
    }

    @Override
    public Collection<Opcion> getOpciones() {
        return this.getRolesDao().getOpciones();
    }

    @Override
    public Opcion retrieve(Integer id) {
        return this.rolesDao.retrieve(id);
    }

    @Override
    public Collection<Opcion> getOpciones(UserRoles role, Users user) {
        return this.rolesDao.getOpciones(role, user);
    }
    
}