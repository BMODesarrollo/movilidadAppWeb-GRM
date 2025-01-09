/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgTc;
import com.movilidad.model.RecapacitacionMaestro;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface RecapacitacionMaestroFacadeLocal {

    void create(RecapacitacionMaestro r);

    void edit(RecapacitacionMaestro r);

    void remove(RecapacitacionMaestro r);

    RecapacitacionMaestro find(Object id);

    List<RecapacitacionMaestro> findAll();

    List<RecapacitacionMaestro> findRange(int[] range);

    int count();

    List<RecapacitacionMaestro> findAllByEstadoReg();
    
    List<RecapacitacionMaestro> findRangeRecapacitacion(Date desde, Date hasta, int idGopUF);
    
    List<RecapacitacionMaestro> findRangeRecapacitacionCita(Date desde, int idGopUF);
    
    RecapacitacionMaestro findNovedad(int idNovedad);
    
    List<RecapacitacionMaestro> findRangeRecapacitacionNoProgramadas(int idGopUF);
    
    List<PrgTc> findRangeTareasRecapacitacionProgramadas(Date desde, Date hasta, int idGopUF);

}
