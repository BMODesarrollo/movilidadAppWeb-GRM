/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgToken;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface PrgTokenFacadeLocal {

    void create(PrgToken prgToken);

    void edit(PrgToken prgToken);

    void remove(PrgToken prgToken);

    PrgToken find(Object id);

    PrgToken login(String token,Integer idEmpleado);

    List<PrgToken> findAll();

    List<PrgToken> findRange(int[] range);

    int count();

    List<PrgToken> findAllEstadoReg();

    PrgToken getValidarProcesoPorDia(Date dSolicitado, Integer idEmpleado);

//    PrgToken getTokenByEmpleado(Date dSolicitado, Integer idEmpleado, String token);

}
