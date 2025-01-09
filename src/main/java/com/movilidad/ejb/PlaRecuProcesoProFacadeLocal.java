package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuProcesoPro;
import com.movilidad.model.planificacion_recursos.PlaRecuTurno;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar.beltran
 */
@Local
public interface PlaRecuProcesoProFacadeLocal {

    void create(PlaRecuProcesoPro plaRecuProcesoPro);

    void edit(PlaRecuProcesoPro plaRecuProcesoPro);

    void remove(PlaRecuProcesoPro plaRecuProcesoPro);

    PlaRecuProcesoPro find(Object id);

    List<PlaRecuProcesoPro> findAll();
    
    PlaRecuProcesoPro findByDescripcion(String descripcion);

    List<PlaRecuProcesoPro> findRange(int[] range);

    int count();

    List<PlaRecuProcesoPro> estadoReg(int estado);

}
