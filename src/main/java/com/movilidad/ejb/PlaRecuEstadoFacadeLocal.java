package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuEstado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar.beltran
 */
@Local
public interface PlaRecuEstadoFacadeLocal {

    void create(PlaRecuEstado plaRecuEstado);

    void edit(PlaRecuEstado plaRecuEstado);

    void remove(PlaRecuEstado plaRecuEstado);

    PlaRecuEstado find(Object id);

    List<PlaRecuEstado> findAll();
    
    PlaRecuEstado findByName(String categoriaName);

    List<PlaRecuEstado> findRange(int[] range);

    int count();

    List<PlaRecuEstado> estadoReg(int estado);

}

