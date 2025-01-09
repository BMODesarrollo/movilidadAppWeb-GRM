/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgServbuses;
import com.movilidad.model.PrgTc;
import com.movilidad.util.beans.ReporteServbuses;
import com.movilidad.util.beans.ReporteServbusesPatioFin;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author luis
 */
@Local
public interface PrgServbusesFacadeLocal {

    void create(PrgServbuses prgServbuses);

    void edit(PrgServbuses prgServbuses);

    void remove(PrgServbuses prgServbuses);

    PrgServbuses find(Object id);

    PrgTc getPrgtcSinAsignar(int codVehiculo);

    PrgTc getPrgtcSinAsignarByFecha(int idVehiculo, Date fecha, int idPatio);

    List<PrgServbuses> findAll();

    List<PrgServbuses> findRange(int[] range);

    int count();

    List<PrgServbuses> findByDate(Date fecha);

    List<ReporteServbusesPatioFin> getReporteServbusesPatioFin(Date fecha);

    List<ReporteServbuses> getReporteServbuses(Date fecha);
    
    int removeByDate(Date d);

}
