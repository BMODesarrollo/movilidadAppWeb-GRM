package com.movilidad.ejb;

import com.movilidad.model.Novedad;
import com.movilidad.util.beans.AccidenteCtrl;
import com.movilidad.util.beans.InformeAccidente;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface NovedadFacadeLocal {

    void create(Novedad novedad);

    void edit(Novedad novedad);

    void remove(Novedad novedad);

    void desasignarOperador(Date fechaIni, Date fechaFin, int codEmpleado);
    
    void desasignarOperadorAusentismo(Date fechaIni, Date fechaFin, int idEmpleado);

    Novedad verificarNovedadPMSinFechas(Date fecha, int idEmpleado, int idNovedadTipoDetalle);

    Novedad find(Object id);

    Novedad findByNovedadDano(int id);

    Novedad findByMulta(int id);

    InformeAccidente obtenerDetalleAccidente(Date fechaIni, Date fechaFin, int idGopUnidadFuncional);

    List<AccidenteCtrl> obtenerDetalleAccidente(Date fecha, int idGopUnidadFuncional);

    List<Novedad> findAll(Date fecha);

    List<Novedad> obtenerTq04(Date fechaIncio, Date fechaFin);

    List<Novedad> obtenerCambiosVehiculo(Date fechaIncio, Date fechaFin);

    List<Novedad> getAccidentes(Date fechaIncio, Date fechaFin, int idGopUnidadFuncional);

    List<Novedad> getQuejas(Date fechaIncio, Date fechaFin);

    List<Novedad> getNovedadesSNC(Date fechaIncio, Date fechaFin);

    List<Novedad> getNovedadesMaestroConsulta(Date fechaInicio, Date fechaFin, List<Integer> detalles, int idGopUnidadFuncional);

    List<Novedad> findChangeVehiculo(Date fechaIni, Date fechaFin, int idGopUnidadFuncional);

    List<Novedad> findByDateRange(Date fechaIni, Date fechaFin, int idGopUnidadFuncional);

    List<Novedad> findAusentismosByDateRange(Date fechaIni, Date fechaFin, int idGopUnidadFuncional);

    List<Novedad> findByDateRangeAndIdEmpleado(Date fechaIni, Date fechaFin, int idEmpleado);
    
    List<Novedad> findByDateRangeAndIdEmpleadoSenior(Date fechaIni, Date fechaFin, int idEmpleado, boolean procede);

    List<Integer> findByDateRangeAndIdEmpleadoSeguim(Date fechaIni, Date fechaFin, int idEmpleado);
    
    List<Integer> findByDateRangeAndIdEmpleadoSeguimSenior(Date fechaIni, Date fechaFin, int idEmpleado);

    List<Integer> findByDateRangeAndIdEmpleadoDocu(Date fechaIni, Date fechaFin, int idEmpleado);
    
    List<Integer> findByDateRangeAndIdEmpleadoDocuSenior(Date fechaIni, Date fechaFin, int idEmpleado);

    List<Novedad> findRange(int[] range);

    int count();

    List<Novedad> findAllForMtto(Date fecha);

    List<Novedad> findByDateRangeForMtto(Date fechaIni, Date fechaFin);

    List<Novedad> liquidaPM();

    List<Novedad> findByDateRangeForLiquidaPM(Date fechaIni, Date fechaFin);

    Novedad validarNovedadConFechas(int empleado, Date desde, Date hasta);

    Novedad validarNovedad(int empleado, int idDetalleTipoNovedad);

    List<Novedad> findNovsAfectaDisp(Date desde, Date hasta);

    int updateClasificacion(int idNovedad, int idClasificacionNov);

    int updateEstadoNovedad(int idNovedad, int estadoNovedad, Date fechaHoraCierre, String usuarioCierre);

    Novedad findNovedadByIdVehiculo(Integer idVehiculo, int estadoNovedad);

    List<Novedad> findNovsAfectaDispByFecha(Date desde, Date hasta);

    List<Novedad> findNovsAfectaDispByFechaAndEstado(Date desde, Date hasta, Integer estadoNovedad);

    List<Novedad> findByDateRangeAndIdEmpleadoAndIdGopUnidadFunc(Date fechaIni,
            Date fechaFin, int idEmpleado, int idGopUnidadFuncional, int idGrupo);

    /**
     * Permite conocer si una fecha se encuentra liquidada en un periodo de
     * fechas
     *
     * @param d Objeto Date
     * @return BigDecimal 1 para fecha liquidada y 0 para fecha no loquidada,
     * null si error
     */
    Long existLiquidacionByFecha(Date d);

    /**
     * Devuelve sumatoria total de novedades de atv abiertas para la fecha por
     * unidad funcional.
     *
     * @param fecha
     * @param estado_nov
     * @param idGopUnidadFuncional
     * @param fechaUltimoCierre
     * @return
     */
    Long findTotalNovedadesAtvByEstado(Date fecha, int estado_nov, int idGopUnidadFuncional, Date fechaUltimoCierre);

    Long totalAusentismos(Date fecha, int idGopUnidadFunc, int idnovedadTipoAusentismo, Date fechaUltimoCierre);

    /**
     * Permite consultar las novedades que requieran de servicio ATV
     *
     * @param desde
     * @param hasta
     * @param idGopUF
     * @return
     */
    List<Novedad> findNovsAfectaATV(Date desde, Date hasta, int idGopUF);

    /**
     * Permite liquidar las novedades de atv en un rango de fecha por unidad
     * funcional
     *
     * @param idGopUF
     * @param desde
     * @param hasta
     * @return
     */
    int liquidarNovedadAtvByIdGopUFAndFechas(Integer idGopUF, Date desde, Date hasta);

    /**
     * Permite consultar las novedades que requieran de servicio ATV por
     * propietario, solo se permite consultar por mes
     *
     * @param fecha
     * @param idAtvPrestador
     * @return
     */
    List<Novedad> findNovsAfectaATVByPropietario(Date fecha, Integer idAtvPrestador);

    /**
     * Permite consultar las novedades de ATV que han sido liquidadas por rango
     * de fechas y por propietario
     *
     * @param desde
     * @param hasta
     * @param idAtvPrestador
     * @param idGopUF
     * @return
     */
    List<Novedad> findNovedadAtvLiquidadaByPropietario(Date desde, Date hasta, Integer idAtvPrestador, int idGopUF);

    /**
     * Permite consultar las novedades de ausentismos que luego serán enviadas a
     * kactus
     *
     * @param fechaIni
     * @param fechaFin
     * @param idGopUnidadFuncional
     * @return
     */
    List<Novedad> findAusentismosAutorizacionNovedadByDateRange(Date fechaIni, Date fechaFin, int idGopUnidadFuncional);
    
    List<Novedad> obtenerAusentismosConsulta(Date fechaIni, Date fechaFin, int idGopUnidadFuncional);

    /**
     *
     * @param fechaIni fecha inicio
     * @param fechaFin fecha fin
     * @param idEmpleado
     * @param idNovedad
     * @return Ausentimos de un empleado por rango de fechas
     */
    List<Novedad> getAusentismosByRangoFechasAndIdEmpleado(Date fechaIni, Date fechaFin, int idEmpleado, int idNovedad);
    
    Novedad findNovedadAfectaDisponibilidadFechaVehiculo(int idVehiculo, Date d);
    
    /**
     * Permite identificar si @idNovedad ha sido creada por el @username indicado.
     * @param idNovedad identificador de la novedad que se desea buscar.
     * @param username nombre del usuario que se desea validar si creó la novedad.
     * @param fecha corresponde a la fecha a la que debe corresponder la novedad
     * @return true si y solo si @username corresponde al username de la table para el registro @idNovedad
     *         false en cualquier otro caso.
     */
    boolean findNovedadByUserCreate(int idNovedad, String username, Date fecha);
    
    public Novedad findUltimoAusentismoByDateRangeEmple(Date fecha, int idEmpleado);

    /**
     * Permite identificar una novedad que corresponde a una infracción, para 
     * esto se auxilia de los datos enviados como parametros
     * @param idTipoNovedad identificador del tipo de novedad
     * @param fechaNovedad fecha de la novedad
     * @param idEmpleado identificador del empleado a quien ese le crea ala novedad
     * @param placaVehiculo placa del móvil asociado a la novedad
     * @param observaciones cadena de caracteres que describe la novedad
     * @return 
     */
    Novedad findNovedadInfraccion(int idTipoNovedad, Date fechaNovedad, int idEmpleado, int placaVehiculo, String observaciones);

    List<Novedad> obtenerNovedadesSinRecapacitacion(Date fechaIni, Date fechaFin, int idGopUnidadFuncional);

}
