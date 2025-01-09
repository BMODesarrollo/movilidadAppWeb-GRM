/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgSerconDet;
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
public class PrgSerconDetFacade extends AbstractFacade<PrgSerconDet> implements PrgSerconDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgSerconDetFacade() {
        super(PrgSerconDet.class);
    }

    @Override
    public List<PrgSerconDet> findByRangoFecha(Date desde, Date hasta) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    psd.*\n"
                + "FROM\n"
                + "    prg_sercon_det psd\n"
                + "        INNER JOIN\n"
                + "    prg_sercon ps ON ps.id_prg_sercon = psd.id_prg_sercon\n"
                + "        INNER JOIN\n"
                + "    empleado e ON e.id_empleado = ps.id_empleado\n"
                + "WHERE\n"
                + "    ps.fecha BETWEEN ?1 AND ?2\n"
                + "        AND ps.estado_reg = 0\n"
                + "        AND psd.estado_reg = 0\n"
                + "ORDER BY e.codigo_tm ASC , ps.fecha ASC, psd.timeorigin ASC;", PrgSerconDet.class);
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        return q.getResultList();
    }

}
