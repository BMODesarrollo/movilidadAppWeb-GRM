/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegInoperativos;
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
public class SegInoperativosFacade extends AbstractFacade<SegInoperativos> implements SegInoperativosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegInoperativosFacade() {
        super(SegInoperativos.class);
    }

    @Override
    public List<SegInoperativos> findByRangoAndEstadoReg(Date desde, Date hasta) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    seg_inoperativos\n"
                + "WHERE\n"
                + "    fecha_evento BETWEEN ?1 AND ?2\n"
                + "        AND estado_reg = 0\n"
                + "ORDER BY fecha_evento DESC;", SegInoperativos.class);
        q.setParameter(1, desde);
        q.setParameter(2, hasta);
        return q.getResultList();
    }

    @Override
    public SegInoperativos findByFechaEventoAndIdEmpleado(int idEmpleado, Date fechaEvento, int idSegInoperativos) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    seg_inoperativos\n"
                    + "WHERE\n"
                    + "    fecha_evento = ?2 AND estado_reg = 0\n"
                    + "        AND id_empleado = ?1\n"
                    + "        AND id_seg_inoperativos <> ?3\n"
                    + "LIMIT 1;", SegInoperativos.class);
            q.setParameter(1, idEmpleado);
            q.setParameter(2, Util.dateFormat(fechaEvento));
            q.setParameter(3, idSegInoperativos);
            return (SegInoperativos) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SegInoperativos findByFechaSancionBetweeHabilitacionnAndIdEmpleado(int idEmpleado, Date fechaEvento) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    seg_inoperativos\n"
                    + "WHERE\n"
                    + "    ?2 BETWEEN fecha_sancion AND fecha_habilitacion\n"
                    + "        AND estado_reg = 0\n"
                    + "        AND id_empleado = ?1\n"
                    + "LIMIT 1;", SegInoperativos.class);
            q.setParameter(1, idEmpleado);
            q.setParameter(2, Util.dateFormat(fechaEvento));
            return (SegInoperativos) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
