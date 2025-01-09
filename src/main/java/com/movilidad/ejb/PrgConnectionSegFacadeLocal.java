/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgConnectionSeg;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author luis
 */
@Local
public interface PrgConnectionSegFacadeLocal {

    void create(PrgConnectionSeg prgConnectionSeg);

    void edit(PrgConnectionSeg prgConnectionSeg);

    void remove(PrgConnectionSeg prgConnectionSeg);

    PrgConnectionSeg find(Object id);

    List<PrgConnectionSeg> findAll();

    List<PrgConnectionSeg> findRange(int[] range);

    int count();
    
}
