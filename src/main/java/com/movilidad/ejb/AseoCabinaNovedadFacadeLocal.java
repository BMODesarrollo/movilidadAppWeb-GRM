/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AseoCabinaNovedad;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AseoCabinaNovedadFacadeLocal {

    void create(AseoCabinaNovedad aseoCabinaNovedad);

    void edit(AseoCabinaNovedad aseoCabinaNovedad);

    void remove(AseoCabinaNovedad aseoCabinaNovedad);

    AseoCabinaNovedad find(Object id);

    List<AseoCabinaNovedad> findAll();

    List<AseoCabinaNovedad> findRange(int[] range);

    List<AseoCabinaNovedad> findByFechaAndEstadoReg(Date fecha);

    int count();

}
