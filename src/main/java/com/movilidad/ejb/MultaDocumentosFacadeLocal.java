/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MultaDocumentos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface MultaDocumentosFacadeLocal {

    void create(MultaDocumentos multaDocumentos);

    void edit(MultaDocumentos multaDocumentos);

    void remove(MultaDocumentos multaDocumentos);

    MultaDocumentos find(Object id);

    List<MultaDocumentos> findAll();
    
    List<MultaDocumentos> estadoRegistro();
    
    List<MultaDocumentos> idMultaEstadoRegistro(int i);

    List<MultaDocumentos> findRange(int[] range);

    int count();
    
}
