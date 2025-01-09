/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AseoCabina;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AseoCabinaFacadeLocal {

    void create(AseoCabina aseoCabina);

    void edit(AseoCabina aseoCabina);

    void remove(AseoCabina aseoCabina);

    AseoCabina find(Object id);

    List<AseoCabina> findAll();

    List<AseoCabina> findRange(int[] range);

    List<AseoCabina> findAllByFechaEstadoReg(Date desde, Date hasta);

    int count();

    AseoCabina findLastByIdAndFecha(int id, Date fecha);

    void limpiarAseoCabinaByIdAseoCabinaAndFecha(int idAseoCabina, Date fecha);

    void limpiarTodoAseoCabinaByFecha(Date fecha);
}
