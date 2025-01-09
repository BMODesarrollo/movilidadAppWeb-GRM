/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmTamplitudHoras;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface PmTamplitudHorasFacadeLocal {

    void create(PmTamplitudHoras pmTamplitudHoras);

    void edit(PmTamplitudHoras pmTamplitudHoras);

    void remove(PmTamplitudHoras pmTamplitudHoras);

    PmTamplitudHoras find(Object id);

    PmTamplitudHoras findByFecha(Date desde, Date hasta, int idGopUnidadFuncional);

    List<PmTamplitudHoras> findAll();

    List<PmTamplitudHoras> findRange(int[] range);

    List<PmTamplitudHoras> cargarEstadoRegistro();

    int count();

}
