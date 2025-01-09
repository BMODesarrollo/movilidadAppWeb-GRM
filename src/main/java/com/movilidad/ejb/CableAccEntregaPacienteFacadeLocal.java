/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccEntregaPaciente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface CableAccEntregaPacienteFacadeLocal {

    void create(CableAccEntregaPaciente cableAccEntregaPaciente);

    void edit(CableAccEntregaPaciente cableAccEntregaPaciente);

    void remove(CableAccEntregaPaciente cableAccEntregaPaciente);

    CableAccEntregaPaciente find(Object id);

    List<CableAccEntregaPaciente> findAll();

    List<CableAccEntregaPaciente> findRange(int[] range);

    int count();

    List<CableAccEntregaPaciente> findAllEstadoReg();

}
