/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.BitacoraAccInItinere;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Julián Arévaloh
 */
@Stateless
public class AccInItinereFacade extends AbstractFacade<BitacoraAccInItinere> implements AccInItinereFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccInItinereFacade() {
        super(BitacoraAccInItinere.class);
    }

    @Override
    public List<BitacoraAccInItinere> getAccidentesInItinere(Date fechaIncio, Date fechaFin, int idGopUnidadFuncional) {
        try {
        String uf = idGopUnidadFuncional == 0 ? " " : " AND t1.id_gop_unidad_funcional = " + idGopUnidadFuncional + " ";
        String sql = "SELECT t1.fecha, t1.hora, t1.observaciones,IFNULL(t2.codigo,'N/A') as codigo,\n" +
        "IFNULL(t2.placa,'N/A') as placa,t3.codigo_tm as codigo_operador, \n" +
        "concat(t3.nombres,' ',t3.apellidos) as nombre_operador, \n" +
        "t3.identificacion, t4.descripcion_tipo_novedad as tipo, t5.descripcion_tipo_novedad as tipo_evento\n" +
        "FROM novedad t1 \n" +
        "LEFT JOIN vehiculo t2 ON t1.id_vehiculo=t2.id_vehiculo \n" +
        "INNER JOIN empleado t3 ON t1.id_empleado=t3.id_empleado \n" +
        "INNER JOIN novedad_tipo t4 ON t1.id_novedad_tipo=t4.id_novedad_tipo \n" +
        "INNER JOIN novedad_tipo_detalles t5 ON t1.id_novedad_tipo_detalle=t5.id_novedad_tipo_detalle \n" +
        "WHERE t1.id_novedad_tipo = 10 \n" +
        " AND t1.estado_reg = 0 \n" +
        uf +        
        " AND t1.fecha BETWEEN ?1 AND ?2 \n" +
        "ORDER BY t1.creado DESC";
        Query query = em.createNativeQuery(sql, BitacoraAccInItinere.class);
        query.setParameter(1, Util.dateFormat(fechaIncio));
        query.setParameter(2, Util.dateFormat(fechaFin));
        //q.setParameter(3, idAtvPrestador);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
