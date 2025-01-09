/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoDano;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USUARIO
 */
@Local
public interface VehiculoDanoFacadeLocal {

    void create(VehiculoDano vehiculoDano);

    void edit(VehiculoDano vehiculoDano);

    void remove(VehiculoDano vehiculoDano);

    VehiculoDano find(Object id);

    List<VehiculoDano> findAll();
    
    List<VehiculoDano> findRange(int[] range);

    int count();
    
}
