/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ConsumoEnergia;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface ConsumoEnergiaFacadeLocal {

    void create(ConsumoEnergia consumoEnergia);

    void edit(ConsumoEnergia consumoEnergia);

    void remove(ConsumoEnergia consumoEnergia);

    ConsumoEnergia find(Object id);
    
    ConsumoEnergia findByFecha(Date fecha);
    
    ConsumoEnergia findByFechaAnterior(Date fecha);
    
    ConsumoEnergia findByFechaSiguiente(Date fecha);

    ConsumoEnergia findByFechaAndEstado(Integer idRegistro, Date fecha, Integer idEstado);

    List<ConsumoEnergia> findAll();

    List<ConsumoEnergia> findAllByFecha(Date fechaInicio, Date fechaFin);

    List<ConsumoEnergia> findRange(int[] range);

    int count();

}
