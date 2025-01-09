/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DanoFlotaComponente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Julián Arévalo
 */
@Local
public interface DanoFlotaComponenteFacadeLocal {

    void create(DanoFlotaComponente danoFlotaComponente);

    void edit(DanoFlotaComponente danoFlotaComponente);

    void remove(DanoFlotaComponente danoFlotaComponente);

    DanoFlotaComponente find(Object id);

    List<DanoFlotaComponente> findAll();

    List<DanoFlotaComponente> findRange(int[] range);

    int count();

    List<DanoFlotaComponente> getAllActivo();
    
    List<DanoFlotaComponente> findPieces(String piezas, Integer vehiculoTipoId);
    
    
}
