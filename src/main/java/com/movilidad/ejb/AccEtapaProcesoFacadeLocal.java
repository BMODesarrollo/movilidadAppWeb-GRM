/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccEtapaProceso;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccEtapaProcesoFacadeLocal {

    void create(AccEtapaProceso accEtapaProceso);

    void edit(AccEtapaProceso accEtapaProceso);

    void remove(AccEtapaProceso accEtapaProceso);

    AccEtapaProceso find(Object id);

    List<AccEtapaProceso> findAll();

    List<AccEtapaProceso> findRange(int[] range);

    int count();

    List<AccEtapaProceso> estadoReg();

    List<AccEtapaProceso> listPorTipoProceso(int i_idAccTipoProceso);
}
