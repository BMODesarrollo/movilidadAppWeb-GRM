/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegActividadInoperativos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface SegActividadInoperativosFacadeLocal {

    void create(SegActividadInoperativos segActividadInoperativos);

    void edit(SegActividadInoperativos segActividadInoperativos);

    void remove(SegActividadInoperativos segActividadInoperativos);

    SegActividadInoperativos find(Object id);

    List<SegActividadInoperativos> findAll();

    List<SegActividadInoperativos> findRange(int[] range);

    int count();
    
}
