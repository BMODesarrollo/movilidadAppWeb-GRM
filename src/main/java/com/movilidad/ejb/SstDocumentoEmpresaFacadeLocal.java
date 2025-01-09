/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstDocumentoEmpresa;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface SstDocumentoEmpresaFacadeLocal {

    void create(SstDocumentoEmpresa sstDocumentoEmpresa);

    void edit(SstDocumentoEmpresa sstDocumentoEmpresa);

    void remove(SstDocumentoEmpresa sstDocumentoEmpresa);

    SstDocumentoEmpresa find(Object id);

    SstDocumentoEmpresa findByTipoDoc(String tipo);

    List<SstDocumentoEmpresa> findAll();
    
    List<SstDocumentoEmpresa> findAllEstadoReg();
    
    List<SstDocumentoEmpresa> findAllTiposVigentes();

    List<SstDocumentoEmpresa> findRange(int[] range);

    int count();
    
}
