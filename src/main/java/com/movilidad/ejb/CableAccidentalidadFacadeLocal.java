/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccidentalidad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface CableAccidentalidadFacadeLocal {

    void create(CableAccidentalidad cableAccidentalidad);

    void edit(CableAccidentalidad cableAccidentalidad);

    void remove(CableAccidentalidad cableAccidentalidad);

    CableAccidentalidad find(Object id);

    List<CableAccidentalidad> findAll();

    List<CableAccidentalidad> findRange(int[] range);

    int count();

    List<CableAccidentalidad> findByArguments(Integer idCabina, Integer idEmpleado, String cDesde, String cHasta, Integer idTipoAcc);

}
