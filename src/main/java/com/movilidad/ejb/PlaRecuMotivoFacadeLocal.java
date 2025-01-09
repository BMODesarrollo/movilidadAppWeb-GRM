package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuMotivo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar.beltran
 */
@Local
public interface PlaRecuMotivoFacadeLocal {

    void create(PlaRecuMotivo plaRecuMotivo);

    void edit(PlaRecuMotivo plaRecuMotivo);

    void remove(PlaRecuMotivo plaRecuMotivo);

    PlaRecuMotivo find(Object id);

    List<PlaRecuMotivo> findAll();
    
    PlaRecuMotivo findByName(String motivoName);

    List<PlaRecuMotivo> findRange(int[] range);

    int count();

    List<PlaRecuMotivo> estadoReg(int estado);
    
}