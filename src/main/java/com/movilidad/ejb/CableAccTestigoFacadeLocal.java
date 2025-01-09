/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccTestigo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface CableAccTestigoFacadeLocal {

    void create(CableAccTestigo cableAccTestigo);

    void edit(CableAccTestigo cableAccTestigo);

    void remove(CableAccTestigo cableAccTestigo);

    CableAccTestigo find(Object id);

    List<CableAccTestigo> findAll();

    List<CableAccTestigo> findRange(int[] range);

    int count();

    List<CableAccTestigo> findAllEstadoReg(Integer idCableAcc);

}
