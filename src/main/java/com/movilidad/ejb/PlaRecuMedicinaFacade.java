package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuMedicina;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author luis.lancheros
 */
@Stateless
public class PlaRecuMedicinaFacade extends AbstractFacade<PlaRecuMedicina> implements PlaRecuMedicinaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlaRecuMedicinaFacade() {
        super(PlaRecuMedicina.class);
    }

    @Override
    public List<PlaRecuMedicina> findAll() {
        try {
            String sql = "SELECT * FROM pla_recu_medicina WHERE estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuMedicina.class);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PlaRecuMedicina> findAllByGropUnidadFun(int idGopUnidadFuncional) {
        try {
            
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND id_gop_unidad_funcional = ?1\n";
            Query q = em.createNativeQuery( "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    pla_recu_medicina\n"
                    + "WHERE\n"
                    + "    estado_reg = 0\n"
                    + sql_unida_func, PlaRecuMedicina.class);
            q.setParameter(1, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String findRestrictionByIdEmployee(int idEmpl) {
        try {
            String sql = "SELECT c.descripcion\n"
                    + "FROM pla_recu_conduccion c\n"
                    + "JOIN rgmo.pla_recu_medicina m\n"
                    + "ON c.id_pla_recu_conduccion = m.id_pla_recu_conduccion\n"
                    + "WHERE m.fecha_fin >= CURDATE()\n"
                    + "AND m.id_empleado = ?1";

            Query query = em.createNativeQuery(sql);
            query.setParameter(1, idEmpl);
            List<String> resultados = query.getResultList();
            if (resultados.isEmpty()) {
                return "N.A"; // No hay resultados, devuelve "N.A"
            }
            return resultados.get(0); // Devuelve el primer resultado
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir detalles del error para depuración
            return null; // En caso de error, devuelve null
        }
    }

    @Override
    public PlaRecuMedicina findMedicina(Date fechaIni, Date fechaFin, Integer idEmpleado) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    pla_recu_medicina\n"
                    + "WHERE\n"
                    + "    DATE(fecha_ini) = ?1 "
                    + "AND DATE(fecha_fin) = ?2\n"
                    + "        AND id_empleado = ?3";
            Query query = em.createNativeQuery(sql, PlaRecuMedicina.class);
            query.setParameter(1, Util.dateFormat(fechaIni));
            query.setParameter(2, Util.dateFormat(fechaFin));
            query.setParameter(3, idEmpleado);

            return (PlaRecuMedicina) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<PlaRecuMedicina> findAllByFechaRange(Date desde, Date hasta) {
       try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    pla_recu_medicina\n"
                    + "WHERE\n"
                    + "    (( ?1 <= DATE(fecha_ini) AND DATE(fecha_ini) <=  ?2) OR\n"
                    + "     ( ?1 <= DATE(fecha_fin) AND DATE(fecha_fin) <=  ?2) OR\n"
                    + "     (?1 <= DATE(fecha_fin) AND DATE(fecha_fin) > ?2)) AND\n"
                    + "    estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuMedicina.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            return query.getResultList();    
        } catch (Exception e) {
            return null;
        }
    }

}
