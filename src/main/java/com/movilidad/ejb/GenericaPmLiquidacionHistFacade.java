/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmLiquidacionHist;
import com.movilidad.utils.MovilidadUtil;
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
public class GenericaPmLiquidacionHistFacade extends AbstractFacade<GenericaPmLiquidacionHist> implements GenericaPmLiquidacionHistFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaPmLiquidacionHistFacade() {
        super(GenericaPmLiquidacionHist.class);
    }

    @Override
    public List<GenericaPmLiquidacionHist> findAllByFechaMes(Date desde, Date hasta, int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    t.*\n"
                    + "FROM\n"
                    + "    generica_pm_liquidacion_hist t\n"
                    + "WHERE\n"
                    + "     t.desde=?1 "
                    + "AND t.hasta=?2 "
                    + "AND t.estado_reg=0 "
                    + "AND t.id_param_area=?3;", GenericaPmLiquidacionHist.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            q.setParameter(3, idArea);
            return q.getResultList();

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public int eliminarDatos(Date desde, Date hasta, String userBorra, int idArea) {
        String sql = "UPDATE generica_pm_liquidacion_hist t \n"
                + "SET \n"
                + "    t.estado_reg = 1,\n"
                + "    t.user_borra=?3,\n"
                + "    t.modificado=?4\n"
                + "WHERE\n"
                + "    t.desde >= ?1 AND t.hasta <= ?2 AND t.id_param_area = ?5;";
        try {
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            q.setParameter(3, userBorra);
            q.setParameter(4, MovilidadUtil.fechaCompletaHoy());
            q.setParameter(5, idArea);
            return q.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
