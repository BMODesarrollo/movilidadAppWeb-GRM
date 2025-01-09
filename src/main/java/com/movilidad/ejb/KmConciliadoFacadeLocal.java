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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface KmConciliadoFacadeLocal {

    void create(KmConciliado kmConciliado);

    void edit(KmConciliado kmConciliado);

    void updateKmComercial(int codVehiculo, Integer kmComercial, Date fecha, int idGopUnidadFuncional);

    void updateKmHlp(Date fecha, int idGopUnidadFuncional);

    void updateKmRecorrido(Date fecha, int idGopUnidadFuncional);

    void remove(KmConciliado kmConciliado);

    void removerKmMtto(Date fecha, int idGopUnidadFuncional);

    int totalVehiculos(Date fecha, int tipoVehiculo, int idGopUnidadFuncional);

    boolean verificarSubida(Date fecha, int idGopUnidadFuncional);

    KmConciliado find(Object id);

    BigDecimal getKmMtto(Date fecha, int idGopUnidadFuncional);

    List<KmsComercial> getKmComerciales(Date fecha, int idGopUnidadFuncional);

    List<KmConciliado> getKmHlp(Date fecha, int tipoVehiculo, int idGopUnidadFuncional);

    List<KmConciliado> getKmHlpConsolidado(Date fecha, int tipoVehiculo, int idGopUnidadFuncional);

    List<KmConciliado> getKmComConsolidado(Date fecha, int tipoVehiculo, int idGopUnidadFuncional);

    List<KmConciliado> findAll(Date fecha, int idGopUnidadFuncional);

    List<KmConciliado> findDate(Date fecha, int idGopUnidadFuncional);

    List<InformeContabilidad> obtenerRangoFechas(Date fechaIncio, Date fechaFin, int idGopUnidadFuncional);

    List<InformeContabilidad235> obtenerRangoFechas235(Date fechaIncio, Date fechaFin);

    List<InformeContabilidadNo235> obtenerRangoFechasNo235(Date fechaIncio, Date fechaFin);

    List<KmConciliado> findRange(int[] range);

    int count();

    public List<KmConciliado> getKmHlp235(Date fecha, int tipoVehiculo);

    List<KmConciliado> findAllByFechas(Date desde, Date hasta);

}
