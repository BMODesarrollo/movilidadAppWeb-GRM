/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.dto.DocumentosPdDTO;
import com.movilidad.model.PdMaestroSeguimiento;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class PdMaestroSeguimientoFacade extends AbstractFacade<PdMaestroSeguimiento> implements PdMaestroSeguimientoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PdMaestroSeguimientoFacade() {
        super(PdMaestroSeguimiento.class);
    }

    @Override
    public List<DocumentosPdDTO> findByIdProceso(Integer idProceso) {
        try {
            String sql = "SELECT id_pd_maestro_seguimiento, id_pd_maestro, fecha, seguimiento, path,\n"
                    + "username, creado, modificado, estado_reg, 'PD' documento_novedad \n"
                    + "FROM pd_maestro_seguimiento where id_pd_maestro = ?1 and estado_reg = 0\n"
                    + "UNION ALL\n"
                    + "SELECT NULL AS id_pd_maestro_seguimiento, id_novedad, ST1.creado, ST2.descripcion_tipo_documento,\n"
                    + "path_documento, usuario, ST1.creado, ST1.modificado, ST1.estado_reg, 'NOVEDAD DOCUMENTO' documento_novedad \n"
                    + "FROM novedad_documentos ST1 \n"
                    + "INNER JOIN novedad_tipo_documentos ST2 on ST1.id_novedad_tipo_documento= ST2.id_novedad_tipo_documento\n"
                    + "where id_novedad in (select id_novedad from pd_maestro_detalle where id_pd_maestro= ?1)\n"
                    + "UNION ALL\n"
                    + "select NULL AS id_pd_maestro_seguimiento, id_novedad_seguimiento, creado, nombre_archivo,path_archivo,\n"
                    + "username, creado, modificado, estado_reg, 'NOVEDAD MULTIMEDIA' documento_novedad \n"
                    + "from novedad_seguimiento_docs where id_novedad_seguimiento in\n"
                    + "(select id_novedad_seguimiento from novedad_seguimiento where id_novedad in (select id_novedad\n"
                    + "from pd_maestro_detalle where id_pd_maestro= ?1))\n"
                    + "UNION ALL\n"
                    + "select NULL AS id_pd_maestro_seguimiento, id_accidente, creado, descripcion,path,\n"
                    + "username, creado, modificado, estado_reg, CASE WHEN SUBSTRING_INDEX(path, '.', -1) = 'pdf' then 'ACCIDENTE' \n"
                    + "else 'ACCIDENTE MULTIMEDIA' end as documento_novedad \n"
                    + "from accidente_documento where id_accidente in\n"
                    + "(select id_accidente from accidente where id_novedad in (select id_novedad\n"
                    + "from pd_maestro_detalle where id_pd_maestro= ?1));";
            Query query = em.createNativeQuery(sql, "DocumentosDTOMapping");
            query.setParameter(1, idProceso);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
