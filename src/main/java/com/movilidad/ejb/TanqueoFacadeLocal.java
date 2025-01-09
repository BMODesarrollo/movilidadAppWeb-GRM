/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Tanqueo;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface TanqueoFacadeLocal {

    void create(Tanqueo tanqueo);

    void edit(Tanqueo tanqueo);

    void remove(Tanqueo tanqueo);

    Tanqueo find(Object id);

    int count();

    Tanqueo findUltimoTanqueo(Integer i);

    List<Tanqueo> findTanqueoDates(Date ini, Date fin);

    List<Tanqueo> findTanqueoIngresado(Date ingresado, Integer i);
}
