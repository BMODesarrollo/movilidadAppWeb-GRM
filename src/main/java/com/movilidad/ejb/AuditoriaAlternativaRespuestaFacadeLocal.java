/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaAlternativaRespuesta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AuditoriaAlternativaRespuestaFacadeLocal {

    void create(AuditoriaAlternativaRespuesta auditoriaAlternativaRespuesta);

    void edit(AuditoriaAlternativaRespuesta auditoriaAlternativaRespuesta);

    void remove(AuditoriaAlternativaRespuesta auditoriaAlternativaRespuesta);

    AuditoriaAlternativaRespuesta find(Object id);

    List<AuditoriaAlternativaRespuesta> findAll();

    List<AuditoriaAlternativaRespuesta> findRange(int[] range);

    int count();

    List<AuditoriaAlternativaRespuesta> findByIdAuditoriaPregunta(int idTipoRespuesta);
}
