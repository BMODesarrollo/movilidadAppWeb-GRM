/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.dto.AuditoriaCostoDTO;
import com.movilidad.model.AuditoriaCosto;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AuditoriaCostoFacadeLocal {

    void create(AuditoriaCosto auditoriaCosto);

    void edit(AuditoriaCosto auditoriaCosto);

    void remove(AuditoriaCosto auditoriaCosto);

    AuditoriaCosto find(Object id);

    List<AuditoriaCosto> findAll();

    List<AuditoriaCosto> findRange(int[] range);

    int count();

    List<AuditoriaCosto> findByEstadoReg();

    List<AuditoriaCosto> findByIdAuditoria(Integer idAuditoria);

    List<AuditoriaCostoDTO> findListDtoByIdAuditoria(Integer idAuditoria);

    AuditoriaCosto validar(int idAuditoria,
            int idVehiculoTipo,
            int idAuditoriaCostoTipo,
            Date desde,
            Date hasta,
            int idAuditoriaCosto);
}
