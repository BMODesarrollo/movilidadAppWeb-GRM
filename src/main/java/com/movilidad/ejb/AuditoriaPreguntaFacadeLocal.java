/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaPregunta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AuditoriaPreguntaFacadeLocal {

    void create(AuditoriaPregunta auditoriaPregunta);

    void edit(AuditoriaPregunta auditoriaPregunta);

    void remove(AuditoriaPregunta auditoriaPregunta);

    AuditoriaPregunta find(Object id);

    List<AuditoriaPregunta> findAll();

    List<AuditoriaPregunta> findRange(int[] range);

    List<AuditoriaPregunta> findByIdArea(int idArea);

    List<AuditoriaPregunta> findByIdAuditoria(int idAuditoriaPregunta);

    int count();

    public AuditoriaPregunta findByAreaIdAuditoriaPreguntaAndCodigo(String codigo, int idAudiPregunta, int idArea);

    List<AuditoriaPregunta> findPreguntasByOpcion(int opc);

}
