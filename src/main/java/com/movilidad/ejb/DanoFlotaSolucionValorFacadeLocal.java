/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DanoFlotaSolucionValor;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Julián Arévalo
 */
@Local
public interface DanoFlotaSolucionValorFacadeLocal {

    void create(DanoFlotaSolucionValor danoFlotaSolucionValor);

    void edit(DanoFlotaSolucionValor danoFlotaSolucionValor);

    void remove(DanoFlotaSolucionValor danoFlotaSolucionValor);

    DanoFlotaSolucionValor find(Object id);

    List<DanoFlotaSolucionValor> findAll();

    List<DanoFlotaSolucionValor> findRange(int[] range);

    int count();

    List<DanoFlotaSolucionValor> getAllActivo();
    
    List<DanoFlotaSolucionValor> findPieces(String piezas, Integer vehiculoTipoId);
    
    List<DanoFlotaSolucionValor> findSolucionesByComponente(Long idComponente);
       
    
}
