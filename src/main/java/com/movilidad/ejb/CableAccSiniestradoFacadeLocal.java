/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccSiniestrado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface CableAccSiniestradoFacadeLocal {

    void create(CableAccSiniestrado cableAccSiniestrado);

    void edit(CableAccSiniestrado cableAccSiniestrado);

    void remove(CableAccSiniestrado cableAccSiniestrado);

    CableAccSiniestrado find(Object id);

    List<CableAccSiniestrado> findAll();

    List<CableAccSiniestrado> findRange(int[] range);

    int count();

    List<CableAccSiniestrado> findAllEstadoReg(Integer idCableAcc);

}
