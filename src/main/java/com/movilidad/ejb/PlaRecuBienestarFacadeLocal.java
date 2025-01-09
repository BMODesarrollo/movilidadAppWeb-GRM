package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuBienestar;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar.beltran
 */
@Local
public interface PlaRecuBienestarFacadeLocal {
    
    void create(PlaRecuBienestar plaRecuBienestar);

    void edit(PlaRecuBienestar plaRecuBienestar);

    void remove(PlaRecuBienestar plaRecuBienestar);

    PlaRecuBienestar find(Object id);

    int count();
    
    List<PlaRecuBienestar> findAll();

    List<PlaRecuBienestar> findAllByFechaRangeAndEstadoReg(Date desde, Date hasta, int estadoReg);
    
    List<PlaRecuBienestar> findAllByFechaRange(Date desde, Date hasta);
    
    List<PlaRecuBienestar> findByEmpleado(int idEmpleado);
    
    List<PlaRecuBienestar> findByMotivo(int idMotivo);
    
    List<PlaRecuBienestar> findByTurno(int idturno);
    
    PlaRecuBienestar findBienestar(Date fechaInicio, Date fechaFin, Integer turno, Integer idEmpleado);
    
    List<PlaRecuBienestar> findByIdGopUnidadFuncional(int idGopUnidadFuncional);
    
    String findRestrictionByIdEmployee(int idEmpl);
}
