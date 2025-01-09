/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Auditoria;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AuditoriaFacadeLocal {

    void create(Auditoria auditoria);

    void edit(Auditoria auditoria);

    void remove(Auditoria auditoria);

    Auditoria find(Object id);

    List<Auditoria> findAll();

    List<Auditoria> findRange(int[] range);

    int count();

    List<Auditoria> findByAreaAndIdGopUnidadFuncional(int idArea, int idGopUnidadFuncional);

    List<Auditoria> findByAreaAndRangoFechas(int idArea, Date desde, Date hasta);

    List<Auditoria> findAllByIdTipoAuditoria(int idTipoAudi, int idGopUnidaFuncional);

    Auditoria findByAreaIdAuditoriaAndCodigo(String codigo, int idAuditoria, int idArea);

    List<Auditoria> findByAreaDisponibles(int idArea, Date fecha);
}
