/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Auditoria;
import com.movilidad.utils.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author solucionesit
 */
@Stateless
public class AuditoriaFacade extends AbstractFacade<Auditoria> implements AuditoriaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuditoriaFacade() {
        super(Auditoria.class);
    }

    @Override
    public List<Auditoria> findByAreaAndIdGopUnidadFuncional(int idArea, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "         a.id_gop_unidad_funcional=?2 AND\n";

        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    a.*\n"
                    + "FROM\n"
                    + "    auditoria a\n"
                    + "WHERE\n"
                    + sql_unida_func
                    + "    a.id_param_area = ?1 AND a.estado_reg = 0;", Auditoria.class);
            q.setParameter(1, idArea);
            q.setParameter(2, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Auditoria> findByAreaAndRangoFechas(int idArea, Date desde, Date hasta) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    a.*\n"
                    + "FROM\n"
                    + "    auditoria a\n"
                    + "        INNER JOIN\n"
                    + "    auditoria_encabezado ae ON ae.id_auditoria_encabezado = a.id_auditoria_encabezado\n"
                    + "WHERE\n"
                    + "    (ae.fecha_desde BETWEEN ?2 AND ?3\n"
                    + "        AND ae.fecha_hasta BETWEEN ?2 AND ?3)\n"
                    + "        AND a.id_param_area = ?1\n"
                    + "        AND a.estado_reg = 0;", Auditoria.class);
            q.setParameter(1, idArea);
            q.setParameter(2, Util.dateFormat(desde));
            q.setParameter(3, Util.dateFormat(hasta));
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Auditoria findByAreaIdAuditoriaAndCodigo(String codigo, int idAuditoria, int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT a.* "
                    + "FROM auditoria a "
                    + "WHERE a.id_param_area=?3 "
                    + "AND a.estado_reg=0 "
                    + "AND a.codigo=?1 "
                    + "AND a.id_auditoria<>?2;", Auditoria.class);
            q.setParameter(1, codigo);
            q.setParameter(2, idAuditoria);
            q.setParameter(3, idArea);
            return (Auditoria) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Auditoria> findAllByIdTipoAuditoria(int idTipoAudi, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "         a.id_gop_unidad_funcional=?2 AND\n";
        try {
            Query q = em.createNativeQuery("SELECT DISTINCT\n"
                    + "    a.*\n"
                    + "FROM\n"
                    + "    auditoria a\n"
                    + "        INNER JOIN\n"
                    + "    auditoria_encabezado ae ON ae.id_auditoria_encabezado = a.id_auditoria_encabezado\n"
                    + "WHERE\n"
                    + sql_unida_func
                    + "    ae.id_auditoria_tipo = ?1\n"
                    + "        AND a.estado_reg = 0;", Auditoria.class);
            q.setParameter(1, idTipoAudi);
            q.setParameter(2, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Auditoria> findByAreaDisponibles(int idArea, Date fecha) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    a.*\n"
                    + "FROM\n"
                    + "    auditoria a\n"
                    + "        INNER JOIN\n"
                    + "    auditoria_encabezado ae ON ae.id_auditoria_encabezado = a.id_auditoria_encabezado\n"
                    + "WHERE\n"
                    + "    TIMESTAMP(?2) BETWEEN ae.fecha_desde AND ae.fecha_hasta\n"
                    + "        AND a.id_param_area = ?1\n"
                    + "        AND a.estado_reg = 0;", Auditoria.class);
            q.setParameter(1, idArea);
            q.setParameter(2, Util.dateTimeFormat(fecha));
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
