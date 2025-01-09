/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Multa;
import com.movilidad.util.beans.MultasCtrl;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface MultaFacadeLocal {

    void create(Multa multa);

    void edit(Multa multa);

    void remove(Multa multa);

    Multa find(Object id);

    List<Multa> findAll(Date fecha);

    List<Multa> findEstRegis(Date fecha);

    List<MultasCtrl> findMultasCtrl(Date fecha, int idGopUnidadFuncional);

    List<Multa> findByDateRange(Date fechaIni, Date fechaFin, int idGopUnidadFuncional);

    List<Multa> findRange(int[] range);

    int count();

}
