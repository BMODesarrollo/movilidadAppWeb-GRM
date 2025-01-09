/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.movilidad.ejb;

import com.movilidad.model.PqrProcede;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ricardo.lopez
 */
@Local
public interface PqrProcedeFacadeLocal {

    void create(PqrProcede PqrProcede);

    void edit(PqrProcede PqrProcede);

    void remove(PqrProcede PqrProcede);

    PqrProcede find(Object id);

    List<PqrProcede> findAll();

    List<PqrProcede> findRange(int[] range);

    int count();

//    List<PqrProcede> obj_proc_esta();

}
