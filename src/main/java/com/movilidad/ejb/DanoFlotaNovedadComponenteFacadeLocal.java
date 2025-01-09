/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DanoFlotaNovedadComponente;
import com.movilidad.model.NovedadDano;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Julián Arévalo
 */
@Local
public interface DanoFlotaNovedadComponenteFacadeLocal {

    void create(DanoFlotaNovedadComponente novedadDanoComponente);

    void edit(DanoFlotaNovedadComponente novedadDanoComponente);

    void remove(DanoFlotaNovedadComponente novedadDanoComponente);

    DanoFlotaNovedadComponente find(Object id);

    List<DanoFlotaNovedadComponente> findAll();

    List<DanoFlotaNovedadComponente> findRange(int[] range);

    int count();

    List<DanoFlotaNovedadComponente> getAllActivo();
    
    public Integer findValueComponents(NovedadDano novedadDano);
    
    List<DanoFlotaNovedadComponente> findSolucionesByNovedad(Integer idNovedad);
    
}
