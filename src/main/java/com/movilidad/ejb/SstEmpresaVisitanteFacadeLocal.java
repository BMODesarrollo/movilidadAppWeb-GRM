/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstEmpresaVisitante;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface SstEmpresaVisitanteFacadeLocal {

    void create(SstEmpresaVisitante sstEmpresaVisitante);

    void edit(SstEmpresaVisitante sstEmpresaVisitante);

    void remove(SstEmpresaVisitante sstEmpresaVisitante);

    SstEmpresaVisitante find(Object id);

    SstEmpresaVisitante findByNumDocumento(String numDocumento);

    List<SstEmpresaVisitante> findAll();

    List<SstEmpresaVisitante> findAllEstadoReg();

    List<SstEmpresaVisitante> findAllByEmpresa(Integer idEmpresa);

    List<SstEmpresaVisitante> findAllAprobados();

    List<SstEmpresaVisitante> findRange(int[] range);

    int count();

    List<SstEmpresaVisitante> findAllAprobadosByEmpresa(Integer idSstEmpresa);

    SstEmpresaVisitante findByHashString(String hashString);

}
