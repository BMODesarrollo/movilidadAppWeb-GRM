/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ConfigEmpresa;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface ConfigEmpresaFacadeLocal {

    void create(ConfigEmpresa configEmpresa);

    void edit(ConfigEmpresa configEmpresa);

    void remove(ConfigEmpresa configEmpresa);

    ConfigEmpresa find(Object id);

    List<ConfigEmpresa> findAll();

    List<ConfigEmpresa> findRange(int[] range);

    List<ConfigEmpresa> findEstadoReg();

    int count();

    ConfigEmpresa findByLlave(String key);

}
