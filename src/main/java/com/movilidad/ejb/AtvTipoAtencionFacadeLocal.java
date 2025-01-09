/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvTipoAtencion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AtvTipoAtencionFacadeLocal {

    void create(AtvTipoAtencion atvTipoAtencion);

    void edit(AtvTipoAtencion atvTipoAtencion);

    void remove(AtvTipoAtencion atvTipoAtencion);

    AtvTipoAtencion find(Object id);

    AtvTipoAtencion findByNombre(String nombre, int idAtvTipoAtencion);

    List<AtvTipoAtencion> findAll();

    List<AtvTipoAtencion> findRange(int[] range);

    List<AtvTipoAtencion> findAllByEstadoReg();

    int count();

}
