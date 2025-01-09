/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.dto.TpConteoDTO;
import com.movilidad.model.TecnicoPatio;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Arévalo
 */
@Local
public interface TecnicoPatioFacadeLocal {

    /**
     *
     * @param par
     * @param d
     * @return
     */
    List<TecnicoPatio> findAll(int par, Date d);
        
    List<TpConteoDTO> findCounterTp(int uF, Date d);
    
    List<TpConteoDTO> findCounterTpTime(int uF, String horaInicio, String horaFinal);

}
