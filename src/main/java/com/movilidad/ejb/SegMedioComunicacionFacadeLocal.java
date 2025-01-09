/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegMedioComunicacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface SegMedioComunicacionFacadeLocal {

    void create(SegMedioComunicacion segMedioComunicacion);

    void edit(SegMedioComunicacion segMedioComunicacion);

    void remove(SegMedioComunicacion segMedioComunicacion);

    SegMedioComunicacion find(Object id);

    SegMedioComunicacion findByCodigo(String codigo, Integer idRegistro);

    SegMedioComunicacion findBySerial(String serial, Integer idRegistro);
    
    SegMedioComunicacion findByImei(String imei, Integer idRegistro);

    List<SegMedioComunicacion> findAll();

    List<SegMedioComunicacion> findByEstadoReg();

    List<SegMedioComunicacion> findRange(int[] range);

    int count();

}
