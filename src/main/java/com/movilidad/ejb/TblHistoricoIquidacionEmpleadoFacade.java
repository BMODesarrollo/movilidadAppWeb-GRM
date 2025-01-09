/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.TblHistoricoIquidacionEmpleado;
import com.movilidad.util.beans.TblHistoricoLiquidacionEmpleadoDTO;
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
public class TblHistoricoIquidacionEmpleadoFacade extends AbstractFacade<TblHistoricoIquidacionEmpleado> implements TblHistoricoIquidacionEmpleadoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TblHistoricoIquidacionEmpleadoFacade() {
        super(TblHistoricoIquidacionEmpleado.class);
    }

    @Override
    public List<TblHistoricoIquidacionEmpleado> findAllByFechaMes(Date desde, Date hasta) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    t.*\n"
                    + "FROM\n"
                    + "    tbl_historico_iquidacion_empleado t\n"
                    + "WHERE\n"
                    + "     t.desde=?1 AND t.hasta=?2 AND t.estado_reg=0;", TblHistoricoIquidacionEmpleado.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            return q.getResultList();

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public int eliminarDatos(Date desde, Date hasta, String userBorra) {
        String sql = "UPDATE tbl_historico_iquidacion_empleado t \n"
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

    @Override
    public List<TblHistoricoLiquidacionEmpleadoDTO> findAllByFechaMesResumenPorGrupo(Date desde, Date hasta) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    desde AS desde,\n"
                    + "    hasta AS hasta,\n"
                    + "    pmg.nombre_grupo AS grupo,\n"
                    + "    ifnull(SUM(diasLaborados),0) AS diasLaborados,\n"
                    + "    ifnull(SUM(diasNovedad),0) AS diasNovedad,\n"
                    + "    ifnull(SUM(puntosEmpresa),0) AS puntosEmpresa,\n"
                    + "    ifnull(SUM(puntos_pm_conciliados),0) AS puntosPmConciliados,\n"
                    + "    ifnull(SUM(vr_bono_salarial),0) AS vrBonoSalarial,\n"
                    + "    ifnull(SUM(vr_bono_alimentos),0) AS vrBonoAlimentos,\n"
                    + "    ifnull(SUM(puntosVrBonoSalarial),0) AS puntosVrBonoSalarial,\n"
                    + "    ifnull(SUM(diasVrBonoSalarial),0) AS diasVrBonoSalarial,\n"
                    + "    ifnull(SUM(diasVrBonoAlimentos),0) AS diasVrBonoAlimentos,\n"
                    + "    ifnull(SUM(descuentoPuntos),0) AS descuentoPuntos,\n"
                    + "    ifnull(SUM(descuentoDiasSalarial),0) AS descuentoDiasSalarial,\n"
                    + "    ifnull(SUM(descuentoDiasAlimentos),0) AS descuentoDiasAlimentos,\n"
                    + "    ifnull(SUM(subtotalBonoSalarial),0) AS subtotalBonoSalarial,\n"
                    + "    ifnull(SUM(subtotalBonoAlimento),0) AS subtotalBonoAlimento,\n"
                    + "    ifnull(SUM(totalBonoVehiculoTipo),0) AS totalBonoVehiculoTipo,\n"
                    + "    ifnull(SUM(BonoAmplitud),0) AS BonoAmplitud,\n"
                    + "    ifnull(SUM(totalPagar),0) AS totalPagar\n"
                    + "FROM\n"
                    + "    tbl_historico_iquidacion_empleado\n"
                    + "        INNER JOIN\n"
                    + "    pm_grupo pmg ON pmg.id_pm_grupo = id_grupo\n"
                    + "WHERE\n"
                    + "    desde = ?1 AND hasta = ?2\n"
                    + "        AND tbl_historico_iquidacion_empleado.estado_reg = 0\n"
                    + "GROUP BY id_grupo ORDER BY puntosPmConciliados asc", "resumenLiquidacionDTO");
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            return q.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<TblHistoricoIquidacionEmpleado> findByRangoFechas(Date desde, Date hasta) {
        try {
            Query q = em.createNativeQuery("        \n"
                    + "SELECT \n"
                    + "    t.*\n"
                    + "FROM\n"
                    + "    tbl_historico_iquidacion_empleado t\n"
                    + "WHERE\n"
                    + "    ((t.desde BETWEEN ?1 AND ?2)\n"
                    + "        OR (t.hasta BETWEEN ?1 AND ?2))\n"
                    + "        AND estado_reg = 0;", TblHistoricoIquidacionEmpleado.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            return q.getResultList();

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
