/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.TecnicoPatioNovedadTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Arévalo
 */
@Local
public interface TecnicoPatioNovedadTipoFacadeLocal {

     public List<TecnicoPatioNovedadTipo> findAll();
     
     public TecnicoPatioNovedadTipo find(Object id);
    
}
