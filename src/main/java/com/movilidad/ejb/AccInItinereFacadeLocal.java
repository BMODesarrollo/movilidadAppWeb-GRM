/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.BitacoraAccInItinere;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Arévalo
 */
@Local
public interface AccInItinereFacadeLocal {

    List<BitacoraAccInItinere> getAccidentesInItinere(Date fechaIncio, Date fechaFin, int idGopUnidadFuncional);
}
