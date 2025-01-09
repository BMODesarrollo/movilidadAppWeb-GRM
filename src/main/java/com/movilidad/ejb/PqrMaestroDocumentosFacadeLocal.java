/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PqrMaestroDocumentos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PqrMaestroDocumentosFacadeLocal {

    void create(PqrMaestroDocumentos pqrMaestroDocumentos);

    void edit(PqrMaestroDocumentos pqrMaestroDocumentos);

    void remove(PqrMaestroDocumentos pqrMaestroDocumentos);

    PqrMaestroDocumentos find(Object id);

    List<PqrMaestroDocumentos> findAll();
    
    List<PqrMaestroDocumentos> findByIdPqrMaestro(Integer idPqrMaestro);

    List<PqrMaestroDocumentos> findRange(int[] range);

    int count();
    
}
