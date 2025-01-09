/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableInfoTecnica;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface CableInfoTecnicaFacadeLocal {

    void create(CableInfoTecnica cableInfoTecnica);

    void edit(CableInfoTecnica cableInfoTecnica);

    void remove(CableInfoTecnica cableInfoTecnica);

    CableInfoTecnica find(Object id);

    List<CableInfoTecnica> findAll();

    List<CableInfoTecnica> findRange(int[] range);

    int count();

    List<CableInfoTecnica> findByEstadoReg(Date desde, Date hasta);
}
