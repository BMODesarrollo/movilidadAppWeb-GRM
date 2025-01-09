/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Config;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface ConfigFacadeLocal {

    void create(Config config);

    void edit(Config config);

    void remove(Config config);

    Config find(Object id);

    Config findByKey(String key);

    List<Config> findAll();

    List<Config> findRange(int[] range);

    int count();

}
