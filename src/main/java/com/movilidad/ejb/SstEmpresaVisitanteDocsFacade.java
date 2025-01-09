/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstEmpresaVisitanteDocs;
import com.movilidad.utils.Util;
import java.util.Date;
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
public class SstEmpresaVisitanteDocsFacade extends AbstractFacade<SstEmpresaVisitanteDocs> implements SstEmpresaVisitanteDocsFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SstEmpresaVisitanteDocsFacade() {
        super(SstEmpresaVisitanteDocs.class);
    }

    @Override
    public SstEmpresaVisitanteDocs findByFechas(Integer idSstEmpresaDoc, Integer idSstEmpresaVisitante, Integer idSstTipoDocTercero, Date desde, Date hasta) {
        try {
            String sql = "SELECT\n"
                    + "	* \n"
                    + "FROM\n"
                    + "	sst_empresa_visitante_docs \n"
                    + "WHERE\n"
                    + "	id_sst_empresa_visitante_docs <> ?1 \n"
                    + "	AND id_sst_empresa_visitante = ?2\n"
                    + "	AND id_sst_documento_tercero = ?3 AND\n"
                    + "	((?4 BETWEEN desde AND hasta\n"
                    + "        OR ?5 BETWEEN desde AND hasta)\n"
                    + "        OR (desde BETWEEN ?4 AND ?5\n"
                    + "        OR hasta BETWEEN ?4 AND ?5)) \n"
                    + " LIMIT 1;";

            Query query = em.createNativeQuery(sql, SstEmpresaVisitanteDocs.class);
            query.setParameter(1, idSstEmpresaDoc);
            query.setParameter(2, idSstEmpresaVisitante);
            query.setParameter(3, idSstTipoDocTercero);
            query.setParameter(4, Util.dateFormat(desde));
            query.setParameter(5, Util.dateFormat(hasta));

            return (SstEmpresaVisitanteDocs) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SstEmpresaVisitanteDocs findUtimoDocumentoActivo(Integer idTipoDocumento, Integer idSstEmpresaVisitante) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    sst_empresa_visitante_docs\n"
                    + "WHERE\n"
                    + "    id_sst_empresa_visitante = ?1\n"
                    + "        AND id_sst_documento_tercero = ?2\n"
                    + "        AND activo = 1\n"
                    + "ORDER BY creado DESC\n"
                    + "LIMIT 1;\n";

            Query query = em.createNativeQuery(sql, SstEmpresaVisitanteDocs.class);
            query.setParameter(1, idSstEmpresaVisitante);
            query.setParameter(2, idTipoDocumento);
            return (SstEmpresaVisitanteDocs) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SstEmpresaVisitanteDocs> findAllActivos(Integer idSstEmpresaVisitante) {
        try {
            String sql = "SELECT * FROM sst_empresa_visitante_docs where "
                    + "id_sst_empresa_visitante = ?1 and activo = 1 and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, SstEmpresaVisitanteDocs.class);
            query.setParameter(1, idSstEmpresaVisitante);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SstEmpresaVisitanteDocs> obtenerHistorico(Integer idTipoDocumento, Integer idSstEmpresaVisitante) {
        try {
            String sql = "SELECT * FROM sst_empresa_visitante_docs where "
                    + "id_sst_documento_tercero = ?1 and id_sst_empresa_visitante = ?2 "
                    + "and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, SstEmpresaVisitanteDocs.class);
            query.setParameter(1, idTipoDocumento);
            query.setParameter(2, idSstEmpresaVisitante);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
