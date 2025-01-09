/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.service;

import com.movilidad.model.UserRoles;
import com.movilidad.model.Users;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author alexander
 */
public interface UsersService {

    Users findUser(String username);

    void createUser(Users user);

    void updateUser(Users user);

    boolean changePassword(String old, String newp);

    void asignarOpciones(UserRoles role);

    Collection<Users> retrieveAll();

    Users retrieve(Integer id);

    boolean isValid(String username, String pwd);

    List<Users> findAllHabilidatosByIdGopUnidadFunc(int idGopUnidadFunc);
}
