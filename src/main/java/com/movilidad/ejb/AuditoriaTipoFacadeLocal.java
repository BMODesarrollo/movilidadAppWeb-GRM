/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AuditoriaTipoFacadeLocal {

    void create(AuditoriaTipo auditoriaTipo);

    void edit(AuditoriaTipo auditoriaTipo);

    void remove(AuditoriaTipo auditoriaTipo);

    AuditoriaTipo find(Object id);

    AuditoriaTipo findTipoUsado(int idAuditoriaTipo);

    List<AuditoriaTipo> findAll();

    List<AuditoriaTipo> findRange(int[] range);

    int count();

    List<AuditoriaTipo> findByArea(int idArea);

    AuditoriaTipo findByAreaIdAuditoriaTipoAndNombre(String nombre, int idAuditoriaTipo, int idArea);
}
