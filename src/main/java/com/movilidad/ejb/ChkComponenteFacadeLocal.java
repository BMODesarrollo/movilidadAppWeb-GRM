/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ChkComponente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface ChkComponenteFacadeLocal {

    void create(ChkComponente chkComponente);

    void edit(ChkComponente chkComponente);

    void remove(ChkComponente chkComponente);

    ChkComponente find(Object id);

    ChkComponente findByNombre(Integer idChkComponente, String nombre);

    List<ChkComponente> findAll();

    List<ChkComponente> findAllByEstadoReg();

    List<ChkComponente> findRange(int[] range);

    int count();

}
