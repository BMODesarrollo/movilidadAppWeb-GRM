/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.dto.InsumoProgramacionDTO;
import com.movilidad.dto.KmPrgEjecDTO;
import com.movilidad.dto.PorcentajeDisponibilidadFlotaDTO;
import com.movilidad.dto.PrimerServicioDTO;
import com.movilidad.dto.ReporteSemanaActualDTO;
import com.movilidad.dto.ServbusPlanificadoDTO;
import com.movilidad.dto.ServbusPlanificadoDetalleDTO;
import com.movilidad.dto.ServbusTipoTablaDTO;
import com.movilidad.dto.SumEliminadoResponsableDTO;
import com.movilidad.dto.TP28UltimoServicioDTO;
import com.movilidad.dto.UltimoServicioDTO;
import com.movilidad.model.PrgClasificacionMotivo;
import com.movilidad.model.PrgSercon;
import com.movilidad.model.PrgStopPoint;
import com.movilidad.model.PrgTc;
import com.movilidad.util.beans.ConsolidadServicios;
import com.movilidad.util.beans.DiasSinOperar;
import com.movilidad.util.beans.InformeOperacion;
import com.movilidad.util.beans.KmsAdicionalesCtrl;
import com.movilidad.util.beans.KmsOperador;
import com.movilidad.util.beans.KmsPerdidosArt;
import com.movilidad.util.beans.KmsPerdidosBi;
import com.movilidad.util.beans.KmsVehiculo;
import com.movilidad.util.beans.NovedadesTQ04;
import com.movilidad.util.beans.SerconList;
import com.movilidad.util.beans.ServbusIdTipoVehiculo;
import com.movilidad.util.beans.ServbusList;
import com.movilidad.util.beans.ServiciosPorSalir;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author luis
 */
@Local
public interface PrgTcFacadeLocal {

    void create(PrgTc prgTc);

    void edit(PrgTc prgTc);

    void remove(PrgTc prgTc);

    void editList(List<PrgTc> list);

    void updateTmDistance(Date fecha);

    void realizarCambioOperacion(Integer idEmpleado, Integer oldEmpleado, String sercon, Date fecha);

    PrgTc find(Object id);

    PrgTc findSerconByCodigoTmAndIdUnidadFunc(int codigoTm, Date fecha, int idGopUnidadFuncional);

    PrgTc findOperadorByCod(String codigoTm, Date fechaConsulta);

    PrgTc findServiceConServbus(String sercon, Date fechaConsulta, int idGopUnidadFunc);

    PrgTc obtenerFechaYRutaDiaPosterior(String sercon, Date fechaConsulta, int idGopUnidadFunc);

    KmsPerdidosArt getEliminadosArtCtrl(Date fecha, int idGopUnidadFuncional);

    KmsPerdidosBi getEliminadosBiCtrl(Date fecha, int idGopUnidadFuncional);

    KmsAdicionalesCtrl getAdicionalesCtrl(Date fecha, int idGopUnidadFuncional);
//    PrgTc findOperadorByCod(String codigoTm, Date fechaConsulta, String timeOrigin, String timeDestiny);

    List<PrgTc> findAllIdGopUnidadFuncional(int idGopUnidadFuncional);

    List<PrgTc> findByCodigoOperador(int value, Date fecha);

    List<PrgTc> findByFecha(Date fecha, Integer idGopUnidadFuncional);

    List<PrgTc> findServiciosEjecutadosByFechaAndUnidadFuncional(Date fecha, Integer idGopUnidadFuncional, Integer tipoServicio);

    List<PrgTc> findEliminadosByFecha(Date fecha, int idGopUnidadFuncional);

    List<PrgTc> findAdicionalesByFecha(Date fecha, int idGopUnidadFuncional);

    List<PrgTc> findByTipoVehiculo(Date fecha, int tipoVehiculo, int idGopUnidadFuncional);

    List<PrgTc> findAdicionalesByTipoVehiculo(Date fecha, int tipoVehiculo, int idGopUnidadFuncional);

    List<PrgTc> findEliminadosByTipoVehiculo(Date fecha, int tipoVehiculo, int idGopUnidadFuncional);

    List<PrgTc> findByFechaResumen(Date fecha, int tipoVehiculo);

    List<PrgTc> findRange(int[] range);

    List<SerconList> findSerconByServbus(String servbus, Date fecha, String timeOrigin, int idGopUnidadFuncional);

    List<ServbusList> findServbusBySercon(String sercon, Date fecha, String timeOrigin, int idGopUnidadFunc);

