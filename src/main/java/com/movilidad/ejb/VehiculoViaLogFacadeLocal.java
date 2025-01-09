/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoViaLog;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Cesar Mercado
 */
@Local
public interface VehiculoViaLogFacadeLocal {

    void create(VehiculoViaLog vehiculoViaLog);

    void edit(VehiculoViaLog vehiculoViaLog);

    void remove(VehiculoViaLog vehiculoViaLog);

    VehiculoViaLog find(Object id);

    List<VehiculoViaLog> findAll();

    List<VehiculoViaLog> findRange(int[] range);

    int count();
    
}
