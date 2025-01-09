/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgVehicleStatus;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author luis
 */
@Local
public interface PrgVehicleStatusFacadeLocal {

    void create(PrgVehicleStatus prgVehicleStatus);

    void edit(PrgVehicleStatus prgVehicleStatus);

    void remove(PrgVehicleStatus prgVehicleStatus);

    PrgVehicleStatus find(Object id);

    List<PrgVehicleStatus> findAll();

    List<PrgVehicleStatus> findByDate(Date fechaIni, Date fechaFin);

    List<PrgVehicleStatus> findRange(int[] range);

    long countByFechasByIdGopUnidadFuncional(Date fromDate, Date toDate, Integer idGopUnidadFunc);

    int count();

    public List<PrgVehicleStatus> findPrgNextDay(Date fecha);

    public List<PrgVehicleStatus> findPrgNextDayPorDistanciaPasoII(Date fecha, int idPatio);

    public List<PrgVehicleStatus> findPrgPatiosNextDay(Date fecha);

    public List<PrgVehicleStatus> findPrgEnPatioNextDay(Date fecha, int idPatio, String desde, String hasta);

    public PrgVehicleStatus getFromDepotOrToDepotByServbus(Date fecha, String servbus, int opc);

    int removeByDate(Date d);

}
