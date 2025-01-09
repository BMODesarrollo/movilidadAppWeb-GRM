package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuEntregaOperador;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar.beltran
 */
@Local
public interface PlaRecuEntregaOperadorFacadeLocal {

    void create(PlaRecuEntregaOperador forDesEntregaOperadores);

    void edit(PlaRecuEntregaOperador forDesEntregaOperadores);

    void remove(PlaRecuEntregaOperador forDesEntregaOperadores);

    PlaRecuEntregaOperador find(Object id);
    
    PlaRecuEntregaOperador find(int id, Date fecha_ascenso);

    List<PlaRecuEntregaOperador> findAll();
    
    List<PlaRecuEntregaOperador> findByCategoria(String idCategoria);
    
    PlaRecuEntregaOperador findByName(String procesoName);

    List<PlaRecuEntregaOperador> findRange(int[] range);

    int count();

    List<PlaRecuEntregaOperador> estadoReg(int estado);
    
    List<PlaRecuEntregaOperador> findByIdGopUnidadFuncional(int idGopUnidadFuncional);

}