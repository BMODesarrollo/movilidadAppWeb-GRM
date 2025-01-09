package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuModalidad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar.beltran
 */
@Local
public interface PlaRecuModalidadFacadeLocal {

    void create(PlaRecuModalidad plaRecuModalidad);

    void edit(PlaRecuModalidad plaRecuModalidad);

    void remove(PlaRecuModalidad plaRecuModalidad);

    PlaRecuModalidad find(Object id);

    List<PlaRecuModalidad> findAll();
    
    PlaRecuModalidad findByName(String modalidadName);

    List<PlaRecuModalidad> findRange(int[] range);

    int count();

    List<PlaRecuModalidad> estadoReg(int estado);

}