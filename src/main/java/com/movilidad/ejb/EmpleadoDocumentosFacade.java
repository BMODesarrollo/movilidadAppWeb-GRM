/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.EmpleadoDocumentos;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Soluciones IT
 */
@Stateless
public class EmpleadoDocumentosFacade extends AbstractFacade<EmpleadoDocumentos> implements EmpleadoDocumentosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpleadoDocumentosFacade() {
        super(EmpleadoDocumentos.class);
    }

    @Override
    public EmpleadoDocumentos findByIdEmpleadoAndIdTipoDocu(int idEmpleado, int idTipoDocu) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    empleado_documentos\n"
                    + "WHERE\n"
                    + "    id_empleado = ?1\n"
                    + "        AND id_empleado_tipo_documento = ?2\n"
                    + "        AND activo = 1\n"
                    + "ORDER BY vigente_desde DESC\n"
                    + "LIMIT 1;", EmpleadoDocumentos.class);
            q.setParameter(1, idEmpleado);
            q.setParameter(2, idTipoDocu);
            return (EmpleadoDocumentos) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public EmpleadoDocumentos findByIdEmpleadoDocumentoCap(int idEmpleado) {
        try {
            Query q = em.createNativeQuery("SELECT ed.*\n"
                    + "FROM empleado_documentos ed\n"
                    + "JOIN (\n"
                    + "    SELECT id_empleado, MAX(fecha_radicado) AS fecha_radicado\n"
                    + "    FROM empleado_documentos\n"
                    + "    WHERE id_empleado = ?1\n"
                    + "    AND id_empleado_tipo_documento IN (8, 9)\n"
                    + "    AND activo = 1\n"
                    + "    AND estado_reg = 0\n"
                    + "    GROUP BY id_empleado\n"
                    + ") max_fecha ON ed.id_empleado = max_fecha.id_empleado AND ed.fecha_radicado = max_fecha.fecha_radicado\n"
                    + "WHERE ed.id_empleado = ?1\n"
                    + "AND ed.id_empleado_tipo_documento IN (8, 9)\n"
                    + "AND ed.activo = 1\n"
                    + "AND ed.estado_reg = 0\n"
                    + "LIMIT 1;", EmpleadoDocumentos.class);
            q.setParameter(1, idEmpleado);
            return (EmpleadoDocumentos) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Permite realizar la busqueda del objeto EmpleadoDocumentos que cumpla con
     * los requerimientos
     *
     * @param idEmpleado Identificador unico de objeto Empleado
     * @param idEmpTpDoc Identificador unico de objeto EmpleadoTipoDocumentos
     * @param fecha fecha formato yyyy-MM-dd
     * @return retorna objeto EmpleadoDocumentos
     */
    @Override
    public EmpleadoDocumentos findByActivoAndVigente(Integer idEmpleado, Integer idEmpTpDoc, String fecha) {
        try {
            String sql = "SELECT * "
                    + "FROM empleado_documentos "
                    + "WHERE ?1 between vigente_desde AND vigente_hasta "
                    + "AND id_empleado = ?2 "
                    + "AND id_empleado_tipo_documento = ?3 "
                    + "AND activo = 1 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, EmpleadoDocumentos.class);
            q.setParameter(1, fecha);
            q.setParameter(2, idEmpleado);
            q.setParameter(3, idEmpTpDoc);
            return (EmpleadoDocumentos) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<EmpleadoDocumentos> findAllActivos() {
        Query q = em.createNativeQuery("SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    empleado_documentos\n"
                + "WHERE\n"
                + "    estado_reg = 0;", EmpleadoDocumentos.class);
        return q.getResultList();
    }

    @Override
    public EmpleadoDocumentos findByIdEmpleadoAndIdTipoDocuAndFechas(int idEmpleado, int idTipoDocu, Date desde, Date hasta) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    ed.*\n"
                    + "FROM\n"
                    + "    empleado_documentos ed\n"
                    + "WHERE\n"
                    + "    ed.id_empleado = ?1\n"
                    + "        AND ed.id_empleado_tipo_documento = ?2\n"
                    + "        AND (?3 BETWEEN ed.vigente_desde AND ed.vigente_hasta\n"
                    + "        || ?4 BETWEEN ed.vigente_desde AND ed.vigente_hasta)\n"
                    + "        AND estado_reg = 0\n"
                    + "LIMIT 1;", EmpleadoDocumentos.class);
            q.setParameter(1, idEmpleado);
            q.setParameter(2, idTipoDocu);
            q.setParameter(3, Util.dateFormat(desde));
            q.setParameter(4, Util.dateFormat(hasta));
            return (EmpleadoDocumentos) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Permite realizar la busqueda del objeto EmpleadoDocumentos que cumpla con
     * los requerimientos
     *
     * @param idEmpleado Identificador unico de objeto Empleado
     * @param idEmpTpDoc Identificador unico de objeto EmpleadoTipoDocumentos
     * @return retorna objeto EmpleadoDocumentos
     */
    @Override
    public EmpleadoDocumentos findByActivo(Integer idEmpleado, Integer idEmpTpDoc) {
        try {
            String sql = "SELECT * "
                    + "FROM empleado_documentos "
                    + "WHERE id_empleado = ?1 "
                    + "AND id_empleado_tipo_documento = ?2 "
                    + "AND activo = 1 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, EmpleadoDocumentos.class);
            q.setParameter(1, idEmpleado);
            q.setParameter(2, idEmpTpDoc);

            return (EmpleadoDocumentos) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int updateUltimoDocActivo(int idEmpleadoTipoDoc, int idEmpledo, int idEmpleadoDoc, boolean vigencia) {
        String part = "AND activo = 1";
        if (vigencia) {
            part = "ORDER BY vigente_hasta DESC limit 1";
        }

        Query q = em.createNativeQuery("update \n"
                + "    empleado_documentos set activo = 0\n"
                + "WHERE\n"
                + "    id_empleado = ?1\n"
                + "        AND id_empleado_tipo_documento = ?2\n"
                + "        AND id_empleado_documento <> ?3\n"
                + part);
        q.setParameter(1, idEmpledo);
        q.setParameter(2, idEmpleadoTipoDoc);
        q.setParameter(3, idEmpleadoDoc);
        return q.executeUpdate();
    }

    @Override
    public List<EmpleadoDocumentos> findDocVencidos(Integer idEmpleado, Date fecha) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    ed.*\n"
                + "FROM\n"
                + "    empleado_documentos ed\n"
                + "        INNER JOIN\n"
                + "    empleado_tipo_documentos etd ON ed.id_empleado_tipo_documento = etd.id_empleado_tipo_documento\n"
                + "WHERE\n"
                + "    etd.vencimiento = 1\n"
                + "        AND DATE(?2) > vigente_hasta\n"
                + "        AND ed.activo = 1\n"
                + "        AND ed.id_empleado = ?1\n"
                + "        AND ed.estado_reg = 0\n"
                + "        AND etd.estado_reg = 0;", EmpleadoDocumentos.class);
        q.setParameter(1, idEmpleado);
        q.setParameter(2, Util.dateFormat(fecha));
        return q.getResultList();
    }

    @Override
    public List<EmpleadoDocumentos> findAllDocVencidos(Date fecha, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND e.id_gop_unidad_funcional = ?2\n";
        Query q = em.createNativeQuery("SELECT \n"
                + "    ed.*\n"
                + "FROM\n"
                + "    empleado_documentos ed\n"
                + "        INNER JOIN\n"
                + "    empleado_tipo_documentos etd ON ed.id_empleado_tipo_documento = etd.id_empleado_tipo_documento\n"
                + "        INNER JOIN\n"
                + "    empleado e ON e.id_empleado = ed.id_empleado\n"
                + "WHERE\n"
                + "    etd.vencimiento = 1\n"
                + "        AND DATE(?1) > vigente_hasta\n"
                + "        AND ed.activo = 1\n"
                + "        AND ed.estado_reg = 0\n"
                + sql_unida_func
                + "        AND etd.estado_reg = 0;", EmpleadoDocumentos.class);
        q.setParameter(1, Util.dateFormat(fecha));
        q.setParameter(2, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public List<EmpleadoDocumentos> findAllActivosUF(int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND em.id_gop_unidad_funcional = ?1\n";

//        Query q = em.createNativeQuery("SELECT \n"
//                + "    ed.*\n"
//                + "FROM\n"
//                + "    empleado_documentos ed\n"
//                + "WHERE\n"
//                + "    ed.estado_reg = 0\n"
//                + sql_unida_func, EmpleadoDocumentos.class);
        Query q = em.createNativeQuery("SELECT * FROM empleado em INNER JOIN empleado_documentos emdoc ON em.id_empleado = emdoc.id_empleado WHERE id_empleado_estado = 1"
                + sql_unida_func, EmpleadoDocumentos.class);
        q.setParameter(1, idGopUnidadFuncional);
        return q.getResultList();
    }

}
