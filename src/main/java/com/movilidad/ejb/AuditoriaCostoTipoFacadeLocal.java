/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaCostoTipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface AuditoriaCostoTipoFacadeLocal {

    void create(AuditoriaCostoTipo auditoriaCostoTipo);

    void edit(AuditoriaCostoTipo auditoriaCostoTipo);

    void remove(AuditoriaCostoTipo auditoriaCostoTipo);

    AuditoriaCostoTipo find(Object id);

    AuditoriaCostoTipo findByNombre(Integer idRegistro, String nombre);

    List<AuditoriaCostoTipo> findAll();

    List<AuditoriaCostoTipo> findAllByEstadoReg();

    List<AuditoriaCostoTipo> findRange(int[] range);

    int count();

}
