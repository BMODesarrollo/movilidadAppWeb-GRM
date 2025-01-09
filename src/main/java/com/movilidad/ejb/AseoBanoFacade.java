/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AseoBano;
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
public class AseoBanoFacade extends AbstractFacade<AseoBano> implements AseoBanoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AseoBanoFacade() {
        super(AseoBano.class);
    }

    @Override
    public AseoBano findByFechaAndCodigo(Date desde, Date hasta, String codigo) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    ab.*\n"
                    + "FROM\n"
                    + "    aseo_bano ab\n"
                    + "WHERE\n"
                    + "    (DATE(ab.fecha_hora_ini) BETWEEN ?1 AND ?2)\n"
                    + "        OR (DATE(ab.fecha_hora_fin) BETWEEN ?1 AND ?2)\n"
                    + "        AND ab.codigo_planilla =?2\n"
                    + "        AND ab.estado_reg = 0;", AseoBano.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            q.setParameter(3, codigo);
            return (AseoBano) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<AseoBano> findAllByEstadoReg(Date desde, Date hasta) {
        try {

            Query q = em.createNativeQuery("SELECT \n"
                    + "    ab.*\n"
                    + "FROM\n"
                    + "    aseo_bano ab\n"
                    + "WHERE\n"
                    + "    ((DATE(ab.fecha_hora_ini) BETWEEN ?1 AND ?2)\n"
                    + "        OR (DATE(ab.fecha_hora_fin) BETWEEN ?1 AND ?2))\n"
                    + "        AND ab.estado_reg = 0;", AseoBano.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<AseoBano> findByAreaPendiente(int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    ab.*\n"
                    + "FROM\n"
                    + "    aseo_bano ab\n"
                    + "WHERE\n"
                    + "        ab.id_aseo_param_area=?1\n"
                    + "        AND ab.autorizado = -1\n"
                    + "        AND ab.estado_reg = 0;", AseoBano.class);
            q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
