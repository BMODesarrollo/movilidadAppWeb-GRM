/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgCompania;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Pc
 */
@Local
public interface PrgCompaniaFacadeLocal {

    void create(PrgCompania prgCompania);

    void edit(PrgCompania prgCompania);

    void remove(PrgCompania prgCompania);

    PrgCompania find(Object id);

    List<PrgCompania> findAll();

    List<PrgCompania> findRange(int[] range);

    int count();

    List<PrgCompania> findallEst();

    List<PrgCompania> findCampo(String campo, String value, int id);

}
