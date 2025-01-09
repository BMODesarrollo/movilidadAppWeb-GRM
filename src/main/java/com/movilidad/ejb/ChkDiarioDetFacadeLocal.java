/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ChkDiarioDet;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface ChkDiarioDetFacadeLocal {

    void create(ChkDiarioDet chkDiarioDet);

    void edit(ChkDiarioDet chkDiarioDet);

    void remove(ChkDiarioDet chkDiarioDet);

    ChkDiarioDet find(Object id);

    List<ChkDiarioDet> findAll();
    
    List<ChkDiarioDet> findByIdChkDiario(Integer idChkDiario);

    List<ChkDiarioDet> findRange(int[] range);

    int count();
    
}
