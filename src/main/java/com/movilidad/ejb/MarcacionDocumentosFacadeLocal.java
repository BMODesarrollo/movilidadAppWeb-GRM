package com.movilidad.ejb;

import com.movilidad.model.MarcacionDocumentos;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 * @author Omar.beltran
 */
@Local
public interface MarcacionDocumentosFacadeLocal {
    void create(MarcacionDocumentos marcacionDocumentos);

    void edit(MarcacionDocumentos empleadoDocumentos);

    void remove(MarcacionDocumentos empleadoDocumentos);

    MarcacionDocumentos find(Object id);

    List<MarcacionDocumentos> findAll();

    int count();

    MarcacionDocumentos findByIdEmpleado(int idEmpleado);

    List<MarcacionDocumentos> findAllActivos();

    MarcacionDocumentos findByIdEmpleadoAndFecha(int idEmpleado, Date fecha);

}
