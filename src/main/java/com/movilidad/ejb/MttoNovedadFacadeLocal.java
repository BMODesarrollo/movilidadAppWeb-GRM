/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MttoNovedad;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface MttoNovedadFacadeLocal {

    void create(MttoNovedad mttoNovedad);

    void edit(MttoNovedad mttoNovedad);

    void remove(MttoNovedad mttoNovedad);

    MttoNovedad find(Object id);

    List<MttoNovedad> findAll();

    List<MttoNovedad> findRange(int[] range);

    List<MttoNovedad> getMttoNovAbiertasByVehiculo(int idVehiculo);

    List<MttoNovedad> getNovByfechas(Date desde, Date hasta);

    int count();

}
