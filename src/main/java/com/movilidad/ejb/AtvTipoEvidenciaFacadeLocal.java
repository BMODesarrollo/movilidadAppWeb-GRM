/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvTipoEvidencia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface AtvTipoEvidenciaFacadeLocal {

    void create(AtvTipoEvidencia atvTipoEvidencia);

    void edit(AtvTipoEvidencia atvTipoEvidencia);

    void remove(AtvTipoEvidencia atvTipoEvidencia);

    AtvTipoEvidencia find(Object id);
    
    AtvTipoEvidencia findByNombre(Integer idRegistro,String nombre);

    List<AtvTipoEvidencia> findAll();
    
    List<AtvTipoEvidencia> findAllByEstadoReg();

    List<AtvTipoEvidencia> findRange(int[] range);

    int count();
    
}
