/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ContableRptFiduciaDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface ContableRptFiduciaDetFacadeLocal {

    void create(ContableRptFiduciaDet contableRptFiduciaDet);

    void edit(ContableRptFiduciaDet contableRptFiduciaDet);

    void remove(ContableRptFiduciaDet contableRptFiduciaDet);

    ContableRptFiduciaDet find(Object id);

    List<ContableRptFiduciaDet> findAll();

    List<ContableRptFiduciaDet> findRange(int[] range);

    int count();

}
