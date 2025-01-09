/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ContableRptFiduciaDist;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface ContableRptFiduciaDistFacadeLocal {

    void create(ContableRptFiduciaDist contableRptFiduciaDist);

    void edit(ContableRptFiduciaDist contableRptFiduciaDist);

    void remove(ContableRptFiduciaDist contableRptFiduciaDist);

    ContableRptFiduciaDist find(Object id);

    List<ContableRptFiduciaDist> findAll();

    List<ContableRptFiduciaDist> findRange(int[] range);

    int count();
    
}
