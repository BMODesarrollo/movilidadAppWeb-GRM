package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuEjecucion;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar.beltran
 */
@Local
public interface PlaRecuEjecucionFacadeLocal {
    
    void create(PlaRecuEjecucion plaRecuEjecucion);

    void edit(PlaRecuEjecucion plaRecuEjecucion);

    void remove(PlaRecuEjecucion plaRecuEjecucion);

    PlaRecuEjecucion find(Object id);

    int count();
    
    List<PlaRecuEjecucion> findAll();

    List<PlaRecuEjecucion> findAllByFechaRangeAndEstadoReg(Date desde, Date hasta, int estadoReg);
    
    List<PlaRecuEjecucion> findAllByFechaRange(Date desde, Date hasta);
    
    List<PlaRecuEjecucion> findByEmpleado(int idEmpleado);
    
    PlaRecuEjecucion findExecute(Date fechaInicio, Date fechaFin, String horaInicio , String horaFin, Integer idEmpleado);
    
    PlaRecuEjecucion findExecute(Date fechaInicio, Date fechaFin, Integer idEmpleado);
    
    List<PlaRecuEjecucion> findByIdGopUnidadFuncional(int idGopUnidadFuncional);
}