    List<ServbusList> findServbusBySerconSinOperador(String sercon, Date fecha, String timeOrigen, int idGopUnidadFunc);

    List<PrgTc> findTcBySercon(String sercon, Date fecha, String timeOrigin, String servbus, int idGopUnidadFunc);

    List<PrgTc> findAllTcBySercon(String sercon, Date fecha, String timeOrigin, int idEmpleado, int idGopUnidadFuncional, boolean dispo);

    List<PrgTc> findAllTcBySerconSinOperador(Date fecha, String timeOrigin, String sercon, int idGopUnidadFuncional);

    void eliminarPrgTc(PrgTc prgtc);

    BigDecimal getEliminadosByTipoVehiculo(Date fecha, int tipoVehiculo);

    BigDecimal getAdicionalesByTipoVehiculo(Date fecha, int tipoVehiculo, int idGopUnidadFuncional);

    BigDecimal getVacEjecutadosByTipoVehiculo(Date fecha, int tipoVehiculo);

    BigDecimal getComEjecutadosConciliadosByTipoVehiculo(Date fecha, int tipoVehiculo);

    BigDecimal getAdicionales(Date fecha);

    BigDecimal getVacComerciales(Date fecha);

    int count();

    public void saveChangeOneToOne(PrgTc prgtc1, PrgTc prgtc2);

    public List<PrgTc> changeOneToOneReturnPrgTcAfectados(PrgTc prgtc1, PrgTc prgtc2);

    public PrgTc findByCodigoAndHora(String codigoV, Date fechaConsulta, String timeOrigin, String timeDestiny, int idGopUnidadFuncional);

    public PrgTc findByCodigoAndHoraUnoAUno(String codigoV, Date fechaConsulta, String timeOrigin, int idGopUnidadFuncional);

    public PrgTc findOperadorByCodigoAndHora(String codigoTm, Date fechaConsulta, String timeOrigin);

    BigDecimal getDistance(PrgStopPoint prgFromStop, PrgStopPoint prgToStop);

    List<PrgTc> serviceSinBus(Date fecha, int idGopUnidadFuncional);

    List<PrgTc> serviceSinOpe(Date fecha, int idGopUnidadFuncional);

    List<PrgTc> operadoresDisponibles(Date fecha, int idGopUnidadFuncional);

    List<PrgTc> operadoresDisponiblesDiaPosterior(Date fecha, String timeDestiny, int idGopUnidadFuncional);

    BigDecimal findDistandeByFromStopAndToStop(int prgFromStop, int prgToStop);

    int asignarBusToServbus(Integer idVehiculo, String servbus, String fecha, String username, int idGopUF);

    int updateDesasignarVehiculoServbus(Integer idVehiculo, String servbus, String fecha, String username, int idGopUF);

    public int updatePrgTcUnoAUno(Integer id_ve, String serv, Date fecha,
            String timeOrigen, int oldVehiculo, String control,
            String observacion, String username, int idPrgTcResponsable,
            PrgClasificacionMotivo clasificacionMotivo, int idGopUnidadFuncional);

    List<PrgTc> prgTcUnoAUnoReturnPrgTcAfectados(String serv, Date fecha, String timeOrigen, int idGopUnidadFunc);

    public List<PrgTc> listarSerbus(Date date, int idGopUnidadFuncional);

    public List<PrgTc> listarSalidas(Date date, int idGopUnidadFuncional);

    List<KmsOperador> getKmByOperador(Date fechaInicio, Date fechaFin, Integer idGopUnidadFuncional);

    List<KmsVehiculo> getKmByVehiculo(Date fechaInicio, Date fechaFin, Integer idGopUnidadFuncional);

    List<DiasSinOperar> obtenerDiasSinOperar(Date fechaInicio, Date fechaFin, Integer idGopUnidadFuncional);

    PrgTc findVehiculoAsignar(int idVehiculo, Date fecha);

    public PrgTc buscarPrgTcSinVehiculo(String servbus, Date fecha, int idGopUnidadFuncional);

    /**
     * consultar las entradas a patio filtrando por patio
     *
     * @param fecha
     * @param fecha_hora
     * @param idPrgStopPoint
     * @param idGopUnidadFuncional
     * @return List<PrgTc>
     */
    List<PrgTc> entradasPatio(String fecha, String fecha_hora, int idPrgStopPoint, int idGopUnidadFuncional);

    List<PrgTc> obtenerInoperativos(Date fecha_inicio, Date fecha_fin, int idGopUnidadFuncional);

