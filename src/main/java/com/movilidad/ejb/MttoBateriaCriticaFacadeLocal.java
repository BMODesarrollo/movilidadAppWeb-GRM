/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MttoBateriaCritica;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author soluciones-it
 */
@Local
public interface MttoBateriaCriticaFacadeLocal {

    void create(MttoBateriaCritica mttoBateriaCritica);

    void edit(MttoBateriaCritica mttoBateriaCritica);

    void remove(MttoBateriaCritica mttoBateriaCritica);

    MttoBateriaCritica find(Object id);

    List<MttoBateriaCritica> findAll();

    List<MttoBateriaCritica> findRange(int[] range);

    int count();

    List<MttoBateriaCritica> findAllEstadoReg();

    MttoBateriaCritica findByCargaAndIdTipoVehiculo(Integer carga, Integer idVehiculoTipo, Integer idMttoBateriaCritica);
}
