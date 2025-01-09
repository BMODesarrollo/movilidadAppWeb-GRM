/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableCabina;
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
 * @author Carlos Ballestas
 */
@Stateless
public class CableCabinaFacade extends AbstractFacade<CableCabina> implements CableCabinaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableCabinaFacade() {
        super(CableCabina.class);
    }

    @Override
    public CableCabina findByCodigo(String codigo, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM cable_cabina where id_cable_cabina <> ?1 and codigo = ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, CableCabina.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, codigo);

            return (CableCabina) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CableCabina findByNombre(String nombre, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM cable_cabina where id_cable_cabina <> ?1 and nombre = ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, CableCabina.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, nombre);

            return (CableCabina) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CableCabina> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("CableCabina.findByEstadoReg", CableCabina.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CableCabina verificarRangoFechas(Date fecha, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM cable_cabina \n"
                    + "where ?1 BETWEEN fecha_ini_op and fecha_fin_op \n"
                    + "and id_cable_cabina <> ?2\n"
                    + "and estado_reg = 0 LIMIT 1;";

            Query query = em.createNativeQuery(sql, CableCabina.class);
            query.setParameter(1, Util.dateFormat(fecha));
            query.setParameter(2, idRegistro);

            return (CableCabina) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CableCabina> findAllByEstadoRegAndNombreOrderBy(String order) {
        try {
            String sql = "SELECT \n"
                    + "    cc.*\n"
                    + "FROM\n"
                    + "    cable_cabina cc\n"
                    + "WHERE\n"
                    + "    cc.estado_reg = 0\n"
                    + "ORDER BY ABS(cc.nombre) " + order + ";";

            Query q = em.createNativeQuery(sql, CableCabina.class);

            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CableCabina> findByLavadas(Date fecha, String order) {
        try {
            String sql = "SELECT \n"
                    + "    cc.*\n"
                    + "FROM\n"
                    + "    cable_cabina cc\n"
                    + "        INNER JOIN\n"
                    + "    aseo_cabina ac ON ac.id_cable_cabina = cc.id_cable_cabina\n"
                    + "WHERE\n"
                    + "    DATE(ac.fecha_hora) = ?1\n"
                    + "        AND ac.estado_reg NOT IN (1, 2)\n"
                    + "ORDER BY ABS(cc.nombre) " + order + ";";

            Query q = em.createNativeQuery(sql, CableCabina.class);
            q.setParameter(1, fecha);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<CableCabina> findByNoOperando(Date fecha, String order) {
        try {
            String sql = "SELECT \n"
                    + "    cc.*\n"
                    + "FROM\n"
                    + "    cable_cabina cc\n"
                    + "        INNER JOIN\n"
                    + "    cable_operacion_cabina coc ON coc.id_cable_cabina = cc.id_cable_cabina\n"
                    + "WHERE\n"
                    + "    coc.fecha = ?1\n"
                    + "        AND coc.operando <> 1\n"
                    + "        AND coc.estado_reg = 0\n"
                    + "ORDER BY ABS(cc.nombre) " + order + ";";

            Query q = em.createNativeQuery(sql, CableCabina.class);
            q.setParameter(1, fecha);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