    /**
     * consultar las salidas a patio filtrando por patio
     *
     * @param fecha
     * @param fecha_hora
     * @param idPrgStopPoint
     * @param i id_prg_stop_point
     * @param idGopUnidadFuncional
     * @return
     */
    List<PrgTc> salidasPatio(String fecha, String fecha_hora, int idPrgStopPoint, int idGopUnidadFuncional);

    PrgTc servicioAnterior(PrgTc prgTc);

    /**
     *
     * @param hora
     * @param idEmpleado
     * @param fecha
     * @return
     */
//    PrgTc validarServicioEnEjecucionByHoraForOperador(String hora, int idEmpleado, Date fecha);
//    PrgTc validarServicioEnEjecucionByHoraAndVehiculo(String hora, String cod, Date fecha, int idGopUnidadFuncional);
    List<ServbusIdTipoVehiculo> getServbusAndIdTipoVehiculo(String fecha, int idGopUF);

    long countByFechas(Date fromDate, Date toDate, int idGopUF);

    PrgTc findOperadorProgramado(String codigoTm, Date fechaConsulta);

    PrgTc findPrgTcForDeleteAndChangeVehiculo(String sercon, Date fechaConsulta, String timeOrigin, int idGopUnidadFuncional);

    PrgTc findPrgTcAsignacionOperador(String sercon, Date fechaConsulta, String timeOrigin, int idGopUnidadFuncional);

    int desasignarOp(PrgTc p);

    int reasignarVe(PrgTc p);

//    PrgTcResumen validarConciliado(Date fecha);
    List<PrgTc> findEntradasMtto(Date fecha, int idpatio, int idTipoVehiculo);

    List<PrgTc> findSalidaMtto(Date fecha, int idpatio, int idTipoVehiculo);

    List<PrgTc> rutasEjecutadas(Date fechaDesde, Date fechaHasta, int idGopUnidadFunc);

    List<PrgTc> reporteVehiculoOperador(Date desde, Date hasta, String codVehiculo, String codEmpleado);

    public List<ConsolidadServicios> getConsolidadoPorHora(Date fecha);

    public List<ConsolidadServicios> getConsolidadoPorDia(Date desde, Date hasta);

    int removeByDate(Date d, int idGopUF);

    int updateByDate(Date d, int idGopUF);

    List<PrgTc> findByServBus(String cServbus, Date d, Integer idEmpleado);

    /**
     * Método que devuelve la programación individual de un operador
     *
     * @param fechaDesde
     * @param fechaHasta
     * @param codOperador
     * @return
     */
    List<PrgTc> obtenerConsultaProgramacion(Date fechaDesde, Date fechaHasta, String codOperador);

    List<InformeOperacion> InformeOperacionParaMtto();

    List<NovedadesTQ04> getNovedadesTq04(int idGopUnidadFuncional);

    /**
     * Método para obtener servicios por varios parametros, la consulta devuelve
     * los servicios de acuerdo a los parametros con valor.
     *
     * @param tabla valor identificador tabla del servicio
     * @param servicio nombre de la tarea del servicio
     * @param servBus servbus o bus logico del servicio
     * @param sercon sercon o servicio conductor del servicio
     * @param codigoOperador codigo tm identificador de cada operador
     * @param vehiculoC codigo identificador de cada vehículo
     * @param fechaConsulta fecha sobre la cual se hará la consulta
     * @param idGopUnidadFuncional Unidad funcional a la que pertenecen los
     * servicios
     * @return lista de servicios prgtc filtrados y ordenados por time_origin
     * ascendentemente
     *
     */
    List<PrgTc> findServicioGenerico(String tabla, String servicio, String servBus,
            String sercon, String codigoOperador, String vehiculoC, Date fechaConsulta,
            int idGopUnidadFuncional);

    /**
     * Retorna distacia recorrida por el empleado segun los parametros de fecha
     * y hora suministrados
     *
     * @param idEmpleado Identificador unico objeto Empleado
     * @param fecha String fotmato YYYY-MM-dd
     * @param hora String formato HH:mm:ss
     * @return
     */
    Object obtenerKmRecorridosByOperador(Integer idEmpleado, String fecha, String hora);

    /**
     * Retorna último servicio programado con vehiculo asignado.
     *
     * Estados de operacion (0 , 1, 2, 5)
     *
     * @param fecha
     * @return
     */
    PrgTc findUltimoServicioProgramado(Date fecha, int idGopUnidadFuncional);

    /**
     * Retorna último servicio programado para un empleado.
     *
     * @param idEmpledao
     * @param fecha
     * @param order ASC para obtener el primero del día, DESC para el último del
     * día
     * @return
     */
    PrgTc findFirtOrEndServiceByIdEmpleado(int idEmpledao, Date fecha, String order);

