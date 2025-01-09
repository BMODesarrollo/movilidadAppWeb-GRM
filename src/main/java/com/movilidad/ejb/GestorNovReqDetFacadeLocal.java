/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestorNovReqDet;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface GestorNovReqDetFacadeLocal {

    void create(GestorNovReqDet gestorNovReqDet);

    void edit(GestorNovReqDet gestorNovReqDet);

    void remove(GestorNovReqDet gestorNovReqDet);
    
    void llenarTablaSemanal();

    GestorNovReqDet find(Object id);

    List<GestorNovReqDet> findAll();

    List<GestorNovReqDet> findAll(Date desde, Date hasta);

    List<GestorNovReqDet> findRange(int[] range);

    int count();

}
