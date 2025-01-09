/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgTcDet;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Soluciones IT
 */
@Local
public interface PrgTcDetFacadeLocal {

    void create(PrgTcDet prgTcDet);

    void edit(PrgTcDet prgTcDet);

    void remove(PrgTcDet prgTcDet);

    PrgTcDet find(Object id);

    List<PrgTcDet> findAll();

    List<PrgTcDet> findByIdPrgTc(int idPrgTc);

    List<PrgTcDet> findRange(int[] range);

    int count();
    
    int removeByDate(Date d);

}
