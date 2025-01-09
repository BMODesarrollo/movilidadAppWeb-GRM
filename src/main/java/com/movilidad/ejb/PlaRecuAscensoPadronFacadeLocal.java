package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuAscensoPadron;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar.beltran
 */
@Local
public interface PlaRecuAscensoPadronFacadeLocal {

    void create(PlaRecuAscensoPadron forDesAscensoPadron);

    void edit(PlaRecuAscensoPadron forDesAscensoPadron);

    void remove(PlaRecuAscensoPadron forDesAscensoPadron);

    PlaRecuAscensoPadron find(Object id);
    
    PlaRecuAscensoPadron find(int id, Date fecha_ascenso);

    List<PlaRecuAscensoPadron> findAll();
    
    PlaRecuAscensoPadron findByName(String procesoName);

    List<PlaRecuAscensoPadron> findRange(int[] range);

    int count();

    List<PlaRecuAscensoPadron> estadoReg(int estado);

    List<PlaRecuAscensoPadron> findByIdGopUnidadFuncional(int idGopUnidadFuncional);
}