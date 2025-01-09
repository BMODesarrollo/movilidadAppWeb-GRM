/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccPre;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface AccPreFacadeLocal {

    void create(AccPre accPre);

    void edit(AccPre accPre);

    void remove(AccPre accPre);

    AccPre find(Object id);

    List<AccPre> findAll();

    List<AccPre> findByFecha(Date fechaInicio, Date fechaFin);

    List<AccPre> findRange(int[] range);

    int count();

    boolean verificarAccidente(int idNovedad);

}
