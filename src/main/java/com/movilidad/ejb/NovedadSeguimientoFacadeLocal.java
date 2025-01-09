package com.movilidad.ejb;

import com.movilidad.model.NovedadSeguimiento;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NovedadSeguimientoFacadeLocal {

    void create(NovedadSeguimiento novedadSeguimiento);

    void edit(NovedadSeguimiento novedadSeguimiento);

    void remove(NovedadSeguimiento novedadSeguimiento);

    NovedadSeguimiento find(Object id);

    List<NovedadSeguimiento> findByNovedad(int id);

    List<NovedadSeguimiento> findAll();

    List<NovedadSeguimiento> findRange(int[] range);

    int count();

}
