/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstEmpresaVisitanteDocs;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface SstEmpresaVisitanteDocsFacadeLocal {

    void create(SstEmpresaVisitanteDocs sstEmpresaVisitanteDocs);

    void edit(SstEmpresaVisitanteDocs sstEmpresaVisitanteDocs);

    void remove(SstEmpresaVisitanteDocs sstEmpresaVisitanteDocs);

    SstEmpresaVisitanteDocs find(Object id);
    
    SstEmpresaVisitanteDocs findUtimoDocumentoActivo(Integer idTipoDocumento,Integer idSstEmpresaVisitante);

    SstEmpresaVisitanteDocs findByFechas(Integer idSstEmpresaDoc, Integer idSstEmpresaVisitante, Integer idSstTipoDocTercero, Date desde, Date hasta);

    List<SstEmpresaVisitanteDocs> findAll();
    
    List<SstEmpresaVisitanteDocs> findAllActivos(Integer idSstEmpresaVisitante);
    
    List<SstEmpresaVisitanteDocs> obtenerHistorico(Integer idTipoDocumento,Integer idSstEmpresaVisitante);

    List<SstEmpresaVisitanteDocs> findRange(int[] range);

    int count();

}
