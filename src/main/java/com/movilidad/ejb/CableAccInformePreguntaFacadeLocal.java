/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccInformePregunta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface CableAccInformePreguntaFacadeLocal {

    void create(CableAccInformePregunta cableAccInformePregunta);

    void edit(CableAccInformePregunta cableAccInformePregunta);

    void remove(CableAccInformePregunta cableAccInformePregunta);

    CableAccInformePregunta find(Object id);

    List<CableAccInformePregunta> findAll();

    List<CableAccInformePregunta> findRange(int[] range);

    int count();

    List<CableAccInformePregunta> findAllEstadoReg();

}
