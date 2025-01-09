/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgTcResponsable;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PrgTcResponsableFacadeLocal {

    void create(PrgTcResponsable prgTcResponsable);

    void edit(PrgTcResponsable prgTcResponsable);

    void remove(PrgTcResponsable prgTcResponsable);

    PrgTcResponsable find(Object id);

    PrgTcResponsable findByResponsable(Integer idRegistro, String responsable);

    List<PrgTcResponsable> findAll();

    List<PrgTcResponsable> getPrgResponsables();

    List<PrgTcResponsable> findRange(int[] range);

    int count();

}
