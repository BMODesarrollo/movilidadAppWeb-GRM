/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoPropietarios;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USUARIO
 */
@Local
public interface VehiculoPropietarioFacadeLocal {

    void create(VehiculoPropietarios vehiculoPropietario);

    void edit(VehiculoPropietarios vehiculoPropietario);

    void remove(VehiculoPropietarios vehiculoPropietario);

    VehiculoPropietarios find(Object id);

    List<VehiculoPropietarios> findAll();

    List<VehiculoPropietarios> findRange(int[] range);

    int count();
    
}
