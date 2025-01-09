/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.dto.PdAgendaMasivaDTO;
import com.movilidad.dto.PdPrincipalDTO;
import com.movilidad.dto.PdReporteNovedadesDTO;
import com.movilidad.model.PdMaestro;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PdMaestroFacadeLocal {

    void create(PdMaestro pdMaestro);

    void edit(PdMaestro pdMaestro);

    void remove(PdMaestro pdMaestro);

    PdMaestro find(Object id);

    List<PdMaestro> findAll();
    
    List<PdPrincipalDTO> findAllByEstadoReg(int idGopUnidadFuncional);
    
    List<PdPrincipalDTO>findAllByDate(int idGopUnidadFuncional, Date fechaInicio, Date fechaFin);

    List<PdMaestro> findRange(int[] range);

    int count();
    
    List<PdAgendaMasivaDTO> findAgendaPrgTc(int idGopUnidadFuncional, Date fechaInicio, Date fechaFin);
    
    List<PdPrincipalDTO> conteoAbiertosPorEmpleado(int idEmpleado);
    
    List<PdReporteNovedadesDTO> findNovedadPd(int idGopUnidadFuncional, Date fechaInicio, Date fechaFin);
    
}
