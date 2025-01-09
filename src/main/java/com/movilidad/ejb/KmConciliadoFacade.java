/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.KmConciliado;
import com.movilidad.util.beans.InformeContabilidad;
import com.movilidad.util.beans.InformeContabilidad235;
import com.movilidad.util.beans.InformeContabilidadNo235;
import com.movilidad.util.beans.KmsComercial;
import com.movilidad.utils.Util;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class KmConciliadoFacade extends AbstractFacade<KmConciliado> implements KmConciliadoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KmConciliadoFacade() {
        super(KmConciliado.class);
    }

    @Override
    public List<KmConciliado> findAll(Date fecha, int idGopUnidadFuncional) {
        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  AND k.idGopUnidadFuncional.idGopUnidadFuncional = " + idGopUnidadFuncional + "\n";

            Query query = em.createQuery("SELECT k from KmConciliado k WHERE "
                    + "k.fecha = :fecha"
                    + sql_unida_func);
            query.setParameter("fecha", Util.toDate(Util.dateFormat(fecha)));
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<KmsComercial> getKmComerciales(Date fecha, int idGopUnidadFuncional) {
        refrescarTabla(fecha, idGopUnidadFuncional);
        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and prg_tc.id_gop_unidad_funcional = ?2\n";

            String sql = "SELECT \n"
                    + "prg_tc.id_vehiculo as codigo_vehiculo,\n"
                    + "	Sum(truncate(if((estado_operacion = 0 or estado_operacion = 1 or estado_operacion = 2 \n"
                    + "	or estado_operacion = 3 or estado_operacion = 4 or estado_operacion = 6 ) and prg_tarea.id_prg_tarea <> 4 , distance, 0),0)) AS comercial \n"
                    + "FROM \n"
                    + "	prg_tc \n"
                    + "INNER JOIN prg_tarea ON \n"
                    + "	prg_tc.id_task_type = prg_tarea.id_prg_tarea \n"
                    + "WHERE \n"
                    + "	prg_tc.fecha = ?1\n"
                    + " and prg_tarea.comercial= 1\n"
                    + "	and prg_tarea.sum_distancia = 1"
                    + "	and prg_tc.servbus is not null \n"
                    + "	and prg_tc.id_vehiculo is not null \n"
                    + sql_unida_func
                    + "GROUP BY codigo_vehiculo,comercial";
            Query query = em.createNativeQuery(sql, "KmsComercialMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
            query.setParameter(2, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void refrescarTabla(Date fecha, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and id_gop_unidad_funcional = ?3\n";
        Query query1 = em.createNativeQuery("update km_conciliado set km_comercial = ?1 , km_hlp = ?1 "
                + "where fecha = ?2"
                + sql_unida_func);
        query1.setParameter(1, 0);
        query1.setParameter(2, Util.dateFormat(fecha));
        query1.setParameter(3, idGopUnidadFuncional);
        query1.executeUpdate();
    }

    @Override
    public void updateKmComercial(int codVehiculo, Integer kmComercial, Date fecha, int idGopUnidadFuncional) {

        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and id_gop_unidad_funcional = ?4\n";

        Query query = em.createNativeQuery("update km_conciliado set km_comercial = ?1 "
                + "where id_vehiculo = ?2 "
                + sql_unida_func
                + "and fecha = ?3;");
        query.setParameter(1, kmComercial);
        query.setParameter(2, codVehiculo);
        query.setParameter(3, Util.dateFormat(fecha));
        query.setParameter(4, idGopUnidadFuncional);
        query.executeUpdate();
    }

    @Override
    public void updateKmHlp(Date fecha, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and id_gop_unidad_funcional = ?2\n";
        Query query = em.createNativeQuery("update km_conciliado set km_hlp = km_mtto - km_comercial "
                + "where fecha = ?1"
                + sql_unida_func
                + ";");
        query.setParameter(1, Util.dateFormat(fecha));
        query.setParameter(2, idGopUnidadFuncional);
        query.executeUpdate();
        updateKmRecorrido(fecha, idGopUnidadFuncional);
    }

    @Override
    public void updateKmRecorrido(Date fecha, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and id_gop_unidad_funcional = ?2\n";
        Query query = em.createNativeQuery("update km_conciliado set km_recorrido = km_comercial + km_hlp "
                + "where fecha = ?1"
                + sql_unida_func
                + ";");
        query.setParameter(1, Util.dateFormat(fecha));
        query.setParameter(2, idGopUnidadFuncional);
        query.executeUpdate();
    }

    @Override
    public boolean verificarSubida(Date fecha, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  AND k.idGopUnidadFuncional.idGopUnidadFuncional = " + idGopUnidadFuncional + "\n";
        Query query = em.createQuery("SELECT k from KmConciliado k WHERE k.fecha = :fecha"
                + sql_unida_func);
        query.setParameter("fecha", Util.toDate(Util.dateFormat(fecha)));
        return query.getResultList().isEmpty();
    }

    @Override
    public List<KmConciliado> getKmHlp(Date fecha, int tipoVehiculo, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  AND k.idGopUnidadFuncional.idGopUnidadFuncional = " + idGopUnidadFuncional + "\n";
        Query query = em.createQuery("SELECT k from KmConciliado k WHERE "
                + "k.kmHlp < 0 and k.fecha = :fecha and "
                + sql_unida_func
                + "k.idVehiculo.idVehiculoTipo.idVehiculoTipo = :tipo", KmConciliado.class);
        query.setParameter("fecha", Util.toDate(Util.dateFormat(fecha)));
        query.setParameter("tipo", tipoVehiculo);
        return query.getResultList();
    }

    @Override
    public List<KmConciliado> getKmHlpConsolidado(Date fecha, int tipoVehiculo, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  AND k.idGopUnidadFuncional.idGopUnidadFuncional = " + idGopUnidadFuncional + "\n";
        Query query = em.createQuery("SELECT k from KmConciliado k WHERE "
                //                + "(k.kmComercial/k.kmMtto) > 0.2 and "
                + " k.fecha = :fecha "
                + "and k.kmHlp > 20000 "
                + sql_unida_func
                + "and k.idVehiculo.idVehiculoTipo.idVehiculoTipo = :tipo "
                + "ORDER BY k.kmHlp DESC", KmConciliado.class);
        query.setParameter("fecha", Util.toDate(Util.dateFormat(fecha)));
        query.setParameter("tipo", tipoVehiculo);
        return query.getResultList();
    }

    @Override
    public List<KmConciliado> findDate(Date fecha, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  AND k.idGopUnidadFuncional.idGopUnidadFuncional = " + idGopUnidadFuncional + "\n";
            Query query = em.createQuery("SELECT k from KmConciliado k WHERE "
                    + "k.fecha = :fecha AND k.kmRecorrido > 0"
                    + sql_unida_func);
            query.setParameter("fecha", Util.toDate(Util.dateFormat(fecha)));
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int totalVehiculos(Date fecha, int tipoVehiculo, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and km_conciliado.id_gop_unidad_funcional = ?3\n";
        Query query = em.createNativeQuery("select count(*) from km_conciliado "
                + "inner join vehiculo on km_conciliado.id_vehiculo = vehiculo.id_vehiculo "
                + "where fecha = ?1 "
                + "and vehiculo.id_vehiculo_tipo = ?2 and km_recorrido > 0"
                + sql_unida_func
                + ";");
        query.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
        query.setParameter(2, tipoVehiculo);
        query.setParameter(3, idGopUnidadFuncional);
        long res = (Long) query.getSingleResult();
        return (int) res;
    }

    @Override
    public List<InformeContabilidad> obtenerRangoFechas(Date fechaIncio, Date fechaFin, int idGopUnidadFuncional) {
        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and k.id_gop_unidad_funcional = ?3\n";

            Query query = em.createNativeQuery("SELECT "
                    + "	v.codigo as codigo_vehiculo,"
                    + "	sum(k.km_contabilidad) total "
                    + "from "
                    + "	km_conciliado k "
                    + "INNER JOIN vehiculo v ON "
                    + "	k.id_vehiculo = v.id_vehiculo "
                    + "WHERE "
                    + "	k.fecha BETWEEN ?1 AND ?2 and k.km_recorrido > 0"
                    + sql_unida_func
                    + "	group by codigo_vehiculo;", "InformeContabilidadMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(fechaIncio)));
            query.setParameter(2, Util.toDate(Util.dateFormat(fechaFin)));
            query.setParameter(3, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<KmConciliado> getKmComConsolidado(Date fecha, int tipoVehiculo, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  AND k.idGopUnidadFuncional.idGopUnidadFuncional = " + idGopUnidadFuncional + "\n";

            Query query = em.createQuery("SELECT k from KmConciliado k WHERE "
                    //                + "(k.kmComercial/k.kmMtto) > 0.2 and "
                    + " k.fecha = :fecha "
                    + "and k.kmMtto > 0 "
                    + sql_unida_func
                    + "and k.idVehiculo.idVehiculoTipo.idVehiculoTipo = :tipo "
                    + "ORDER BY k.kmComercial DESC", KmConciliado.class);
            query.setParameter("fecha", Util.toDate(Util.dateFormat(fecha)));
            query.setParameter("tipo", tipoVehiculo);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<KmConciliado> getKmHlp235(Date fecha, int tipoVehiculo) {
        try {
            Query query = em.createQuery("SELECT k from KmConciliado k WHERE "
                    //                + "(k.kmComercial/k.kmMtto) > 0.2 and "
                    + " k.fecha = :fecha "
                    + "and k.kmMtto > 0 "
                    + "and k.idVehiculo.idVehiculoTipo.idVehiculoTipo = :tipo "
                    + "ORDER BY k.kmHlp235 DESC", KmConciliado.class);
            query.setParameter("fecha", Util.toDate(Util.dateFormat(fecha)));
            query.setParameter("tipo", tipoVehiculo);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<InformeContabilidad235> obtenerRangoFechas235(Date fechaIncio, Date fechaFin) {
        try {
            Query query = em.createNativeQuery("SELECT \n"
                    + "	v.codigo as codigo_vehiculo,\n"
                    + "	sum(k.km_com_235) as km_com_235,\n"
                    + "	sum(k.km_hlp_235) as km_hlp_235\n"
                    + "from \n"
                    + "	km_conciliado k \n"
                    + "INNER JOIN vehiculo v ON \n"
                    + "	k.id_vehiculo = v.id_vehiculo \n"
                    + "WHERE \n"
                    + "	k.fecha BETWEEN ?1 AND ?2 \n"
                    //                    + "	and k.km_comercial > 0\n"
                    + "	group by codigo_vehiculo;", "InformeContabilidad235Mapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(fechaIncio)));
            query.setParameter(2, Util.toDate(Util.dateFormat(fechaFin)));
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<InformeContabilidadNo235> obtenerRangoFechasNo235(Date fechaIncio, Date fechaFin) {
        try {
            Query query = em.createNativeQuery("SELECT \n"
                    + "	v.codigo as codigo_vehiculo,\n"
                    + "	sum(k.km_comercial) as comercial,\n"
                    + "	sum(k.km_hlp) as hlp\n"
                    + "from \n"
                    + "	km_conciliado k \n"
                    + "INNER JOIN vehiculo v ON \n"
                    + "	k.id_vehiculo = v.id_vehiculo \n"
                    + "WHERE \n"
                    + "	k.fecha BETWEEN ?1 AND ?2 \n"
                    + "	group by codigo_vehiculo;", "InformeContabilidadNo235Mapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(fechaIncio)));
            query.setParameter(2, Util.toDate(Util.dateFormat(fechaFin)));
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removerKmMtto(Date fecha, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and id_gop_unidad_funcional = ?2\n";
        Query query = em.createNativeQuery("DELETE from km_conciliado where fecha = ?1"
                + sql_unida_func
                + ";");
        query.setParameter(1, Util.dateFormat(fecha));
        query.setParameter(2, idGopUnidadFuncional);
        query.executeUpdate();
    }

    @Override
    public BigDecimal getKmMtto(Date fecha, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and id_gop_unidad_funcional = ?2\n";
            Query query = em.createNativeQuery("select sum(km_mtto) from km_conciliado where fecha= ?1"
                    + sql_unida_func
                    + ";");
            query.setParameter(1, Util.dateFormat(fecha));
            query.setParameter(2, idGopUnidadFuncional);
            return (BigDecimal) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return Util.CERO;
        }
    }

    @Override
    public List<KmConciliado> findAllByFechas(Date desde, Date hasta) {
        try {
            String sql = "SELECT id_km_conciliado, "
                    + "fecha, "
                    + "id_vehiculo, "
                    + "km_mtto, "
                    + "sum(km_comercial) as 'km_comercial', "
                    + "km_hlp, "
                    + "km_contabilidad, "
                    + "sum(km_com_235) as 'km_com_235', "
                    + "km_hlp_235, "
                    + "username, "
                    + "creado, "
                    + "modificado, "
                    + "estado_reg "
                    + "FROM km_conciliado "
                    + "where fecha between ?1 and ?2 "
                    + "group by id_vehiculo";
            Query q = em.createNativeQuery(sql, KmConciliado.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
