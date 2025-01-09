/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableOperacionCabina;
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
public class CableOperacionCabinaFacade extends AbstractFacade<CableOperacionCabina> implements CableOperacionCabinaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableOperacionCabinaFacade() {
        super(CableOperacionCabina.class);
    }

    @Override
    public CableOperacionCabina findByIdCableCabinaAndFecha(int id, Date fecha) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    coc.*\n"
                    + "FROM\n"
                    + "    cable_operacion_cabina coc\n"
                    + "WHERE\n"
                    + "    coc.id_cable_cabina = ?1\n"
                    + "        AND coc.estado_reg = 0\n"
                    + "        AND coc.fecha = ?2;", CableOperacionCabina.class);
            q.setParameter(1, id);
            q.setParameter(2, Util.dateFormat(fecha));
            return (CableOperacionCabina) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CableOperacionCabina> findAllByFecha(Date fecha, String order) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    coc.*\n"
                    + "FROM\n"
                    + "    cable_operacion_cabina coc\n"
                    + "        INNER JOIN\n"
                    + "    cable_cabina cc ON cc.id_cable_cabina = coc.id_cable_cabina\n"
                    + "WHERE\n"
                    + "    coc.fecha = ?1 AND coc.estado_reg = 0\n"
                    + "ORDER BY ABS(cc.nombre) " + order + ";", CableOperacionCabina.class);
            q.setParameter(1, Util.dateFormat(fecha));
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<CableOperacionCabina> findFechaAndOperando(Date fecha, String order, int operando) {
        try {
            String sql = "SELECT \n"
                    + "    coc.*\n"
                    + "FROM\n"
                    + "    cable_operacion_cabina coc\n"
                    + "        INNER JOIN\n"
                    + "    cable_cabina cc ON cc.id_cable_cabina = coc.id_cable_cabina\n"
                    + "WHERE\n"
                    + "    coc.fecha = ?1 AND coc.estado_reg = 0\n"
                    + "        AND coc.operando <> ?2\n"
                    + "        AND coc.estado_reg = 0\n"
                    + "ORDER BY ABS(cc.nombre) " + order + ";";

            Query q = em.createNativeQuery(sql, CableOperacionCabina.class);
            q.setParameter(1, fecha);
            q.setParameter(2, operando);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<CableOperacionCabina> findByRangoFecha(Date desde, Date hasta) {
        try {
            String sql = "SELECT \n"
                    + "    coc.*\n"
                    + "FROM\n"
                    + "    cable_operacion_cabina coc\n"
                    + "WHERE\n"
                    + "        coc.estado_reg = 0\n"
                    + "        AND coc.fecha between ?1 AND ?2;";

            Query q = em.createNativeQuery(sql, CableOperacionCabina.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
