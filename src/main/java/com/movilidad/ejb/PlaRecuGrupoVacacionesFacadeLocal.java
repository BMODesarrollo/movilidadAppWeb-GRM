package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuGrupoVacaciones;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar.beltran
 */
@Local
public interface PlaRecuGrupoVacacionesFacadeLocal {
    
    void create(PlaRecuGrupoVacaciones plaRecuGrupoVacaciones);

    void edit(PlaRecuGrupoVacaciones plaRecuGrupoVacaciones);

    void remove(PlaRecuGrupoVacaciones plaRecuGrupoVacaciones);

    PlaRecuGrupoVacaciones find(Object id);

    int count();
    
    List<PlaRecuGrupoVacaciones> findAll();

    List<PlaRecuGrupoVacaciones> findAllByFechaRangeAndEstadoReg(Date desde, Date hasta, int estadoReg);
    
    List<PlaRecuGrupoVacaciones> findAllByFechaRange(Date desde, Date hasta);
    
    PlaRecuGrupoVacaciones findByName(String grupo, Integer idUF);
    
    List<PlaRecuGrupoVacaciones> findByIdGopUnidadFuncional(int idGopUnidadFuncional);
}

