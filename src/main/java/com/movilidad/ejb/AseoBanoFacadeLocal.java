/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AseoBano;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AseoBanoFacadeLocal {

    void create(AseoBano aseoBano);

    void edit(AseoBano aseoBano);

    void remove(AseoBano aseoBano);

    AseoBano find(Object id);

    List<AseoBano> findAll();

    List<AseoBano> findRange(int[] range);

    int count();

    AseoBano findByFechaAndCodigo(Date desde, Date hasta, String codigo);

    List<AseoBano> findAllByEstadoReg(Date desde, Date hasta);

    List<AseoBano> findByAreaPendiente(int idArea);
}
