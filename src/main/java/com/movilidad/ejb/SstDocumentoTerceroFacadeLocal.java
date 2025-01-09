/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstDocumentoTercero;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface SstDocumentoTerceroFacadeLocal {

    void create(SstDocumentoTercero sstDocumentoTercero);

    void edit(SstDocumentoTercero sstDocumentoTercero);

    void remove(SstDocumentoTercero sstDocumentoTercero);

    SstDocumentoTercero find(Object id);

    SstDocumentoTercero findByTipoDocumento(String tipo);

    List<SstDocumentoTercero> findAll();

    List<SstDocumentoTercero> findAllEstadoReg();
    
    List<SstDocumentoTercero> findAllTiposVigentes();

    List<SstDocumentoTercero> findRange(int[] range);

    int count();

}
