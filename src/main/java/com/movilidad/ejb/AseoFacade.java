/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Aseo;
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
public class AseoFacade extends AbstractFacade<Aseo> implements AseoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AseoFacade() {
        super(Aseo.class);
    }

    @Override
    public List<Aseo> findAllByRangoFechas(Date desde, Date hasta) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    a.*\n"
                    + "FROM\n"
                    + "    aseo a\n"
                    + "WHERE\n"
                    + "    (DATE(a.fecha_ini) BETWEEN ?1 AND ?2)\n"
                    + "        OR (DATE(a.fecha_fin) BETWEEN ?1 AND ?2)\n"
                    + "        AND a.estado_reg = 0;", Aseo.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            return q.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
