/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ChkDiario;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface ChkDiarioFacadeLocal {

    void create(ChkDiario chkDiario);

    void edit(ChkDiario chkDiario);

    void remove(ChkDiario chkDiario);

    ChkDiario find(Object id);

    List<ChkDiario> findAll();

    List<ChkDiario> findAllByFechas(Date desde, Date hasta, int idGopUnidadFuncional);

    List<ChkDiario> findRange(int[] range);

    int count();

}
