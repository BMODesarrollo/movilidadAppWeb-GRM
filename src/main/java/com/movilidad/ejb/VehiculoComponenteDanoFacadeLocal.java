/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoComponenteDano;
import com.movilidad.model.VehiculoComponenteGrupo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface VehiculoComponenteDanoFacadeLocal {

    void create(VehiculoComponenteDano vehiculoComponenteDano);

    void edit(VehiculoComponenteDano vehiculoComponenteDano);

    void remove(VehiculoComponenteDano vehiculoComponenteDano);

    VehiculoComponenteDano find(Object id);

    List<VehiculoComponenteDano> findByCteGrupo(VehiculoComponenteGrupo componenteGrupo);

    List<VehiculoComponenteDano> findAll();

    List<VehiculoComponenteDano> findRange(int[] range);

    int count();

    VehiculoComponenteDano findByIdCompGrupoAndIdVehiculoDano(int idComponenteGrupo, int idVehiculoDano, int idVehiculoComponenteDano);
}