    /**
     * Retorna las entradas a patio de acuerdo al tiempo parametrizado
     *
     * @param fecha
     * @param idGopUnidadFuncional
     * @param time
     * @return
     */
    List<PrgTc> findVehiculosSinPresentacion(Date fecha, Integer idGopUnidadFuncional);

    /**
     * consultar las entradas a patio programadas, idGopUF si es 0, consulta
     * todos los registros
     *
     * @param fechaHora
     * @param idGopUF
     * @return List<PrgTc>
     */
    List<PrgTc> entradasPatio(Date fechaHora, int idGopUF);

    /**
     * consultar las salidas de patio programadas, idGopUF si es 0, consulta
     * todos los registros
     *
     * @param fechaHora
     * * @param idGopUF
     * @param idGopUF
     * @return List<PrgTc>
     */
    List<PrgTc> salidasPatio(Date fechaHora, int idGopUF);

    /**
     * Retorna los registros del vehiculo mayores a la hora suministrada y igual
     * a la fecha suministrada
     *
     * @param idVehiculo Identificador unico de Vehiculo
     * @param fechaHora Fecha y Hora ej: 2021-10-10 08:10:45
     * @param idGopUF Identificador UF
     * @return int 1 si tiene registros, 0 si no
     */
    List<PrgTc> getListPrgTcByIdVehiculoAndFechaAndHoraMayorIgual(Integer idVehiculo, Date fechaHora, int idGopUF);

    /**
     * Permite consultar los servicios de un vehiculo por fecha suministrada
     *
     * @param idVehiculo Identificador Vehiculo
     * @param fecha Fecha de consulta
     * @param idGopUF Identificador Unidad Funcional
     * @return List<PrgTc>
     */
    List<PrgTc> findServicesByVehiculo(Integer idVehiculo, Date fecha, int idGopUF);

    /**
     * Permite consultar la proxima entrada a patio, suministrando la fecha y
     * hora, esta debe ser mayor al registro esperado
     *
     * @param idVehiculo Identificador Vehiculo
     * @param fecha Date Fecha de consulta
     * @param hora String Hora de consulta
     * @param idGopUF Identificador Unidad Funcional
     * @return PrgTc o null
     */
    PrgTc getPrgTcByFechaAndTimeOriginAndStopPointIsDepotEntry(Integer idVehiculo, Date fecha, String hora, int idGopUF);

    /**
     * Permite consultar la proxima salida a patio, suministrando la fecha y
     * hora, esta debe ser mayor al registro esperado
     *
     * @param idVehiculo Identificador Vehiculo
     * @param fecha Date Fecha de consulta
     * @param hora String Hora de consulta
     * @param idGopUF Identificador Unidad Funcional
     * @return PrgTc o null
     */
    PrgTc getPrgTcByFechaAndTimeOriginAndStopPointIsDepotExit(Integer idVehiculo, Date fecha, String hora, int idGopUF);

    /**
     * Permite consultar la proxima tarea del operador donde id_task_type IS NOT
     * NULL
     *
     * @param idEmpleado Identificador Empleado
     * @param fecha Date Fecha de consulta
     * @param hora String hora 30:00:00
     * @param idGopUF Identificador Unidad Funcional
     * @return PrgTc o null
     */
    PrgTc getPrgTcByIdEmpleadoTmAndFechaByIdGopUF(Integer idEmpleado, Date fecha, String hora, int idGopUF);

    /**
     * Consultar los servicios de un empleado para el dia suministrado
     *
     * @param idEmpleado Identificador Empleado
     * @param fecha Date Fecha de consulta
     * @param idGopUF Identificador Unidad Funcional
     * @return List<PrgTc>
     */
    List<PrgTc> getPrgTcByIdEmpleadoAndFechaAndIdGopUF(Integer idEmpleado, Date fecha, int idGopUF);

    List<ServiciosPorSalir> findServiciosForGestionTaller();

    /**
     * Devuelve suma total de servicios eliminados por responsable
     *
     * @param fecha
     * @param idGopUnidadFunc
     * @param fechaUltimoCierre
     * @return
     */
    List<SumEliminadoResponsableDTO> findReportSumEliminadoResponsable(Date fecha,
            int idGopUnidadFunc, Date fechaUltimoCierre);

    /**
     * Devuelve sumatoria total de servicios sin operador.
     *
     * @param fecha
     * @param idGopUnidadFunc
     * @return
     */
    Long findReportSumServiciosSinOp(Date fecha, int idGopUnidadFunc);

