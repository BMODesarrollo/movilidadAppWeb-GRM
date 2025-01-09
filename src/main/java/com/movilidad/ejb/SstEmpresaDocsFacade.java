/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstEmpresaDocs;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cesar
 */
@Stateless
public class SstEmpresaDocsFacade extends AbstractFacade<SstEmpresaDocs> implements SstEmpresaDocsFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SstEmpresaDocsFacade() {
        super(SstEmpresaDocs.class);
    }

    @Override
    public SstEmpresaDocs findByFechas(Integer idSstEmpresaDoc, Integer idSstEmpresa, Integer idSstEmpresaTipoDoc, Date desde, Date hasta) {
        try {
            String sql = "SELECT\n"
                    + "	* \n"
                    + "FROM\n"
                    + "	sst_empresa_docs \n"
                    + "WHERE\n"
                    + "	id_sst_empresa_docs <> ?1 \n"
                    + "	AND id_sst_empresa = ?2\n"
                    + "	AND id_sst_empresa_tipo_doc = ?3 AND\n"
                    + " ((?4 BETWEEN desde AND hasta\n"
                    + "        OR ?5 BETWEEN desde AND hasta)\n"
                    + "        OR (desde BETWEEN ?4 AND ?5\n"
                    + "        OR hasta BETWEEN ?4 AND ?5)) \n"
                    + " LIMIT 1;";

            Query query = em.createNativeQuery(sql, SstEmpresaDocs.class);
            query.setParameter(1, idSstEmpresaDoc);
            query.setParameter(2, idSstEmpresa);
            query.setParameter(3, idSstEmpresaTipoDoc);
            query.setParameter(4, Util.dateFormat(desde));
            query.setParameter(5, Util.dateFormat(hasta));

            return (SstEmpresaDocs) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<SstEmpresaDocs> findAllActivos(Integer idSstEmpresa) {
        try {
            String sql = "SELECT * FROM sst_empresa_docs where "
                    + "id_sst_empresa = ?1 and activo = 1 and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, SstEmpresaDocs.class);
            query.setParameter(1, idSstEmpresa);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<SstEmpresaDocs> obtenerHistorico(Integer idTipoDocumento, Integer idSstEmpresa) {
        try {
            String sql = "SELECT * FROM sst_empresa_docs where "
                    + "id_sst_empresa_tipo_doc = ?1 and id_sst_empresa = ?2 "
                    + "and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, SstEmpresaDocs.class);
            query.setParameter(1, idTipoDocumento);
            query.setParameter(2, idSstEmpresa);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SstEmpresaDocs findUtimoDocumentoActivo(Integer idTipoDocumento, Integer idSstEmpresa) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    sst_empresa_docs\n"
                    + "WHERE\n"
                    + "    id_sst_empresa = ?1\n"
                    + "        AND id_sst_empresa_tipo_doc = ?2\n"
                    + "        AND activo = 1\n"
                    + "ORDER BY creado DESC\n"
                    + "LIMIT 1;\n";

            Query query = em.createNativeQuery(sql, SstEmpresaDocs.class);
            query.setParameter(1, idSstEmpresa);
            query.setParameter(2, idTipoDocumento);
            return (SstEmpresaDocs) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
