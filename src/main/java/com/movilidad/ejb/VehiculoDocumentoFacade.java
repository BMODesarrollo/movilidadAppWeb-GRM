/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoDocumentos;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author USUARIO
 */
@Stateless
public class VehiculoDocumentoFacade extends AbstractFacade<VehiculoDocumentos> implements VehiculoDocumentoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VehiculoDocumentoFacade() {
        super(VehiculoDocumentos.class);
    }

    @Override
    public VehiculoDocumentos findByVehiculoAndTipoDocumento(Integer idVehiculo, Integer idTipoDocumento, Integer idDocumento, Date fechaDesde, Date fechaHasta) {
        try {
            String sql = "SELECT * FROM vehiculo_documentos where "
                    + "id_vehiculo_documento <> ?1 and id_vehiculo = ?2 and "
                    + "vigente_desde = ?3 and vigente_hasta = ?4 and "
                    + "id_vehiculo_tipo_documento = ?5 and estado_reg = 0 LIMIT 1;";
            Query query = em.createNativeQuery(sql, VehiculoDocumentos.class);
            query.setParameter(1, idDocumento);
            query.setParameter(2, idVehiculo);
            query.setParameter(3, Util.dateFormat(fechaDesde));
            query.setParameter(4, Util.dateFormat(fechaHasta));
            query.setParameter(5, idTipoDocumento);

            return (VehiculoDocumentos) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retorna el registro anterior, en base a los parámetros ingresados
     *
     * @param idVehiculo Identificador unico de objeto Vehiculo
     * @param idTipoDocumento Identificador unico de objeto
     * VehiculoTipoDocumento
     *
     * @return retorna objeto VehiculoDocumentos
     */
    @Override
    public VehiculoDocumentos obtenerDocumentoAnterior(Integer idVehiculo, Integer idTipoDocumento) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    vehiculo_documentos\n"
                    + "WHERE\n"
                    + "    id_vehiculo = ?1\n"
                    + "        AND id_vehiculo_tipo_documento = ?2\n"
                    + "        AND estado_reg = 0\n"
                    + "        AND activo = 1\n"
                    + "ORDER BY vigente_hasta DESC\n"
                    + "LIMIT 1;";

            Query query = em.createNativeQuery(sql, VehiculoDocumentos.class);
            query.setParameter(1, idVehiculo);
            query.setParameter(2, idTipoDocumento);

            return (VehiculoDocumentos) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param idGopUnidadFuncional
     * @return retorna listado de documentos registrados
     */
    @Override
    public List<VehiculoDocumentos> findAllByActivos(int idGopUnidadFuncional) {
        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and v.id_gop_unidad_funcional = ?1\n";

            String sql = "SELECT\n"
                    + "	vd.*\n"
                    + "FROM\n"
                    + "	vehiculo_documentos vd\n"
                    + "inner join vehiculo v on vd.id_vehiculo = v.id_vehiculo\n"
                    + "where\n"
                    + "	vd.estado_reg = 0\n"
                    + sql_unida_func
                    + "ORDER BY\n"
                    + "	vd.id_vehiculo asc,\n"
                    + "	vd.activo desc;";
            Query query = em.createNativeQuery(sql, VehiculoDocumentos.class);
            query.setParameter(1, idGopUnidadFuncional);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Permite realizar la búsqueda del objeto VehiculoDocumento que cumpla con
     * los requerimientos
     *
     * @param idVehiculo Identificador unico de objeto Vehiculo
     * @param idTipoDocumento Identificador unico de objeto
     * VehiculoTipoDocumento
     * @param fechaDesde fecha desde en formato yyyy-MM-dd
     * @param fechaHasta fecha hasta en formato yyyy-MM-dd
     * @param idDocumento Identificador unico de objeto VehiculoDocumento
     * @return objeto VehiculoDocumento
     */
    @Override
    public VehiculoDocumentos verificarRegistro(Integer idVehiculo, Integer idTipoDocumento, Date fechaDesde, Date fechaHasta, Integer idDocumento) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    vehiculo_documentos\n"
                    + "WHERE\n"
                    + "    id_vehiculo = ?1\n"
                    + "        AND id_vehiculo_tipo_documento = ?2\n"
                    + "        AND activo = 1\n"
                    + "        AND estado_reg = 0\n"
                    + "        AND ((?3 BETWEEN vigente_desde AND vigente_hasta)\n"
                    + "        OR (?4 BETWEEN vigente_desde AND vigente_hasta))\n"
                    + "        AND id_vehiculo_documento <> ?5\n"
                    + "LIMIT 1;";

            Query query = em.createNativeQuery(sql, VehiculoDocumentos.class);
            query.setParameter(1, idVehiculo);
            query.setParameter(2, idTipoDocumento);
            query.setParameter(3, Util.dateFormat(fechaDesde));
            query.setParameter(4, Util.dateFormat(fechaHasta));
            query.setParameter(5, idDocumento);

            return (VehiculoDocumentos) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Permite realizar la busqueda del objeto VehiculoDocumento que cumpla con
     * los requerimientos
     *
     * @param idVehiculo Identificador unico de objeto Vehiculo
     * @param idTipoDocumento Identificador unico de objeto
     * VehiculoTipoDocumento
     * @param fecha fecha formato yyyy-MM-dd
     * @return retorna objeto VehiculoDocumentos
     */
    @Override
    public VehiculoDocumentos findByActivoAndVigente(Integer idVehiculo, Integer idTipoDocumento, String fecha) {
        try {
            String sql = "SELECT * "
                    + "FROM vehiculo_documentos "
                    + "WHERE ?1 between vigente_desde AND vigente_hasta "
                    + "AND id_vehiculo = ?2 "
                    + "AND id_vehiculo_tipo_documento = ?3 "
                    + "AND activo = 1 "
                    + "AND estado_reg = 0 "
                    + "LIMIT 1";
            Query q = em.createNativeQuery(sql, VehiculoDocumentos.class);
            q.setParameter(1, fecha);
            q.setParameter(2, idVehiculo);
            q.setParameter(3, idTipoDocumento);
            return (VehiculoDocumentos) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Permite realizar la busqueda del objeto VehiculoDocumento que cumpla con
     * los requerimientos
     *
     * @param idVehiculo Identificador unico de objeto Vehiculo
     * @param idTipoDocumento Identificador unico de objeto
     * VehiculoTipoDocumento
     * @return retorna objeto VehiculoDocumentos
     */
    @Override
    public VehiculoDocumentos findByActivo(Integer idVehiculo, Integer idTipoDocumento) {
        try {
            String sql = "SELECT * "
                    + "FROM vehiculo_documentos "
                    + "WHERE id_vehiculo = ?1 "
                    + "AND id_vehiculo_tipo_documento = ?2 "
                    + "AND activo = 1 "
                    + "AND estado_reg = 0 "
                    + "LIMIT 1";
            Query q = em.createNativeQuery(sql, VehiculoDocumentos.class);
            q.setParameter(1, idVehiculo);
            q.setParameter(2, idTipoDocumento);
            return (VehiculoDocumentos) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Permite realizar la búsqueda del objeto VehiculoDocumento que cumpla con
     * los requerimientos
     *
     * @param idVehiculo Identificador unico de objeto Vehiculo
     * @param idTipoDocumento Identificador unico de objeto
     * VehiculoTipoDocumento
     * @param fechaDesde fecha desde en formato yyyy-MM-dd
     * @param idDocumento Identificador unico de objeto VehiculoDocumento ó cero
     * en caso de que se vaya a realizar el registro de un documento.
     * @return objeto VehiculoDocumento
     */
    @Override
    public VehiculoDocumentos verificarRegistroDesde(Integer idVehiculo, Integer idTipoDocumento, Date fechaDesde, Integer idDocumento) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    vehiculo_documentos\n"
                    + "WHERE\n"
                    + "    id_vehiculo = ?1\n"
                    + "        AND id_vehiculo_tipo_documento = ?2\n"
                    + "        AND activo = 1\n"
                    + "        AND estado_reg = 0\n"
                    + "        AND vigente_desde = ?3 \n"
                    + "        AND id_vehiculo_documento <> ?4\n"
                    + "LIMIT 1;";

            Query query = em.createNativeQuery(sql, VehiculoDocumentos.class);
            query.setParameter(1, idVehiculo);
            query.setParameter(2, idTipoDocumento);
            query.setParameter(3, Util.dateFormat(fechaDesde));
            query.setParameter(4, idDocumento);

            return (VehiculoDocumentos) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<VehiculoDocumentos> findDocVencidos(Integer idVehiculo, Date fecha) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    vd.*\n"
                + "FROM\n"
                + "    vehiculo_documentos vd\n"
                + "        INNER JOIN\n"
                + "    vehiculo_tipo_documentos vtd ON vd.id_vehiculo_tipo_documento = vtd.id_vehiculo_tipo_documento\n"
                + "WHERE\n"
                + "    vtd.vencimiento = 1\n"
                + "        AND DATE(?2) > vigente_hasta\n"
                + "        AND vd.activo = 1\n"
                + "        AND vd.id_vehiculo = ?1\n"
                + "        AND vd.estado_reg = 0\n"
                + "        AND vtd.estado_reg = 0;", VehiculoDocumentos.class);
        q.setParameter(1, idVehiculo);
        q.setParameter(2, Util.dateFormat(fecha));
        return q.getResultList();
    }

    @Override
    public List<VehiculoDocumentos> findAllDocVencidos(Date fecha, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND v.id_gop_unidad_funcional = ?2\n";

        Query q = em.createNativeQuery("SELECT \n"
                + "    vd.*\n"
                + "FROM\n"
                + "    vehiculo_documentos vd\n"
                + "        INNER JOIN\n"
                + "    vehiculo_tipo_documentos vtd ON vd.id_vehiculo_tipo_documento = vtd.id_vehiculo_tipo_documento\n"
                + "        INNER JOIN\n"
                + "    vehiculo v ON v.id_vehiculo= vd.id_vehiculo\n"
                + "WHERE\n"
                + "    vtd.vencimiento = 1\n"
                + "        AND DATE(?1) > vigente_hasta\n"
                + "        AND vd.activo = 1\n"
                + "        AND vd.estado_reg = 0\n"
                + sql_unida_func
                + "        AND vtd.estado_reg = 0;", VehiculoDocumentos.class);
        q.setParameter(1, Util.dateFormat(fecha));
        q.setParameter(2, idGopUnidadFuncional);
        return q.getResultList();
    }

}
