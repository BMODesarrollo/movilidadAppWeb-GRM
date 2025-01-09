/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaRespuesta;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AuditoriaRespuestaFacadeLocal {

    void create(AuditoriaRespuesta auditoriaRespuesta);

    void edit(AuditoriaRespuesta auditoriaRespuesta);

    void remove(AuditoriaRespuesta auditoriaRespuesta);

    AuditoriaRespuesta find(Object id);

    List<AuditoriaRespuesta> findAll();

    List<AuditoriaRespuesta> findRange(int[] range);

    List<AuditoriaRespuesta> findByIdAuditoria(int idAuditoria);

    int count();

    AuditoriaRespuesta findByIdPregunta(int idAudiPregunta);

    AuditoriaRespuesta findByIdTipoRespuesta(int idTipoRespuesta);

    List<AuditoriaRespuesta> findByRangeDateAndAuditoria(Date desde, Date hasta, int idAudi);

    List<AuditoriaRespuesta> findByIdAuditoriaRealizadoPor(int idAudiRealizadoPor);
}
