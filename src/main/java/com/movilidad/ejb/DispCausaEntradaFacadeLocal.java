/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DispCausaEntrada;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface DispCausaEntradaFacadeLocal {

    void create(DispCausaEntrada dispCausaEntrada);

    void edit(DispCausaEntrada dispCausaEntrada);

    void remove(DispCausaEntrada dispCausaEntrada);

    DispCausaEntrada find(Object id);

    List<DispCausaEntrada> findAll();

    List<DispCausaEntrada> findRange(int[] range);

    int count();

    DispCausaEntrada findByNombreByIdDispSistema(String nombre, int id, int idDispSistema);

    List<DispCausaEntrada> findByEstadoReg();

    List<DispCausaEntrada> findAllByIdDispSistema(int idDispSistema);
}
