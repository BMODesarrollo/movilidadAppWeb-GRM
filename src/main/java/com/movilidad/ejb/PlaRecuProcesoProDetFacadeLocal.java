package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuProcesoProDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar.beltran
 */
@Local
public interface PlaRecuProcesoProDetFacadeLocal {

    void create(PlaRecuProcesoProDet plaRecuProcesoPro);

    void edit(PlaRecuProcesoProDet plaRecuProcesoPro);

    void remove(PlaRecuProcesoProDet plaRecuProcesoPro);

    PlaRecuProcesoProDet find(Object id);

    List<PlaRecuProcesoProDet> findAll();
    
    PlaRecuProcesoProDet findByDescripcion(String descripcion);

    List<PlaRecuProcesoProDet> findRange(int[] range);

    int count();

    List<PlaRecuProcesoProDet> estadoReg(int estado);

}
