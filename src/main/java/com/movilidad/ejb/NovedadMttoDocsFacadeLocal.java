/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadMttoDocs;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NovedadMttoDocsFacadeLocal {

    void create(NovedadMttoDocs novedadMttoDocs);

    void edit(NovedadMttoDocs novedadMttoDocs);

    void remove(NovedadMttoDocs novedadMttoDocs);

    NovedadMttoDocs find(Object id);
    
    List<NovedadMttoDocs> findAll();
    
    List<NovedadMttoDocs> findAllByNovMtto(Integer idNovMtto);

    List<NovedadMttoDocs> findRange(int[] range);

    int count();
    
}
