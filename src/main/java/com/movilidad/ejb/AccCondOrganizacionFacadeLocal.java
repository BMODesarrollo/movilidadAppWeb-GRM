/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccCondOrganizacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccCondOrganizacionFacadeLocal {

    void create(AccCondOrganizacion accCondOrganizacion);

    void edit(AccCondOrganizacion accCondOrganizacion);

    void remove(AccCondOrganizacion accCondOrganizacion);

    AccCondOrganizacion find(Object id);

    List<AccCondOrganizacion> findAll();

    List<AccCondOrganizacion> findRange(int[] range);

    int count();

    List<AccCondOrganizacion> estadoReg();

}
