/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PqrMaestroRespaldo;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PqrMaestroRespaldoFacadeLocal {

    void create(PqrMaestroRespaldo pqrMaestro);

    void edit(PqrMaestroRespaldo pqrMaestro);

    void remove(PqrMaestroRespaldo pqrMaestro);

    PqrMaestroRespaldo find(Object id);

    List<PqrMaestroRespaldo> findAll();

    List<PqrMaestroRespaldo> findByIdPqr(Integer idPqr);

    List<PqrMaestroRespaldo> findRange(int[] range);

    int count();

}
