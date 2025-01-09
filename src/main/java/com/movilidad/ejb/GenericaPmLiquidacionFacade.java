/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmLiquidacion;
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
public class GenericaPmLiquidacionFacade extends AbstractFacade<GenericaPmLiquidacion> implements GenericaPmLiquidacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaPmLiquidacionFacade() {
        super(GenericaPmLiquidacion.class);
    }

    @Override
    public List<GenericaPmLiquidacion> findAllByFechaMes(Date fecha, int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    t.*\n"
                    + "FROM\n"
                    + "    generica_pm_liquidacion t\n"
                    + "WHERE\n"
                    + "    ?1 BETWEEN t.desde AND t.hasta AND t.id_param_area;", GenericaPmLiquidacion.class);
            q.setParameter(1, Util.dateFormat(fecha));
            return q.getResultList();

        } catch (Exception e) {

            return new ArrayList<>();
        }
    }

    @Override
    public int eliminarDatos(Date desde, Date hasta, String userBorra, int idArea) {
        try {
            Query q = em.createNativeQuery("UPDATE generica_pm_liquidacion t \n"
                    + "SET \n"
                    + "    t.estado_reg = 1,\n"
                    + "    t.user_borra=?3,\n"
                    + "    t.modificado=?4\n"
                    + "WHERE\n"
                    + "    t.desde = ?1 AND t.hasta = ?2 AND t.id_param_area = ?5;", GenericaPmLiquidacion.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            q.setParameter(3, userBorra);
            q.setParameter(4, MovilidadUtil.fechaCompletaHoy());
            q.setParameter(5, idArea);
            return q.executeUpdate();

        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void generarReporte(Date desde, Date hasta, int idArea, String userGenera) {
        try {
            Query query = em.createNativeQuery("{call spLiquidaGenerica(?1,?2,'',?3,?4)}")
                    .setParameter(1, Util.dateFormat(desde))
                    .setParameter(2, Util.dateFormat(hasta))
                    .setParameter(3, idArea)
                    .setParameter(4, userGenera);
            query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
