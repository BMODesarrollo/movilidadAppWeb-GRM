/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmVrbonoGrupal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PmVrbonoGrupalFacadeLocal {

    void create(PmVrbonoGrupal pmVrbonoGrupal);

    void edit(PmVrbonoGrupal pmVrbonoGrupal);

    void remove(PmVrbonoGrupal pmVrbonoGrupal);

    PmVrbonoGrupal find(Object id);

    PmVrbonoGrupal verificarFecha(Date fecha, Integer idRegistro);

    List<PmVrbonoGrupal> findAll();

    List<PmVrbonoGrupal> findAllByEstadoReg();

    List<PmVrbonoGrupal> findRange(int[] range);

    int count();

}
