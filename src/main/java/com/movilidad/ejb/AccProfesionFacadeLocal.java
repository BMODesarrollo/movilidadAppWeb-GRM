/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccProfesion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccProfesionFacadeLocal {

    void create(AccProfesion accProfesion);

    void edit(AccProfesion accProfesion);

    void remove(AccProfesion accProfesion);

    AccProfesion find(Object id);

    List<AccProfesion> findAll();

    List<AccProfesion> findRange(int[] range);

    int count();
    
    List<AccProfesion> estadoReg();
}
