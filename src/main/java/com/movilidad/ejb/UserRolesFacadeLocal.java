/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.UserRoles;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface UserRolesFacadeLocal {

    void create(UserRoles userRoles);

    void edit(UserRoles userRoles);

    void remove(UserRoles userRoles);

    UserRoles find(Object id);

    UserRoles findByUserName(String username);

    List<UserRoles> findAll();

    List<UserRoles> findRange(int[] range);

    int count();

    List findRoles();
    
    List<Integer> idOpcionRole(int i);

}
