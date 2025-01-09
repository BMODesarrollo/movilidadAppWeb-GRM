/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.TblHistoricoIquidacionEmpleado;
import com.movilidad.model.TblLiquidacionEmpleadoMes;
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
public class TblLiquidacionEmpleadoMesFacade extends AbstractFacade<TblLiquidacionEmpleadoMes> implements TblLiquidacionEmpleadoMesFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TblLiquidacionEmpleadoMesFacade() {
        super(TblLiquidacionEmpleadoMes.class);
    }

    @Override
    public List<TblLiquidacionEmpleadoMes> findAllByFechaMes(Date fecha) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    t.*\n"
                    + "FROM\n"
                    + "    tbl_liquidacion_empleado_mes t\n"
                    + "WHERE\n"
                    + "    ?1 BETWEEN t.desde AND t.hasta;", TblLiquidacionEmpleadoMes.class);
            q.setParameter(1, Util.dateFormat(fecha));
            return q.getResultList();

        } catch (Exception e) {

            return new ArrayList<>();
        }
    }

    @Override
    public int eliminarDatos(Date desde, Date hasta, String userBorra) {
        try {
            Query q = em.createNativeQuery("UPDATE tbl_liquidacion_empleado_mes t \n"
                    + "SET \n"
                    + "    t.estado_reg = 1,\n"
                    + "    t.user_borra=?3,\n"
                    + "    t.modificado=?4\n"
                    + "WHERE\n"
                    + "    t.desde = ?1 AND t.hasta = ?2;", TblHistoricoIquidacionEmpleado.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            q.setParameter(3, userBorra);
            q.setParameter(4, MovilidadUtil.fechaCompletaHoy());
            return q.executeUpdate();

        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void generarReporte(Date desde, Date hasta, String userGenera) {
        try {
            Query query = em.createNativeQuery("{call spSetLiquidarBono(?1,?2,'',?3)}")
                    .setParameter(1, Util.dateFormat(desde))
                    .setParameter(3, userGenera)
                    .setParameter(2, Util.dateFormat(hasta));
            query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