    /**
     * Devuelve sumatoria total de servicios sin operador.
     *
     * @param fecha
     * @param idGopUnidadFunc
     * @return
     */
    Long findReportSumServiciosSinVehiculo(Date fecha, int idGopUnidadFunc);

    /**
     * Devuelve total de disponibles programados para la fecha, por unidad
     * funcional.
     *
     * @param fecha
     * @param idGopUnidadFuncional
     * @return
     */
    Long totalDisponibleByFechaAndUf(Date fecha, int idGopUnidadFuncional);

    List<PrgTc> tareasDispoByIdEmpeladoAndFechaAndUnidadFunc(Integer idEmpleado, Date fecha, int idGopUnidadFuncional);

    void cambioUnoAUnoServicosBySercon(PrgSercon sercon1, PrgSercon sercon2);

    PrgTc currentServiceByCodeVehicle(String codeVehicle);

    PrgTc firtServiceByIdEmpleado(Integer idEmpleado, Date fecha);

    long buildShifts(Date desde, Date hasta, int idGopUnidadFuncional);

    long spDesasignarOp(Date desde, Date hasta, int idGopUnidadFuncional, String username);

    List<PrgTc> findServiciosByFechaAndIdEmpleado(Date d, Integer idEmpleado);

    /**
     * Permite consultar los servicios sin vehiculos que no esten eliminados por
     * unidad funcional
     *
     * @param d Date
     * @param idGopUF
     * @return
     */
    List<PrgTc> findServiciosSinAsignarByFechaAndGopUf(Date d, int idGopUF);

    /**
     * Devuelve el total de servbus en donde esta montado el vehículo.
     *
     * @param fecha
     * @param idVehiculo
     * @param idGopUnidadFunc
     * @return
     */
    Long totalServbusVehiculo(Date fecha, int idVehiculo, int idGopUnidadFunc);

    List<PrgTc> serviciosPrgTcPendientesPorOperador(int idEmpleado, Date fecha);

    List<PrgTc> serviciosPendientesPorVehiculo(String cod, Date fecha, int idGopUnidadFuncional);

    List<ServbusPlanificadoDTO> findBusesPlanificadosByRangeDate(Date desde, Date hasta, int idGopUnidadFunc);
    
    List<ServbusPlanificadoDetalleDTO> findBusesPlanificadosDetalleByRangeDate(Date desde, Date hasta, int idGopUnidadFunc);
    
    List<KmPrgEjecDTO> findKmPrgEjecDTOByRangeDate(Date desde, Date hasta, int idGopUnidadFunc);

    List<UltimoServicioDTO> findUltimosServiciosPorServbus(Date fecha, int idGopUnidadFunc);

    List<PrimerServicioDTO> findPrimerosServiciosPorServbus(Date fecha, int idGopUnidadFunc);

    List<ServbusTipoTablaDTO> findServbusTipoTabla(Date fecha, int idGopUnidadFunc);

    List<InsumoProgramacionDTO> findInsumoProgramacion(Date desde, Date hasta, int idGopUnidadFunc);

    List<PorcentajeDisponibilidadFlotaDTO> porcentajeDispoFlota(Date desde, Date hasta, String horaCorte, int idGopUnidadFunc);

    List<TP28UltimoServicioDTO> findTP28UltimosServicios(Date fecha, int idGopUnidadFunc);

    public int updateServbusDesasignado(Date fecha, int idGopUnidadFunc, String servbus, int idVehiculo, String timeOrigin, String username);

    Long findVehiculoLibreAsignar(int idGopUnidadFunc, String servbus, int idVehiculo, Date fecha, String timeOrigin);
    
    Long findVehiculoLibreSinVACAsignar(int idGopUnidadFunc, String servbus, int idVehiculo, Date fecha, String timeOrigin);

    List<PrgTc> obtenerTareasPorFechasYOperador(Date fechaDesde, Date fechaHasta, int idGopUnidadFuncional, int idEmpleado);
    
    PrgTc findPrgTcEntrada(int idVehiculo, String date, String hour);
    
    PrgTc findPrgTcSalida(int idVehiculo, String date, String hour);
    
    PrgTc findPrgTcEntradaId(int id, String time);
    
    List<PrgTc> findPrimerasTareasServbus(Date fechaDesde, Date fechaHasta);
    
    List<ReporteSemanaActualDTO> findReporteSemanaActual(Date desde, Date hasta, int idGopUnidadFunc);
    
    List<PrgTc> findByEmpleadoByTarea(Date fecha, String horaInicial, String horaFin, String descripcion);
}
