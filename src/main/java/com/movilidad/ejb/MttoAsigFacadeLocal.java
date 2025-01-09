/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MttoAsig;
import com.movilidad.util.beans.ResumenAsignadosPorPatio;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface MttoAsigFacadeLocal {

    void create(MttoAsig mttoAsig);

    void edit(MttoAsig mttoAsig);

    void remove(MttoAsig mttoAsig);

    MttoAsig find(Object id);

    MttoAsig findByServbus(Date fecha, String servbus);

    MttoAsig findByIdVehiculo(int idVehiculo, Date fecha,int idMttoAsig);

    List<MttoAsig> findAll();

    List<MttoAsig> findAsignacionSinServbus(Date fechaPost);

    List<MttoAsig> findRange(int[] range);

    List<ResumenAsignadosPorPatio> getResumenAsignados(Date fecha);

    int count();

}
