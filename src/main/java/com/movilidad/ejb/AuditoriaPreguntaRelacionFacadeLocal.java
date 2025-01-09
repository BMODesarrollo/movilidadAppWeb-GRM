/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaPreguntaRelacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AuditoriaPreguntaRelacionFacadeLocal {

    void create(AuditoriaPreguntaRelacion auditoriaPreguntaRelacion);

    void edit(AuditoriaPreguntaRelacion auditoriaPreguntaRelacion);

    void remove(AuditoriaPreguntaRelacion auditoriaPreguntaRelacion);

    AuditoriaPreguntaRelacion find(Object id);

    List<AuditoriaPreguntaRelacion> findAll();

    List<AuditoriaPreguntaRelacion> findRange(int[] range);

    int count();
    
}
