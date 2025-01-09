/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstEmpresa;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface SstEmpresaFacadeLocal {

    void create(SstEmpresa sstEmpresa);

    void edit(SstEmpresa sstEmpresa);

    void remove(SstEmpresa sstEmpresa);

    SstEmpresa find(Object id);

    List<SstEmpresa> findAll();
    
    SstEmpresa login(String emailEmpresa, String usrSst);
    
    SstEmpresa findByRazonSocial(String razon);
    
    SstEmpresa findByNitCedula(String nit);

    List<SstEmpresa> findAllEstadoReg();
    
    List<SstEmpresa> findAllBySeguridad();

    List<SstEmpresa> findRange(int[] range);

    int count();
    
}
