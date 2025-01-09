/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteVictima;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccidenteVictimaFacadeLocal {

    void create(AccidenteVictima accidenteVictima);

    void edit(AccidenteVictima accidenteVictima);

    void remove(AccidenteVictima accidenteVictima);

    AccidenteVictima find(Object id);

    List<AccidenteVictima> findAll();

    List<AccidenteVictima> findRange(int[] range);

    int count();

    List<AccidenteVictima> estadoReg(int i_idAccidente);

    /**
     * Consultar victima por accidente y cedula
     *
     * @param cedula
     * @param idAccidente
     * @return
     */
    AccidenteVictima findAccidenteVictimaByCedulaAndIdAcc(String cedula, Integer idAccidente);

}
