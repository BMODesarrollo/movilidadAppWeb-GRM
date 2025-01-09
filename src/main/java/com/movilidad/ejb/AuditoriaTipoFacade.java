/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaTipo;
import java.util.ArrayList;
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
public class AuditoriaTipoFacade extends AbstractFacade<AuditoriaTipo> implements AuditoriaTipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuditoriaTipoFacade() {
        super(AuditoriaTipo.class);
    }

    @Override
    public List<AuditoriaTipo> findByArea(int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT at.* "
                    + "FROM auditoria_tipo at "
                    + "WHERE at.id_param_area=?1 "
                    + "AND at.estado_reg=0;", AuditoriaTipo.class);
            q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public AuditoriaTipo findByAreaIdAuditoriaTipoAndNombre(String nombre, int idAuditoriaTipo, int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT at.* "
                    + "FROM auditoria_tipo "
                    + "WHERE at.id_param_area=?3 "
                    + "AND at.estado_reg=0 "
                    + "AND at.nombre=?1 "
                    + "AND at.id_auditoria_tipo=?2;", AuditoriaTipo.class);
            q.setParameter(1, nombre);
            q.setParameter(2, idAuditoriaTipo);
            q.setParameter(3, idArea);
            return (AuditoriaTipo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public AuditoriaTipo findTipoUsado(int idAuditoriaTipo) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    at.*\n"
                    + "FROM\n"
                    + "    auditoria_tipo at\n"
                    + "        INNER JOIN\n"
                    + "    auditoria_encabezado ae ON ae.id_auditoria_tipo = at.id_auditoria_tipo\n"
                    + "        INNER JOIN\n"
                    + "    auditoria a ON a.id_auditoria_encabezado = ae.id_auditoria_encabezado\n"
                    + "        INNER JOIN\n"
                    + "    auditoria_realizado_por arp ON arp.id_auditoria = a.id_auditoria\n"
                    + "WHERE\n"
                    + "    at.id_auditoria_tipo = ?1\n"
                    + "        AND arp.estado_reg = 0\n"
                    + "LIMIT 1", AuditoriaTipo.class);
            q.setParameter(1, idAuditoriaTipo);
            return (AuditoriaTipo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
