/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ContableRptFiducia;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface ContableRptFiduciaFacadeLocal {

    void create(ContableRptFiducia contableRptFiducia);

    void edit(ContableRptFiducia contableRptFiducia);

    void remove(ContableRptFiducia contableRptFiducia);

    ContableRptFiducia find(Object id);

    List<ContableRptFiducia> findAll();

    List<ContableRptFiducia> findRange(int[] range);

    int count();

    List<ContableRptFiducia> getContablesRptFiduciaByDate(Date d, Integer idContableRptFiducia);

    List<ContableRptFiducia> findAllEstadoReg();

    List<ContableRptFiducia> findAllRangoFechaEstadoReg(Date desde, Date hasta);

}
