/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteAudiencia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccidenteAudienciaFacadeLocal {

    void create(AccidenteAudiencia accidenteAudiencia);

    void edit(AccidenteAudiencia accidenteAudiencia);

    void remove(AccidenteAudiencia accidenteAudiencia);

    AccidenteAudiencia find(Object id);

    List<AccidenteAudiencia> findAll();

    List<AccidenteAudiencia> findRange(int[] range);

    int count();
    
    List<AccidenteAudiencia> estadoReg(int i_idAccidente);
    
}
