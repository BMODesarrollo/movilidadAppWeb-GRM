/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmDiasEmpresa;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GenericaPmDiasEmpresaFacadeLocal {

    void create(GenericaPmDiasEmpresa genericaPmDiasEmpresa);

    void edit(GenericaPmDiasEmpresa genericaPmDiasEmpresa);

    void remove(GenericaPmDiasEmpresa genericaPmDiasEmpresa);

    GenericaPmDiasEmpresa find(Object id);

    List<GenericaPmDiasEmpresa> findAll();

    List<GenericaPmDiasEmpresa> findRange(int[] range);

    List<GenericaPmDiasEmpresa> getByIdArea(int idArea);

    GenericaPmDiasEmpresa findByIdAreaAndFecha(int idArea, Date fecha, int idGenericaPmDiasEmpresa);

    int count();

}
