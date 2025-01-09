/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.service.impl;

import com.movilidad.dao.UsersDao;
import com.movilidad.model.Users;
import com.movilidad.service.UsersService;
import com.movilidad.model.UserRoles;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author alexander
 */
@Service("usersService")
public class UsersServiceImpl implements UsersService, Serializable {
    
    private UsersDao usersDao;
    
    public UsersDao getUsersDao() {
        return usersDao;
    }
    
    public void setUsersDao(UsersDao usersDao) {
        this.usersDao = usersDao;
    }
    
    @Override
    public Users findUser(String username) {
        return this.usersDao.findUser(username);
    }
    
    @Override
    public void createUser(Users user) {
        usersDao.createUser(user);
    }
    
    @Override
    public void updateUser(Users user) {
        usersDao.updateUser(user);
    }
    
    @Override
    public boolean changePassword(String old, String newp) {
        return usersDao.changePassword(old, newp);
    }
    
    @Override
    public void asignarOpciones(UserRoles role) {
        this.getUsersDao().asignarOpciones(role);
    }
    
    @Override
    public Collection<Users> retrieveAll() {
        return this.getUsersDao().retrieveAll();
    }
    
    @Override
    public Users retrieve(Integer id) {
        return this.getUsersDao().retrieve(id);
    }
    
    @Override
    public boolean isValid(String username, String pwd) {
        return this.getUsersDao().isValid(username, pwd);
    }
    
    @Override
    public List<Users> findAllHabilidatosByIdGopUnidadFunc(int idGopUnidadFunc) {
        return this.getUsersDao().findAllHabilidatosByIdGopUnidadFunc(idGopUnidadFunc);
    }
    
}
