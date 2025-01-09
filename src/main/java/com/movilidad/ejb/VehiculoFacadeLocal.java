/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.dto.ParrillaDTO;
import com.movilidad.model.Vehiculo;
import com.movilidad.util.beans.ResEstadoActFlota;
import com.movilidad.util.beans.SumDistanciaEntradaPatioDTO;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface VehiculoFacadeLocal {

    void create(Vehiculo vehiculo);

    void edit(Vehiculo vehiculo);

    void remove(Vehiculo vehiculo);

    Vehiculo find(Object id);

    List<String> getVehiculos();

    List<Vehiculo> findAll();

    List<Vehiculo> getVehiclosActivo();

    List<Vehiculo> getVehiclosByType(int tipo);

    List<Vehiculo> findRange(int[] range);

    int count();

    int getVehiculosDisponibles(int idGopUnidadFuncional);

    int getVehiculosOperando(Date fecha, int idGopUnidadFuncional);

    Vehiculo getVehiculo(String codigo, int idGopUnidadFuncional);

    Vehiculo getVehiculoCodigo(String s);

    Vehiculo getVehiculoPlaca(String placa);

    List<Vehiculo> getParametros(String campo, String valor, Integer id);

    List<Vehiculo> getDisponibles(Date fecha, int idGopUnidadFuncional);

    List<ResEstadoActFlota> getResumenEstadoActualFlota(int idGopUnidadFuncional);

    Vehiculo findVehiculoByCod(String codigo, Date fecha, int idGopUnidadFuncional);

    Vehiculo findVehiculoExist(String codigo, int idGopUnidadFuncional);
    
    String esVehiculoRequerido(int idVehiculo, Date fecha);

    Vehiculo findByCodigo(String codigo);

    List<Vehiculo> findAllVehiculosByidGopUnidadFuncional(int idGopUnidadFuncional);

    int updateEstadoVehiculo(int idVehiculo, int idVehiculoTipoEstado);

    List<Vehiculo> findByidGopUnidadFuncAndTipo(int idGopUnidadFuncional, int idVehiculoTipo);

    Long totalDisponibles(Date fecha, int idGopUnidadFuncional);

    List<ParrillaDTO> findStatesVehicleServices(Integer idGopUF);

    /**
     * Permite consultar la sumatoria de distancia del dia -now()- por vehiculo
     * por unidad funcional
     *
     * @param idGopUF
     * @return
     */
    List<SumDistanciaEntradaPatioDTO> findSumDistanciaForVehiculo(Integer idGopUF);
}
