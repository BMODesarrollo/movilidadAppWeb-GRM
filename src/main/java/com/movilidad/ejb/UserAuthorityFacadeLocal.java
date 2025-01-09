/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.UserAuthority;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface UserAuthorityFacadeLocal {

    void create(UserAuthority userAuthority);

    void edit(UserAuthority userAuthority);

    void remove(UserAuthority userAuthority);

    UserAuthority find(Object id);

    List<UserAuthority> findAll();

    List<UserAuthority> findRange(int[] range);

    int count();
    
    List<UserAuthority> findAllEstadoReg();
    
}
