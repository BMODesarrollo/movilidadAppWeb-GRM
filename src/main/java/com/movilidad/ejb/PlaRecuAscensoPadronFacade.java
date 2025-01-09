package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuAscensoPadron;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Omar Beltrán
 */
@Stateless
public class PlaRecuAscensoPadronFacade extends AbstractFacade<PlaRecuAscensoPadron> implements PlaRecuAscensoPadronFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlaRecuAscensoPadronFacade() {
        super(PlaRecuAscensoPadron.class);
    }

    @Override
    public List<PlaRecuAscensoPadron> estadoReg(int estado) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM for_des_ascenso_padron WHERE estado_reg = ?1", PlaRecuAscensoPadron.class);
            q.setParameter(1, estado);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Formación y Desarrollo Ascenso Padron");
            return null;
        }
    }

    @Override
    public List<PlaRecuAscensoPadron> findAll() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM for_des_ascenso_padron WHERE estado_reg = 0", PlaRecuAscensoPadron.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Formación y Desarrollo Ascenso Padron");
            return null;
        }
    }

    @Override
    public PlaRecuAscensoPadron findByName(String categoriaName) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM for_des_ascenso_padron WHERE name = ?1 AND estado_reg = 0", PlaRecuAscensoPadron.class);
            q.setParameter(1, categoriaName);
            return (PlaRecuAscensoPadron) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error en Facade Formación y Desarrollo 'Ascenso Padron'");
            return null;
        }
    }

    @Override
    public List<PlaRecuAscensoPadron> findByIdGopUnidadFuncional(int idGopUnidadFuncional) {
        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND e.id_gop_unidad_funcional = ?1\n";
            Query q = em.createNativeQuery("SELECT \n"
                    + "    e.*\n"
                    + "FROM\n"
                    + "    for_des_ascenso_padron e\n"
                    + "WHERE\n"
                    + "    e.estado_reg = 0\n"
                    + sql_unida_func, PlaRecuAscensoPadron.class);
            q.setParameter(1, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    @Override
    public PlaRecuAscensoPadron find(int idEmpleado, Date fecha_ascenso) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM for_des_ascenso_padron WHERE id_empleado = ?1 "
                    + "AND DATE(fecha_ascenso_nomina) = ?2 "
                    + "AND estado_reg = 0", PlaRecuAscensoPadron.class
            );
            q.setParameter(1, idEmpleado);
            q.setParameter(2, fecha_ascenso);

            // Usar getResultList para evitar excepciones si no hay resultados
            List<PlaRecuAscensoPadron> results = q.getResultList();
            if (results.isEmpty()) {
                System.out.println("No se encontraron registros que coincidan con los criterios especificados.");
                return null;
            }

            // Devuelve el primer resultado si es necesario uno solo
            return results.get(0);

        } catch (Exception e) {
            System.out.println("Error en Facade Formación y Desarrollo 'Ascenso Padron': " + e.getMessage());
            return null;
        }
    }

}

