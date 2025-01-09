/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableRevisionDiaRta;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface CableRevisionDiaRtaFacadeLocal {

    void create(CableRevisionDiaRta cableRevisionDiaRta);

    void edit(CableRevisionDiaRta cableRevisionDiaRta);

    void remove(CableRevisionDiaRta cableRevisionDiaRta);

    CableRevisionDiaRta find(Object id);

    CableRevisionDiaRta findLastRecordByRevisionDia(Integer idRevisionDia);

    CableRevisionDiaRta verificarRegistro(Date fecha, Integer idRevisionDia, Integer idCableEstacion, Integer revisionDiaHorario);

    List<CableRevisionDiaRta> findAll();

    List<CableRevisionDiaRta> findByDateRange(Date fechaInicio,Date fechaFin);

    List<CableRevisionDiaRta> findByHorarioAndDia(Integer idRevisionDiaHorario, Integer idRevisionDia);

    List<CableRevisionDiaRta> findAllByEstadoReg();

    List<CableRevisionDiaRta> findRange(int[] range);

    int count();

}
