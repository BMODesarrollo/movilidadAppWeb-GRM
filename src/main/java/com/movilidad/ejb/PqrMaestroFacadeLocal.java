/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PqrMaestro;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PqrMaestroFacadeLocal {

    void create(PqrMaestro pqrMaestro);

    void edit(PqrMaestro pqrMaestro);

    void remove(PqrMaestro pqrMaestro);

    PqrMaestro find(Object id);

    List<PqrMaestro> findAll();

    List<PqrMaestro> findByRangoFechas(Date desde, Date hasta, int idGopUF);

    List<PqrMaestro> findRange(int[] range);

    int count();

    List<PqrMaestro> findAllOpen(boolean edit, int idPqr);

}
