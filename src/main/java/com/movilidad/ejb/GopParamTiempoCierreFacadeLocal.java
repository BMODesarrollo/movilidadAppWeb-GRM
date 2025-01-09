/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GopParamTiempoCierre;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GopParamTiempoCierreFacadeLocal {

    void create(GopParamTiempoCierre gopParamTiempoCierre);

    void edit(GopParamTiempoCierre gopParamTiempoCierre);

    void remove(GopParamTiempoCierre gopParamTiempoCierre);

    GopParamTiempoCierre find(Object id);

    List<GopParamTiempoCierre> findAll();

    List<GopParamTiempoCierre> findRange(int[] range);

    int count();

    List<GopParamTiempoCierre> findAllEstadoRegByUnidadFuncional(int idGopUnidaFunc);

    GopParamTiempoCierre findParam();

}
