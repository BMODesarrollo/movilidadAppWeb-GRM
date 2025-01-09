/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmPuntosEmpresa;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GenericaPmPuntosEmpresaFacadeLocal {

    void create(GenericaPmPuntosEmpresa genericaPmPuntosEmpresa);

    void edit(GenericaPmPuntosEmpresa genericaPmPuntosEmpresa);

    void remove(GenericaPmPuntosEmpresa genericaPmPuntosEmpresa);

    GenericaPmPuntosEmpresa find(Object id);

    List<GenericaPmPuntosEmpresa> findAll();

    List<GenericaPmPuntosEmpresa> getByIdArea(int idArea);

    List<GenericaPmPuntosEmpresa> findRange(int[] range);

    GenericaPmPuntosEmpresa findByIdAreaAndFecha(int idArea, Date fecha, int idGenericaPmPuntosEmpresa);

    int count();

}
