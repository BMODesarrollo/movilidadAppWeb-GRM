/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dao;

import com.movilidad.model.UserRoles;
import com.movilidad.model.Users;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author alexander
 */
public interface UsersDao {

    Users findUser(String username);

    boolean isValid(String username, String pwd);

    void createUser(Users user);

    void asignarOpciones(UserRoles role);

    void updateUser(Users user);

    boolean changePassword(String old, String newp);

    Collection<Users> retrieveAll();

    Users retrieve(Integer id);

    List<Users> findAllHabilidatosByIdGopUnidadFunc(int idGopUnidadFunc);

}
