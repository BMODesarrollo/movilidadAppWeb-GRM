package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuReprogramacionPAA;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar.beltran
 */
@Local
public interface PlaRecuReprogramacionPAAFacadeLocal {
    
    void create(PlaRecuReprogramacionPAA plaRecuReprogramacionPAA);

    void edit(PlaRecuReprogramacionPAA plaRecuReprogramacionPAA);

    void remove(PlaRecuReprogramacionPAA plaRecuReprogramacionPAA);

    PlaRecuReprogramacionPAA find(Object id);

    int count();
    
    List<PlaRecuReprogramacionPAA> findAll();
    
    List<PlaRecuReprogramacionPAA> findAllActiveDays();
    
    List<PlaRecuReprogramacionPAA> findByEmpleado(int idEmpleado);
        
    PlaRecuReprogramacionPAA findReprogramacionPAA(Integer idEmpleado);
    
    List<PlaRecuReprogramacionPAA> findByIdGopUnidadFuncional(int idGopUnidadFuncional);
}
