/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableOperacionCabina;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface CableOperacionCabinaFacadeLocal {

    void create(CableOperacionCabina cableOperacionCabina);

    void edit(CableOperacionCabina cableOperacionCabina);

    void remove(CableOperacionCabina cableOperacionCabina);

    CableOperacionCabina find(Object id);

    List<CableOperacionCabina> findAll();

    List<CableOperacionCabina> findRange(int[] range);

    int count();

    CableOperacionCabina findByIdCableCabinaAndFecha(int id, Date fecha);

    List<CableOperacionCabina> findAllByFecha(Date fecha, String order);

    List<CableOperacionCabina> findFechaAndOperando(Date fecha, String order, int operando);

    List<CableOperacionCabina> findByRangoFecha(Date fecha, Date hasta);

}
