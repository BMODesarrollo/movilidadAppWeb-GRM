/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaRealizadoPor;
import com.movilidad.utils.Util;
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
public class AuditoriaRealizadoPorFacade extends AbstractFacade<AuditoriaRealizadoPor> implements AuditoriaRealizadoPorFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuditoriaRealizadoPorFacade() {
        super(AuditoriaRealizadoPor.class);
    }

    @Override
    public List<AuditoriaRealizadoPor> findByIdAuditoria(int idAuditoria) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    auditoria_realizado_por arp\n"
                + "WHERE\n"
                + "    arp.id_auditoria = ?1\n"
                + "        AND arp.estado_reg = 0;", AuditoriaRealizadoPor.class);
        q.setParameter(1, idAuditoria);
        return q.getResultList();

    }

    @Override
    public List<AuditoriaRealizadoPor> findByRagoFechasIdAuditoria(Date desde, Date hasta, int idAuditoria) {
        Query q = em.createNativeQuery("SELECT \n"
                + "   distinct arp.*\n"
                + "FROM\n"
                + "    auditoria_respuesta ar\n"
                + "        INNER JOIN\n"
                + "    auditoria_realizado_por arp ON arp.id_auditoria_realizado_por = ar.id_auditoria_realizado_por\n"
                + "        INNER JOIN\n"
                + "    auditoria a ON a.id_auditoria = arp.id_auditoria\n"
                + "WHERE\n"
                + "    DATE(arp.fecha) BETWEEN ?2 AND ?3 \n"
                + "        AND arp.id_auditoria = ?1\n"
                + "ORDER BY ar.id_auditoria_realizado_por ASC , ar.id_auditoria_pregunta ASC;", AuditoriaRealizadoPor.class);
        q.setParameter(1, idAuditoria);
        q.setParameter(2, Util.dateFormat(desde));
        q.setParameter(3, Util.dateFormat(hasta));
        return q.getResultList();

    }

}
