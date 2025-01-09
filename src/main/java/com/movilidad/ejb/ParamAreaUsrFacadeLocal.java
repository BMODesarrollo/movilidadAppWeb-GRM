/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ParamAreaUsr;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface ParamAreaUsrFacadeLocal {

    void create(ParamAreaUsr paramAreaUsr);

    void edit(ParamAreaUsr paramAreaUsr);

    void remove(ParamAreaUsr paramAreaUsr);

    ParamAreaUsr find(Object id);

    ParamAreaUsr getByIdUser(String username);

    List<ParamAreaUsr> findAll();

    List<ParamAreaUsr> findRange(int[] range);

    int count();

    List<ParamAreaUsr> findAllEstadoReg();

}
