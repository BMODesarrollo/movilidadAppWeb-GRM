package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuSeguridad;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar.beltran
 */
@Local
public interface PlaRecuSeguridadFacadeLocal {
    
    void create(PlaRecuSeguridad plaRecuSeguridad);

    void edit(PlaRecuSeguridad plaRecuSeguridad);

    void remove(PlaRecuSeguridad plaRecuSeguridad);

    PlaRecuSeguridad find(Object id);

    int count();
    
    List<PlaRecuSeguridad> findAll();

    List<PlaRecuSeguridad> findAllByFechaRangeAndEstadoReg(Date desde, Date hasta, int estadoReg);
    
    List<PlaRecuSeguridad> findAllByFechaRange(Date desde, Date hasta);
    
    List<PlaRecuSeguridad> findByEmpleado(int idEmpleado);
    
    PlaRecuSeguridad findSeguridad(Date fechaInicio, Date fechaFin,String descripcion ,Integer idEmpleado);
    
    List<PlaRecuSeguridad> findByIdGopUnidadFuncional(int idGopUnidadFuncional);
}
