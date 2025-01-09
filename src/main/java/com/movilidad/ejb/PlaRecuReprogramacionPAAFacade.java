package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuReprogramacionPAA;
import java.util.ArrayList;
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
public class PlaRecuReprogramacionPAAFacade extends AbstractFacade<PlaRecuReprogramacionPAA> implements PlaRecuReprogramacionPAAFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    public PlaRecuReprogramacionPAAFacade() {
        super(PlaRecuReprogramacionPAA.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public List<PlaRecuReprogramacionPAA> findAll() {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    pla_recu_reprogramacion_paa\n"
                    + "WHERE\n"
                    + "estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuReprogramacionPAA.class);
            return query.getResultList();    
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<PlaRecuReprogramacionPAA> findAllActiveDays() {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    pla_recu_reprogramacion_paa\n"
                    + "WHERE\n"
                    + "    (lunes = 1 \n"
                    + "     OR martes = 1 \n"
                    + "     OR miercoles = 1 \n"
                    + "     OR jueves = 1 \n"
                    + "     OR viernes = 1) \n"
                    + " AND estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuReprogramacionPAA.class);
            return query.getResultList();    
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Permite ´validar los criterios de no duplicidad para los registros de la tabla
     * En este caso se valida que un empleado no tenga activas reprogramaciones 
     * para cualquiera de los días.
     * @param idEmpleado indice del empleado en la tabla de Empleados
     * @return un registro 
     */
    @Override
    public PlaRecuReprogramacionPAA findReprogramacionPAA(Integer idEmpleado) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    pla_recu_reprogramacion_paa\n"
                    + "WHERE\n"
                    + "     id_empleado = ?1 \n"
                    + "     AND (lunes = 1 \n"
                    + "     OR martes = 1 \n"
                    + "     OR miercoles = 1 \n"
                    + "     OR jueves = 1 \n"
                    + "     OR viernes = 1) \n"
                    + "     AND estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuReprogramacionPAA.class);
            query.setParameter(1, idEmpleado);

            // Usar getResultList para evitar excepciones si no hay resultados
            List<PlaRecuReprogramacionPAA> results = query.getResultList();
            if (results.isEmpty()) {
                System.out.println("No se encontraron registros que coincidan con los criterios especificados.");
                return null;
            }
            // Devuelve el primer resultado si es necesario uno solo
            return results.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PlaRecuReprogramacionPAA> findByEmpleado(int idEmpleado) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    pla_recu_reprogramacion_paa\n"
                    + "WHERE\n"
                    + "     id_empleado = ?1 \n"
                    + "     AND estado_reg = 0 ;";
            Query query = em.createNativeQuery(sql, PlaRecuReprogramacionPAA.class);
            query.setParameter(1, idEmpleado);
            return query.getResultList();    
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<PlaRecuReprogramacionPAA> findByIdGopUnidadFuncional(int idGopUnidadFuncional) {
        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND e.id_gop_unidad_funcional = ?1\n";
            Query q = em.createNativeQuery("SELECT \n"
                    + "    e.*\n"
                    + "FROM\n"
                    + "    pla_recu_reprogramacion_paa e\n"
                    + "WHERE\n"
                    + "    e.estado_reg = 0\n"
                    + sql_unida_func, PlaRecuReprogramacionPAA.class);
            q.setParameter(1, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
}
