/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaTipoRespuesta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AuditoriaTipoRespuestaFacadeLocal {

    void create(AuditoriaTipoRespuesta auditoriaTipoPregunta);

    void edit(AuditoriaTipoRespuesta auditoriaTipoPregunta);

    void remove(AuditoriaTipoRespuesta auditoriaTipoPregunta);

    AuditoriaTipoRespuesta find(Object id);

    List<AuditoriaTipoRespuesta> findAll();

    List<AuditoriaTipoRespuesta> findRange(int[] range);

    int count();

    List<AuditoriaTipoRespuesta> findByArea(int idArea);

    AuditoriaTipoRespuesta findByAreaIdAuditoriaTipoRespuestaAndCodigo(String codigo, int id, int idArea);
}
