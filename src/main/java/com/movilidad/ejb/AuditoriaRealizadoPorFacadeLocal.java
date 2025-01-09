/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaRealizadoPor;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AuditoriaRealizadoPorFacadeLocal {

    void create(AuditoriaRealizadoPor auditoriaRealizadoPor);

    void edit(AuditoriaRealizadoPor auditoriaRealizadoPor);

    void remove(AuditoriaRealizadoPor auditoriaRealizadoPor);

    AuditoriaRealizadoPor find(Object id);

    List<AuditoriaRealizadoPor> findAll();

    List<AuditoriaRealizadoPor> findRange(int[] range);

    List<AuditoriaRealizadoPor> findByIdAuditoria(int idAuditoria);

    List<AuditoriaRealizadoPor> findByRagoFechasIdAuditoria(Date desde, Date hasta, int idAuditoria);

    int count();

}
