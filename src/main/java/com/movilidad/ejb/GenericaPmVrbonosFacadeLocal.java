/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmVrbonos;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GenericaPmVrbonosFacadeLocal {

    void create(GenericaPmVrbonos genericaPmVrbonos);

    void edit(GenericaPmVrbonos genericaPmVrbonos);

    void remove(GenericaPmVrbonos genericaPmVrbonos);

    GenericaPmVrbonos find(Object id);

    List<GenericaPmVrbonos> findAll();

    List<GenericaPmVrbonos> findRange(int[] range);

    List<GenericaPmVrbonos> getByIdArea(int idArea);

    GenericaPmVrbonos findByIdAreaAndFecha(int idArea, Date fecha, int idGenericaPmDiasEmpresa,int idEmpeladoTipoCargo);

    int count();

}
