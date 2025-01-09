package com.movilidad.ejb;

import com.movilidad.model.NovedadDano;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NovedadDanoFacadeLocal {

    void create(NovedadDano novedadDano);

    void edit(NovedadDano novedadDano);

    void remove(NovedadDano novedadDano);

    NovedadDano find(Object id);

    List<NovedadDano> findAll(Date fecha);
    
    List<NovedadDano> findByDateRangeV1(Date fechaIni, Date fechaFin, int idGopUnidadFuncional);

    List<NovedadDano> findByDateRange(Date fechaIni, Date fechaFin, int idGopUnidadFuncional);

    List<NovedadDano> findRange(int[] range);

    int count();
    
    List<NovedadDano> findAllByIdEmpleadoAndDates(Integer idNov, Integer idEmpleado, String desde, String hasta, int idGopUF);

}
