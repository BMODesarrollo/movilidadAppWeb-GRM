/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPdMaestro;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GenericaPdMaestroFacadeLocal {

    void create(GenericaPdMaestro genericaPdMaestro);

    void edit(GenericaPdMaestro genericaPdMaestro);

    void remove(GenericaPdMaestro genericaPdMaestro);

    GenericaPdMaestro find(Object id);

    List<GenericaPdMaestro> findAll();
    
    List<GenericaPdMaestro> findAllByEstadoReg();
    
    List<GenericaPdMaestro> findAllByArea(Integer idArea);

    List<GenericaPdMaestro> findRange(int[] range);

    int count();
    
}
