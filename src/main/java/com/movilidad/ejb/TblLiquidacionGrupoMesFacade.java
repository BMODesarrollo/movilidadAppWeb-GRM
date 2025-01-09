/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.TblLiquidacionGrupoMes;
import com.movilidad.utils.MovilidadUtil;
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
public class TblLiquidacionGrupoMesFacade extends AbstractFacade<TblLiquidacionGrupoMes> implements TblLiquidacionGrupoMesFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TblLiquidacionGrupoMesFacade() {
        super(TblLiquidacionGrupoMes.class);
    }

    @Override
    public List<TblLiquidacionGrupoMes> findAllByRangoFechas(Date desde, Date hasta) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    tbl_liquidacion_grupo_mes\n"
                + "WHERE\n"
                + "    desde = ?1 AND hasta = ?2\n"
                + "        AND estado_reg = 0;", TblLiquidacionGrupoMes.class);
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        return q.getResultList();
    }

    
    @Override
    public int eliminarDatos(Date desde, Date hasta, String userBorra) {
        String sql = "UPDATE tbl_liquidacion_grupo_mes t \n"
                + "SET \n"
                + "    t.estado_reg = 1,\n"
                + "    t.user_borra=?3,\n"
                + "    t.modificado=?4\n"
                + "WHERE\n"
                + "    t.desde >= ?1 AND t.hasta <= ?2;";
        try {
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            q.setParameter(3, userBorra);
            q.setParameter(4, MovilidadUtil.fechaCompletaHoy());
            return q.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
