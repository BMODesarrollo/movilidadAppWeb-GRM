/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.UserAuthorityOption;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface UserAuthorityOptionFacadeLocal {

    void create(UserAuthorityOption userAuthorityOption);

    void edit(UserAuthorityOption userAuthorityOption);

    void remove(UserAuthorityOption userAuthorityOption);

    UserAuthorityOption find(Object id);
    
    UserAuthorityOption verificarRepetido(int authority, int opcion);

    List<UserAuthorityOption> findAll();

    List<UserAuthorityOption> findRange(int[] range);

    int count();
    
}
