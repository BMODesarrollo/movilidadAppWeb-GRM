/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteDocumento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccidenteDocumentoFacadeLocal {

    void create(AccidenteDocumento accidenteDocumento);

    void edit(AccidenteDocumento accidenteDocumento);

    void remove(AccidenteDocumento accidenteDocumento);

    AccidenteDocumento find(Object id);

    List<AccidenteDocumento> findAll();

    List<AccidenteDocumento> findRange(int[] range);

    int count();

    List<AccidenteDocumento> estadoReg(int i_idAccidente);

}
