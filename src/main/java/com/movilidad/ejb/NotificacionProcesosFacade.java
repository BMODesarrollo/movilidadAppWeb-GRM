package com.movilidad.ejb;

import com.movilidad.model.Empleado;
import com.movilidad.model.NotificacionProcesos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author luis
 */
@Stateless
public class NotificacionProcesosFacade extends AbstractFacade<NotificacionProcesos> implements NotificacionProcesosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotificacionProcesosFacade() {
        super(NotificacionProcesos.class);
    }

    @Override
    public List<Empleado> getEmployeesEmail(int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       WHERE id_gop_unidad_funcional = ?1\n";
            String sql = "SELECT * FROM empleado"
                    + sql_unida_func
                    + ";";
            Query query = em.createNativeQuery(sql, Empleado.class);
            query.setParameter(1, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Empleado findEmpleadoByEmail(String email) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM empleado "
                    //                    + "WHERE email_corporativo = ?1 LIMIT 1;", Empleado.class)
                    + "where email_corporativo like '%" + email + "%' limit 1;", Empleado.class);
//                    .setParameter(1, email);
            return (Empleado) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public NotificacionProcesos findByCodigoByIdGopUnidadFunc(String codigo, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?2\n";
            String sql = "select * from notificacion_procesos where codigo_proceso = ?1 and estado_reg = 0 "
                    + sql_unida_func
                    + ";";
            Query query = em.createNativeQuery(sql, NotificacionProcesos.class);
            query.setParameter(1, codigo);
            query.setParameter(2, idGopUnidadFuncional);
            return (NotificacionProcesos) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public NotificacionProcesos findByCodigo(String codigo) {
        try {
            String sql = "SELECT \n"
                    + "    np.*\n"
                    + "FROM\n"
                    + "    notificacion_procesos np\n"
                    + "WHERE\n"
                    + "    np.codigo_proceso = ?1 AND np.estado_reg = 0";
            Query query = em.createNativeQuery(sql, NotificacionProcesos.class);
            query.setParameter(1, codigo);
            return (NotificacionProcesos) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<NotificacionProcesos> findAll(int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?1\n";
            String sql = "select * from notificacion_procesos where estado_reg = 0"
                    + sql_unida_func
                    + ";";
            Query query = em.createNativeQuery(sql, NotificacionProcesos.class);
            query.setParameter(1, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
