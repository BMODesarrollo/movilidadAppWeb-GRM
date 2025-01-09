package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuCategoria;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar.beltran
 */
@Local
public interface PlaRecuCategoriaFacadeLocal {

    void create(PlaRecuCategoria plaRecuCategoria);

    void edit(PlaRecuCategoria plaRecuCategoria);

    void remove(PlaRecuCategoria plaRecuCategoria);

    PlaRecuCategoria find(Object id);

    List<PlaRecuCategoria> findAll();
    
    PlaRecuCategoria findByName(String categoriaName);

    List<PlaRecuCategoria> findRange(int[] range);

    int count();

    List<PlaRecuCategoria> estadoReg(int estado);

}
