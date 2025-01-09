/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadMttoDiaria;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface NovedadMttoDiariaFacadeLocal {

    void create(NovedadMttoDiaria novedadMttoDiaria);

    void edit(NovedadMttoDiaria novedadMttoDiaria);

    void remove(NovedadMttoDiaria novedadMttoDiaria);

    NovedadMttoDiaria find(Object id);

    List<NovedadMttoDiaria> findAll();

    List<NovedadMttoDiaria> findRange(int[] range);

    int count();
    
    List<NovedadMttoDiaria> findAllByFechaHora(Date ini, Date fin);
    
}
