/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DispFaltanteRepuesto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface DispFaltanteRepuestoFacadeLocal {

    void create(DispFaltanteRepuesto dispFaltanteRepuesto);

    void edit(DispFaltanteRepuesto dispFaltanteRepuesto);

    void remove(DispFaltanteRepuesto dispFaltanteRepuesto);

    DispFaltanteRepuesto find(Object id);

    List<DispFaltanteRepuesto> findAll();

    List<DispFaltanteRepuesto> findRange(int[] range);

    int count();
    
}
