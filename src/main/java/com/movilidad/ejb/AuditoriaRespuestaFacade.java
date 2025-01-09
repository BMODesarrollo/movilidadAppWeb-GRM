/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaRespuesta;
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
public class AuditoriaRespuestaFacade extends AbstractFacade<AuditoriaRespuesta> implements AuditoriaRespuestaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuditoriaRespuestaFacade() {
        super(AuditoriaRespuesta.class);
    }

    @Override
    public List<AuditoriaRespuesta> findByIdAuditoria(int idAuditoria) {
        try {

            Query q = em.createNativeQuery("SELECT \n"
                    + "    ar.*\n"
                    + "FROM\n"
                    + "    auditoria_respuesta ar\n"
                    + "WHERE\n"
                    + "    ar.id_auditoria = ?1\n"
                    + "        AND ar.estado_reg = 0;", AuditoriaRespuesta.class);
            q.setParameter(1, idAuditoria);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public AuditoriaRespuesta findByIdPregunta(int idAudiPregunta) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    auditoria_respuesta\n"
                    + "WHERE\n"
                    + "    id_auditoria_pregunta = ?1 LIMIT 1;", AuditoriaRespuesta.class);
            q.setParameter(1, idAudiPregunta);
            return (AuditoriaRespuesta) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public AuditoriaRespuesta findByIdTipoRespuesta(int idTipoRespuesta) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    ar.*\n"
                    + "FROM\n"
                    + "    auditoria_respuesta ar\n"
                    + "        INNER JOIN\n"
                    + "    auditoria_pregunta ap ON ar.id_auditoria_pregunta = ap.id_auditoria_pregunta\n"
                    + "        INNER JOIN\n"
                    + "    auditoria_tipo_respuesta atr ON ap.id_auditoria_tipo_respuesta = atr.id_auditoria_tipo_respuesta\n"
                    + "WHERE\n"
                    + "    atr.id_auditoria_tipo_respuesta = ?1 LIMIT 1", AuditoriaRespuesta.class);
            q.setParameter(1, idTipoRespuesta);
            return (AuditoriaRespuesta) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<AuditoriaRespuesta> findByRangeDateAndAuditoria(Date desde, Date hasta, int idAudi) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    ar.*\n"
                    + "FROM\n"
                    + "    auditoria_respuesta ar\n"
                    + "        INNER JOIN\n"
                    + "    auditoria_realizado_por arp ON arp.id_auditoria_realizado_por = ar.id_auditoria_realizado_por\n"
                    + "        INNER JOIN\n"
                    + "    auditoria a ON a.id_auditoria = arp.id_auditoria\n"
                    + "WHERE\n"
                    + "    DATE(arp.fecha) BETWEEN ?2 AND ?3\n"
                    + "        AND arp.id_auditoria = ?1 order by ar.id_auditoria_realizado_por asc, ar.id_auditoria_pregunta asc;", AuditoriaRespuesta.class);
            q.setParameter(1, idAudi);
            q.setParameter(2, Util.dateFormat(desde));
            q.setParameter(3, Util.dateFormat(hasta));
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<AuditoriaRespuesta> findByIdAuditoriaRealizadoPor(int idAudiRealizadoPor) {
        try {
            Query q = em.createNativeQuery("SELECT DISTINCT\n"
                    + "    ar.*\n"
                    + "FROM\n"
                    + "    auditoria_respuesta ar\n"
                    + "        INNER JOIN\n"
                    + "    auditoria_realizado_por arp ON arp.id_auditoria_realizado_por = ar.id_auditoria_realizado_por\n"
                    + "        INNER JOIN\n"
                    + "    auditoria a ON a.id_auditoria = arp.id_auditoria\n"
                    + "WHERE\n"
                    + "    arp.id_auditoria_realizado_por = ?1\n"
                    + "ORDER BY ar.id_auditoria_realizado_por ASC , ar.id_auditoria_pregunta ASC;", AuditoriaRespuesta.class);
            q.setParameter(1, idAudiRealizadoPor);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
