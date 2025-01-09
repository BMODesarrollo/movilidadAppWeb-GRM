package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuTurno;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar.beltran
 */
@Local
public interface PlaRecuTurnoFacadeLocal {

    void create(PlaRecuTurno plaRecuTurno);

    void edit(PlaRecuTurno plaRecuTurno);

    void remove(PlaRecuTurno plaRecuTurno);

    PlaRecuTurno find(Object id);

    List<PlaRecuTurno> findAll();
    
    PlaRecuTurno findByName(String turnoName);

    List<PlaRecuTurno> findRange(int[] range);

    int count();

    List<PlaRecuTurno> estadoReg(int estado);

}
