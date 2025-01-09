package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuConduccion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar.beltran
 */
@Local
public interface PlaRecuConduccionFacadeLocal {

    void create(PlaRecuConduccion plaRecuConduccion);

    void edit(PlaRecuConduccion plaRecuConduccion);

    void remove(PlaRecuConduccion plaRecuConduccion);

    PlaRecuConduccion find(Object id);

    List<PlaRecuConduccion> findAll();
    
    PlaRecuConduccion findByDescripcion(String conduccionDescripcion);

    List<PlaRecuConduccion> findRange(int[] range);

    int count();

    List<PlaRecuConduccion> estadoReg(int estado);

}
