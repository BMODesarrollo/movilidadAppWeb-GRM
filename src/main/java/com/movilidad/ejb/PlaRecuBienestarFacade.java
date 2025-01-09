package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuBienestar;
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
public class PlaRecuBienestarFacade extends AbstractFacade<PlaRecuBienestar> implements PlaRecuBienestarFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    public PlaRecuBienestarFacade() {
        super(PlaRecuBienestar.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<PlaRecuBienestar> findAllByFechaRangeAndEstadoReg(Date desde, Date hasta, int estadoReg) {
        try {
            String sql = "SELECT \n"
                    + "    b.*\n"
                    + "FROM\n"
                    + "    pla_recu_bienestar b\n"
                    + "WHERE\n"
                    + "    DATE(b.fecha_inicio) = ?1 AND\n"
                    + "    DATE(b.fecha_fin) = ?2 AND\n"
                    + "        b.estado_reg = ?3;";
            Query query = em.createNativeQuery(sql, PlaRecuBienestar.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, estadoReg);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PlaRecuBienestar> findAllByFechaRange(Date desde, Date hasta) {
        try {
            String sql = "SELECT \n"
                    + "    b.*\n"
                    + "FROM\n"
                    + "    pla_recu_bienestar b\n"
                    + "WHERE\n"
                    + "    (( ?1 <= DATE(fecha_inicio) AND DATE(fecha_inicio) <=  ?2) OR\n"
                    + "     ( ?1 <= DATE(fecha_fin) AND DATE(fecha_fin) <=  ?2) OR \n"
                    + "     ( ?1 <= DATE(fecha_fin) AND DATE(fecha_fin) >  ?2)) AND\n"
                    + "        b.estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuBienestar.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PlaRecuBienestar findBienestar(Date fechaIni, Date fechaFin, Integer turno, Integer idEmpleado) {
        try {
            String sql = "SELECT \n"
                    + "    b.*\n"
                    + "FROM\n"
                    + "    pla_recu_bienestar b\n"
                    + "WHERE\n"
                    + "    (( ?1 <= DATE(b.fecha_inicio) AND DATE(b.fecha_inicio) <=  ?2) OR\n"
                    + "     ( ?1 <= DATE(b.fecha_fin) AND DATE(b.fecha_fin) <=  ?2) OR\n"
                    + "     ( ?1 <= DATE(b.fecha_fin) AND DATE(b.fecha_fin) >  ?2)) AND\n"
                    + "        b.id_pla_recu_turno = ?3 \n"
                    + "        AND b.id_empleado = ?4 \n"
                    + "        AND b.estado_reg = 0 \n";
            Query query = em.createNativeQuery(sql, PlaRecuBienestar.class);
            query.setParameter(1, Util.dateFormat(fechaIni));
            query.setParameter(2, Util.dateFormat(fechaFin));
            query.setParameter(3, turno);
            query.setParameter(4, idEmpleado);
            return (PlaRecuBienestar) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PlaRecuBienestar> findByEmpleado(int idEmpleado) {
        try {
            String sql = "SELECT \n"
                    + "    b.*\n"
                    + "FROM\n"
                    + "    pla_recu_bienestar b\n"
                    + "WHERE\n"
                    + "     b.id_empleado = ?1 \n"
                    + "     AND b.estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuBienestar.class);
            query.setParameter(1, idEmpleado);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PlaRecuBienestar> findByMotivo(int idMotivo) {
        try {
            String sql = "SELECT \n"
                    + "    b.*\n"
                    + "FROM\n"
                    + "    pla_recu_bienestar b\n"
                    + "WHERE\n"
                    + "     b.id_pla_recu_motivo = ?1 \n"
                    + "     AND b.estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuBienestar.class);
            query.setParameter(1, idMotivo);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PlaRecuBienestar> findByTurno(int idTurno) {
        try {
            String sql = "SELECT \n"
                    + "    b.*\n"
                    + "FROM\n"
                    + "    pla_recu_bienestar b\n"
                    + "WHERE\n"
                    + "     b.id_pla_recu_turno = ?1 \n"
                    + "     AND b.estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuBienestar.class);
            query.setParameter(1, idTurno);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PlaRecuBienestar> findByIdGopUnidadFuncional(int idGopUnidadFuncional) {
        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND e.id_gop_unidad_funcional = ?1\n";
            Query q = em.createNativeQuery("SELECT \n"
                    + "    e.*\n"
                    + "FROM\n"
                    + "    pla_recu_bienestar e\n"
                    + "WHERE\n"
                    + "    e.estado_reg = 0\n"
                    + sql_unida_func, PlaRecuBienestar.class);
            q.setParameter(1, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public String findRestrictionByIdEmployee(int idEmpl) {
        try {
            String sql = "SELECT t.turno\n"
                    + "FROM pla_recu_turno t\n"
                    + "JOIN rgmo.pla_recu_bienestar b\n"
                    + "ON t.id_pla_recu_turno = b.id_pla_recu_turno\n"
                    + "WHERE b.fecha_fin >= CURDATE()\n"
                    + "AND b.id_empleado = ?1";

            Query query = em.createNativeQuery(sql);
            query.setParameter(1, idEmpl);
            List<String> resultados = query.getResultList();
            if (resultados.isEmpty()) {
                return "N.A"; // No hay resultados, devuelve "N.A"
            }
            return resultados.get(0); // Devuelve el primer resultado
        } catch (Exception e) {
            return null;
        }
    }

}
