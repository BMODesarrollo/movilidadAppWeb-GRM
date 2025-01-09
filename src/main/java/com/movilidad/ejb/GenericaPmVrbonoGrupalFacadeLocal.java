/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmVrbonoGrupal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaPmVrbonoGrupalFacadeLocal {

    void create(GenericaPmVrbonoGrupal genericaPmVrbonoGrupal);

    void edit(GenericaPmVrbonoGrupal genericaPmVrbonoGrupal);

    void remove(GenericaPmVrbonoGrupal genericaPmVrbonoGrupal);

    GenericaPmVrbonoGrupal find(Object id);
    
    GenericaPmVrbonoGrupal verificarFecha(Date fecha, Integer idRegistro,Integer idArea);

    List<GenericaPmVrbonoGrupal> findAll();
    
    List<GenericaPmVrbonoGrupal> findAllByEstadoRegAndArea(Integer idArea);

    List<GenericaPmVrbonoGrupal> findRange(int[] range);

    int count();
    
}
