/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dao;

import com.movilidad.model.Opcion;
import com.movilidad.model.UserRoles;
import com.movilidad.model.Users;
import java.util.Collection;

/**
 *
 * @author alexander
 */
public interface RolesDao {

    Collection<Opcion> getOpciones(UserRoles role);

    UserRoles getRole(String autoridad, Users users);

    Collection<Opcion> getOpciones();

    Opcion retrieve(Integer id);
	
    Collection<Opcion> getOpciones(UserRoles role,Users user);

}
