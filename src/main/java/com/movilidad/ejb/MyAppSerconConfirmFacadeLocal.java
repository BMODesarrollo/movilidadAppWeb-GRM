/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MyAppSerconConfirm;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface MyAppSerconConfirmFacadeLocal {

    void create(MyAppSerconConfirm myAppSerconConfirm);

    void edit(MyAppSerconConfirm myAppSerconConfirm);

    void remove(MyAppSerconConfirm myAppSerconConfirm);

    MyAppSerconConfirm find(Object id);

    List<MyAppSerconConfirm> findAll();

    List<MyAppSerconConfirm> findRange(int[] range);

    int count();

    List<MyAppSerconConfirm> findAllRangoFechas(Date desde, Date hasta);

    MyAppSerconConfirm findByIdEmpledao(Integer idEmpleado, Date fecha);
}
