package com.movilidad.ejb;

import com.movilidad.model.Vehiculo;
import com.movilidad.model.VehiculoIdf;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface VehiculoIdfFacadeLocal {

    void create(VehiculoIdf vehiculoIdf);

    void edit(VehiculoIdf vehiculoIdf);

    void remove(VehiculoIdf vehiculoIdf);

    VehiculoIdf find(Object id);

    List<VehiculoIdf> findByFecha(Date fecha);

    List<VehiculoIdf> findByRangoFecha(Date fechaInicio, Date fechaFin);

    VehiculoIdf findByFechaAndIdVehiculo(Date fecha, int idVehiculo);

    Vehiculo findVehiculo(int id);

    List<VehiculoIdf> findAll();

    List<VehiculoIdf> findRange(int[] range);

    int count();

    boolean verificarSubida(Date fecha, Date fechaFin);

}
