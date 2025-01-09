/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstEmpresaDocs;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface SstEmpresaDocsFacadeLocal {

    void create(SstEmpresaDocs sstEmpresaDocs);

    void edit(SstEmpresaDocs sstEmpresaDocs);

    void remove(SstEmpresaDocs sstEmpresaDocs);

    SstEmpresaDocs find(Object id);
    
    SstEmpresaDocs findUtimoDocumentoActivo(Integer idTipoDocumento,Integer idSstEmpresa);
    
    SstEmpresaDocs findByFechas(Integer idSstEmpresaDoc, Integer idSstEmpresa, Integer idSstEmpresaTipoDoc, Date desde, Date hasta);

    List<SstEmpresaDocs> findAll();
    
    List<SstEmpresaDocs> findAllActivos(Integer idSstEmpresa);
    
    List<SstEmpresaDocs> obtenerHistorico(Integer idTipoDocumento,Integer idSstEmpresa);

    List<SstEmpresaDocs> findRange(int[] range);

    int count();
    
}
