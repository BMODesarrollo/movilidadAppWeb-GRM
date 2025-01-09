/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteLugarDemar;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccidenteLugarDemarFacadeLocal {

    void create(AccidenteLugarDemar accidenteLugarDemar);

    void edit(AccidenteLugarDemar accidenteLugarDemar);

    void remove(AccidenteLugarDemar accidenteLugarDemar);

    AccidenteLugarDemar find(Object id);

    List<AccidenteLugarDemar> findAll();

    List<AccidenteLugarDemar> findRange(int[] range);

    int count();

    List<AccidenteLugarDemar> objetosSelect(int i_id_Accidente);
    
    AccidenteLugarDemar findByAccLugarAndViaDemar(int i_idAccidenteLugar, int i_idAccViaDemarcacion);
    
    List<AccidenteLugarDemar> getAccidenteLugarDemarInformeOpe(int idAccInformeOpe);

}
