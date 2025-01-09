/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaEncabezado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AuditoriaEncabezadoFacadeLocal {

    void create(AuditoriaEncabezado auditoriaEncabezado);

    void edit(AuditoriaEncabezado auditoriaEncabezado);

    void remove(AuditoriaEncabezado auditoriaEncabezado);

    AuditoriaEncabezado find(Object id);

    List<AuditoriaEncabezado> findAll();

    List<AuditoriaEncabezado> findRange(int[] range);

    int count();

    List<AuditoriaEncabezado> findByArea(int idArea);

    AuditoriaEncabezado findIdAuditoriaEncabezado(int idAuditoriaEncabezado);

}
