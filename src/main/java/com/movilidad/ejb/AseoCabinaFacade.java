/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AseoCabina;
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
public class AseoCabinaFacade extends AbstractFacade<AseoCabina> implements AseoCabinaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AseoCabinaFacade() {
        super(AseoCabina.class);
    }

    @Override
    public AseoCabina findLastByIdAndFecha(int id, Date fecha) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    ac.*\n"
                    + "FROM\n"
                    + "    aseo_cabina ac\n"
                    + "WHERE\n"
                    + "    ac.id_cable_cabina = ?1\n"
                    + "        AND DATE(ac.fecha_hora) = ?2\n"
                    + "        AND estado_reg NOT IN (1 , 2)\n"
                    + "ORDER BY id_aseo_cabina DESC\n"
                    + "LIMIT 1;", AseoCabina.class);
            q.setParameter(1, id);
            q.setParameter(2, Util.dateFormat(fecha));
            return (AseoCabina) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void limpiarAseoCabinaByIdAseoCabinaAndFecha(int idAseoCabina, Date fecha) {
        Query q = em.createNativeQuery("UPDATE aseo_cabina ac \n"
                + "SET \n"
                + "    ac.estado_reg = 2\n"
                + "WHERE\n"
                + "    ac.id_aseo_cabina = ?1\n"
                + "    AND date(ac.fecha_hora) = ?2\n"
                + "        AND ac.estado_reg = 0;");
        q.setParameter(1, idAseoCabina);
        q.setParameter(2, fecha);
        q.executeUpdate();
    }

    @Override
    public void limpiarTodoAseoCabinaByFecha(Date fecha) {
        Query q = em.createNativeQuery("UPDATE aseo_cabina ac \n"
                + "SET \n"
                + "    ac.estado_reg = 2\n"
                + "WHERE\n"
                + "     date(ac.fecha_hora) = ?2\n"
                + "        AND ac.estado_reg = 0;");
        q.setParameter(2, Util.dateFormat(fecha));
        q.executeUpdate();
    }

    @Override
    public List<AseoCabina> findAllByFechaEstadoReg(Date desde, Date hasta) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    ac.*\n"
                    + "FROM\n"
                    + "    aseo_cabina ac\n"
                    + "WHERE\n"
                    + "    DATE(ac.fecha_hora) BETWEEN ?1 AND ?2;", AseoCabina.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
