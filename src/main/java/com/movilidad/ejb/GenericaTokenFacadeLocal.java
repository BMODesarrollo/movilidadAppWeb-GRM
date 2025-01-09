/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaToken;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface GenericaTokenFacadeLocal {

    void create(GenericaToken genericaToken);

    void edit(GenericaToken genericaToken);

    void remove(GenericaToken genericaToken);

    GenericaToken find(Object id);

    List<GenericaToken> findAll();

    List<GenericaToken> findRange(int[] range);

    int count();

    GenericaToken login(String token, Integer idEmpleado);

    List<GenericaToken> findAllEstadoReg();

    GenericaToken getValidarProcesoPorDia(Date dSolicitado, Integer idEmpleado);
}
