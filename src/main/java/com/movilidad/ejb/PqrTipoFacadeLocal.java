/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PqrTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PqrTipoFacadeLocal {

    void create(PqrTipo pqrTipo);

    void edit(PqrTipo pqrTipo);

    void remove(PqrTipo pqrTipo);

    PqrTipo find(Object id);
    
    PqrTipo verificarRegistro(Integer idRegistro, String nombre);

    List<PqrTipo> findAll();
    
    List<PqrTipo> findAllByEstadoReg();

    List<PqrTipo> findRange(int[] range);

    int count();
    
}
