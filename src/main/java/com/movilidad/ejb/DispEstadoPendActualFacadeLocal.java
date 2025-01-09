/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DispEstadoPendActual;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface DispEstadoPendActualFacadeLocal {

    void create(DispEstadoPendActual dispEstadoPendActual);

    void edit(DispEstadoPendActual dispEstadoPendActual);

    void remove(DispEstadoPendActual dispEstadoPendActual);

    DispEstadoPendActual find(Object id);

    List<DispEstadoPendActual> findAll();

    List<DispEstadoPendActual> findRange(int[] range);

    int count();

    DispEstadoPendActual findByNombreByIdVehiculoTipoEstadoDet(String nombre, int id);

    List<DispEstadoPendActual> findAllByEstadoReg();

    /**
     * Permite devolver todos los estados pendientes actuales por
     * id_vehiculo_tipo_estado_det o un(Primer estado) por
     * id_vehiculo_tipo_estado_det.
     *
     * @param id_vehiculo_tipo_estado_det
     * @param all Valor booleano, Sí es true, la consulta arroja un estado
     * pendiente actual parametrizado como primer estado. De lo contrario arroja
     * todos los estados pentiendes actuales
     * @return
     */
    List<DispEstadoPendActual> findFirtStatusByidVehiculoTipoEstadoDetOrAll(int id_vehiculo_tipo_estado_det, boolean all);

    DispEstadoPendActual findEstadoDiferir(int idDispEstadoPendActual);
}
