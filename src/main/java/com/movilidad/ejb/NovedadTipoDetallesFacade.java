package com.movilidad.ejb;

import com.movilidad.model.NovedadTipo;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.util.beans.GestionNovedad;
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
 * @author luis
 */
@Stateless
public class NovedadTipoDetallesFacade extends AbstractFacade<NovedadTipoDetalles> implements NovedadTipoDetallesFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadTipoDetallesFacade() {
        super(NovedadTipoDetalles.class);
    }

    @Override
    public NovedadTipoDetalles findByNovedadTipoId(NovedadTipo novedadTipo) {
        Query query = em.createNativeQuery("SELECT * FROM novedad_tipo_detalles "
                + "WHERE id_novedad_tipo = ?1 LIMIT 1;", NovedadTipoDetalles.class)
                .setParameter(1, novedadTipo.getIdNovedadTipo());
        return (NovedadTipoDetalles) query.getSingleResult();
    }

    @Override
    public List<NovedadTipoDetalles> findTipoAcc() {
        try {
            Query q = em.createNativeQuery("select * from novedad_tipo_detalles where id_novedad_tipo = 2 and estado_reg = 0", NovedadTipoDetalles.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Novedad Tipo Detalles");
            return null;
        }
    }

    @Override
    public List<NovedadTipoDetalles> findByTipo(int idTipo) {
        try {
            String sql = "SELECT \n"
                    + "    ntd.*\n"
                    + "FROM\n"
                    + "    novedad_tipo_detalles ntd\n"
                    + "WHERE\n"
                    + "    ntd.id_novedad_tipo= ?1\n"
                    + "        AND ntd.estado_reg = 0\n"
                    + "        AND ntd.afecta_programacion = 0;";

            Query query = em.createNativeQuery(sql, NovedadTipoDetalles.class);
            query.setParameter(1, idTipo);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<NovedadTipoDetalles> findByTipoNovedad(Integer idTipoNov) {
        try {
            String sql = "SELECT ntd.* "
                    + "FROM novedad_tipo_detalles ntd "
                    + "WHERE ntd.id_novedad_tipo = ?1 "
                    + "AND ntd.estado_reg = 0";
            Query query = em.createNativeQuery(sql, NovedadTipoDetalles.class);
            query.setParameter(1, idTipoNov);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<NovedadTipoDetalles> findByTipoNovedadForTC(Integer idTipoNov) {
        try {
            String sql = "SELECT ntd.* "
                    + "FROM novedad_tipo_detalles ntd "
                    + "WHERE ntd.id_novedad_tipo = ?1 AND id_novedad_tipo_detalle in (9, 12, 13)"
                    + "AND ntd.estado_reg = 0";
            Query query = em.createNativeQuery(sql, NovedadTipoDetalles.class);
            query.setParameter(1, idTipoNov);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<NovedadTipoDetalles> findByFechaIsActive() {
        String sql = "SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    novedad_tipo_detalles\n"
                + "WHERE\n"
                + "    fechas = 1 AND estado_reg = 0";
        Query q = em.createNativeQuery(sql, NovedadTipoDetalles.class);
        return q.getResultList();
    }

    @Override
    public List<NovedadTipoDetalles> findByTipoNovedadAndAfectaDisp(Integer idTipoNov) {
        String sql = "SELECT \n"
                + "    ntd.*\n"
                + "FROM\n"
                + "    novedad_tipo_detalles ntd\n"
                + "WHERE\n"
                + "    ntd.id_novedad_tipo = ?1\n"
                + "        AND ntd.afecta_disponibilidad = 1\n"
                + "        AND ntd.estado_reg = 0;";
        Query q = em.createNativeQuery(sql, NovedadTipoDetalles.class);
        q.setParameter(1, idTipoNov);
        return q.getResultList();
    }

    @Override
    public List<NovedadTipoDetalles> findByTipoNovedadAndBeMtto(Integer idTipoNov) {
        String sql = "SELECT \n"
                + "    ntd.*\n"
                + "FROM\n"
                + "    novedad_tipo_detalles ntd\n"
                + "WHERE\n"
                + "    ntd.id_novedad_tipo = ?1\n"
                + "        AND ntd.mtto = 1\n"
                + "        AND ntd.estado_reg = 0;";
        Query q = em.createNativeQuery(sql, NovedadTipoDetalles.class);
        q.setParameter(1, idTipoNov);
        return q.getResultList();
    }

    @Override
    public List<GestionNovedad> obtenerValoresGestorNovedades(Date desde, Date hasta, int idGopUnidadFuncional) {
        try {

            Query q1 = em.createNativeQuery("{call GestorNovedades_3(?1,?2,?3)}")
                    .setParameter(1, Util.dateFormat(desde))
                    .setParameter(2, Util.dateFormat(hasta))
                    .setParameter(3, idGopUnidadFuncional);
            q1.executeUpdate();

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?3\n";
            String sql = "SELECT \n"
                    + "    fecha,\n"
                    + "    id_novedad_tipo_detalle,\n"
                    + "    id_gop_unidad_funcional,\n"
                    + "    id_empleado_tipo_cargo,\n"
                    + "    suma_gestor,\n"
                    + "    COUNT(id_gestor_tmp) AS valor\n"
                    + "FROM\n"
                    + "    gestor_tmp\n"
                    + "WHERE\n"
                    + "    fecha BETWEEN ?1 AND ?2\n"
                    + sql_unida_func
                    + "GROUP BY fecha, id_novedad_tipo_detalle,id_gop_unidad_funcional,id_empleado_tipo_cargo,suma_gestor "
                    + "order by fecha;";
            Query q = em.createNativeQuery(sql, "GestionNovedadMapping");
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            q.setParameter(3, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<NovedadTipoDetalles> obtenerDetallesAfectaGestor() {
        try {
            String sql = "SELECT ntd.* "
                    + "FROM novedad_tipo_detalles ntd "
                    + "WHERE ntd.afecta_gestor = 1 "
                    + "AND ntd.estado_reg = 0";
            Query query = em.createNativeQuery(sql, NovedadTipoDetalles.class);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<NovedadTipoDetalles> obtenerDetallesNomina(String idsListaDetalles) {
        try {

            String sql_detalles_arr = idsListaDetalles == null ? ""
                    : "  AND ntd.id_novedad_tipo_detalle not in (" + idsListaDetalles + ")\n";
            String sql = "SELECT ntd.* "
                    + "FROM novedad_tipo_detalles ntd "
                    + "WHERE ntd.novedad_nomina = 1 "
                    + sql_detalles_arr
                    + "AND ntd.estado_reg = 0";
            Query query = em.createNativeQuery(sql, NovedadTipoDetalles.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<NovedadTipoDetalles> obtenerDetallesAccesoRapido() {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    novedad_tipo_detalles\n"
                    + "WHERE\n"
                    + "    nombre_acceso_rapido IS NOT NULL\n"
                    + "        AND estado_reg = 0;";
            Query query = em.createNativeQuery(sql, NovedadTipoDetalles.class);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
