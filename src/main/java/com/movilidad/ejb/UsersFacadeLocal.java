/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Users;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface UsersFacadeLocal {

    void create(Users users);

    void edit(Users users);

    void remove(Users users);

    Users find(Object id);

    boolean userNameFind(String userName);

    List<Users> findAll();

    List<Users> findRange(int[] range);

    int count();

    boolean validarUnicoEmpleado(int i);

    List<Users> findAllUsersActivos();

    Users findUserForUsername(String username);

    List<Users> findAllUsersActivosByUnidadFunc(int idGopUnidadFuncional);
    
    Users findEmail(String username);

}
