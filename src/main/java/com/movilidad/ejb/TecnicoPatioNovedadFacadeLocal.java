/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.TecnicoPatioNovedad;
import javax.ejb.Local;

/**
 *
 * @author Arévalo
 */
@Local
public interface TecnicoPatioNovedadFacadeLocal {

    void create(TecnicoPatioNovedad novedad);

    void edit(TecnicoPatioNovedad novedad);

    void remove(TecnicoPatioNovedad novedad);
    

}
