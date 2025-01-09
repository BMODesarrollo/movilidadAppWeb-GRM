package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuProceso;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar.beltran
 */
@Local
public interface PlaRecuProcesoFacadeLocal {

    void create(PlaRecuProceso plaRecuProceso);

    void edit(PlaRecuProceso plaRecuProceso);

    void remove(PlaRecuProceso plaRecuProceso);

    PlaRecuProceso find(Object id);

    List<PlaRecuProceso> findAll();
    
    PlaRecuProceso findByName(String procesoName);

    List<PlaRecuProceso> findRange(int[] range);

    int count();

    List<PlaRecuProceso> estadoReg(int estado);

}