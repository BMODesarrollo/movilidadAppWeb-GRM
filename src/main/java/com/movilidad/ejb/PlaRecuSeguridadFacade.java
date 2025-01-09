package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuSeguridad;
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
 * @author Omar.beltran
 */
@Stateless
public class PlaRecuSeguridadFacade extends AbstractFacade<PlaRecuSeguridad> implements PlaRecuSeguridadFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    public PlaRecuSeguridadFacade() {
        super(PlaRecuSeguridad.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<PlaRecuSeguridad> findAllByFechaRangeAndEstadoReg(Date desde, Date hasta, int estadoReg) {
        try {
            String sql = "SELECT \n"
                    + "    b.*\n"
                    + "FROM\n"
                    + "    pla_recu_seguridad b\n"
                    + "WHERE\n"
                    + "    DATE(b.fecha_inicio) = ?1 AND\n"
                    + "    DATE(b.fecha_fin) = ?2 \n"
                    + "        AND b.estado_reg = ?3;";
            Query query = em.createNativeQuery(sql, PlaRecuSeguridad.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, estadoReg);
            return query.getResultList();    
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PlaRecuSeguridad> findAllByFechaRange(Date desde, Date hasta) {
        try {
            String sql = "SELECT \n"
                    + "    b.*\n"
                    + "FROM\n"
                    + "    pla_recu_seguridad b\n"
                    + "WHERE\n"
                    + "    (( ?1 <= DATE(fecha_inicio) AND DATE(fecha_inicio) <=  ?2) OR\n"
                    + "     ( ?1 <= DATE(fecha_fin) AND DATE(fecha_fin) <=  ?2) OR\n"
                    + "     (?1 <= DATE(fecha_fin) AND DATE(fecha_fin) > ?2)) AND\n"
                    + "    estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuSeguridad.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            return query.getResultList();    
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PlaRecuSeguridad findSeguridad(Date fechaIni, Date fechaFin, String descripcion, Integer idEmpleado) {
        try {
            String sql = "SELECT \n"
                    + "    b.*\n"
                    + "FROM\n"
                    + "    pla_recu_seguridad b\n"
                    + "WHERE\n"
                    + "    DATE(b.fecha_inicio) = ?1 AND\n"
                    + "    DATE(b.fecha_fin) = ?2 \n"
                    + "        AND b.id_empleado = ?4 \n"
                    + "        AND b.estado_reg = 0 \n"
                    + "        AND b.descripcion = ?3;";
            Query query = em.createNativeQuery(sql, PlaRecuSeguridad.class);
            query.setParameter(1, Util.dateFormat(fechaIni));
            query.setParameter(2, Util.dateFormat(fechaFin));
            query.setParameter(3, descripcion);
            query.setParameter(4, idEmpleado);

            return (PlaRecuSeguridad)query.getSingleResult();    
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PlaRecuSeguridad> findByEmpleado(int idEmpleado) {
        try {
        String sql = "SELECT \n"
                    + "    b.*\n"
                    + "FROM\n"
                    + "    pla_recu_seguridad b\n"
                    + "WHERE\n"
                    + "     b.id_empleado = ?1 \n"
                    + "     AND b.fecha_fin >= CURDATE() \n"
                    + "     AND b.estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuSeguridad.class);
            query.setParameter(1, idEmpleado);
            return query.getResultList();    
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<PlaRecuSeguridad> findByIdGopUnidadFuncional(int idGopUnidadFuncional) {
        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND e.id_gop_unidad_funcional = ?1\n";
            Query q = em.createNativeQuery("SELECT \n"
                    + "    e.*\n"
                    + "FROM\n"
                    + "    pla_recu_seguridad e\n"
                    + "WHERE\n"
                    + "    e.estado_reg = 0\n"
                    + sql_unida_func, PlaRecuSeguridad.class);
            q.setParameter(1, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}

