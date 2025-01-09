package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuVacaciones;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar.beltran
 */
@Local
public interface PlaRecuVacacionesFacadeLocal {
    
    void create(PlaRecuVacaciones plaRecuVacaciones);

    void edit(PlaRecuVacaciones plaRecuVacaciones);

    void remove(PlaRecuVacaciones plaRecuVacaciones);

    PlaRecuVacaciones find(Object id);

    int count();
    
    List<PlaRecuVacaciones> findAll();

    List<PlaRecuVacaciones> findAllByFechaRangeAndEstadoReg(Date desde, Date hasta, int estadoReg);
    
    List<PlaRecuVacaciones> findAllByFechaRange(Date desde, Date hasta);
    
    PlaRecuVacaciones findByEmpleado(int idEmpleado);
    
    PlaRecuVacaciones findVacaciones(Date fechaInicio, Date fechaFin, Integer idEmpleado);
    
    List<PlaRecuVacaciones> findByIdGopUnidadFuncional(int idGopUnidadFuncional);
}
