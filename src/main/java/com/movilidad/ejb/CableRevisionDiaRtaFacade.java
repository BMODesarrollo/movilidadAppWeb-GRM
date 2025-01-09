/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableRevisionDiaRta;
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
public class CableRevisionDiaRtaFacade extends AbstractFacade<CableRevisionDiaRta> implements CableRevisionDiaRtaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableRevisionDiaRtaFacade() {
        super(CableRevisionDiaRta.class);
    }

    @Override
    public List<CableRevisionDiaRta> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("CableRevisionDiaRta.findByEstadoReg", CableRevisionDiaRta.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CableRevisionDiaRta verificarRegistro(Date fecha, Integer idRevisionDia, Integer idCableEstacion, Integer revisionDiaHorario) {
        try {
            String sql = "SELECT \n"
                    + "    rta.*\n"
                    + "FROM\n"
                    + "    cable_revision_dia_rta rta\n"
                    + "        INNER JOIN\n"
                    + "    cable_revision_dia d ON rta.id_cable_revision_dia = d.id_cable_revision_dia\n"
                    + "WHERE\n"
                    + "    d.fecha = ?1\n"
                    + "        AND d.id_cable_revision_dia <> ?2\n"
                    + "        AND d.id_cable_estacion = ?3\n"
                    + "        AND rta.id_cable_revision_dia_horario = ?4\n"
                    + "        AND rta.estado_reg = 0\n"
                    + "LIMIT 1;";
            Query query = em.createNativeQuery(sql, CableRevisionDiaRta.class);
            query.setParameter(1, Util.dateFormat(fecha));
            query.setParameter(2, idRevisionDia);
            query.setParameter(3, idCableEstacion);
            query.setParameter(4, revisionDiaHorario);

            return (CableRevisionDiaRta) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CableRevisionDiaRta> findByHorarioAndDia(Integer idRevisionDiaHorario, Integer idRevisionDia) {
        try {
            String sql = "SELECT * FROM cable_revision_dia_rta where id_cable_revision_dia_horario = ?1 and id_cable_revision_dia = ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, CableRevisionDiaRta.class);
            query.setParameter(1, idRevisionDiaHorario);
            query.setParameter(2, idRevisionDia);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CableRevisionDiaRta findLastRecordByRevisionDia(Integer idRevisionDia) {
        try {
            String sql = "SELECT * FROM cable_revision_dia_rta where id_cable_revision_dia = ?1 and estado_reg = 0 order by creado desc limit 1;";
            Query query = em.createNativeQuery(sql, CableRevisionDiaRta.class);
            query.setParameter(1, idRevisionDia);

            return (CableRevisionDiaRta) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CableRevisionDiaRta> findByDateRange(Date fechaInicio, Date fechaFin) {
        try {
            String sql = "SELECT \n"
                    + "    rta.*\n"
                    + "FROM\n"
                    + "    cable_revision_dia_rta rta\n"
                    + "        INNER JOIN\n"
                    + "    cable_revision_dia dia ON dia.id_cable_revision_dia = rta.id_cable_revision_dia\n"
                    + "WHERE\n"
                    + "    dia.fecha BETWEEN ?1 AND ?2\n"
                    + "ORDER BY dia.fecha ASC;";
            Query query = em.createNativeQuery(sql, CableRevisionDiaRta.class);
            query.setParameter(1, Util.dateFormat(fechaInicio));
            query.setParameter(2, Util.dateFormat(fechaFin));

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
