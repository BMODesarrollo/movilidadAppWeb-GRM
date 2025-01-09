/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.aja.jornada.model.PrgSerconLiqUtil;
import com.movilidad.dto.EmpleadoDescansoDTO;
import com.movilidad.dto.EntradaSalidaJornadaDTO;
import com.movilidad.dto.HoraPrgEjecDTO;
import com.movilidad.dto.PrgSerconDTO;
import com.movilidad.model.PrgSercon;
import com.movilidad.model.PrgSerconInicial;
import com.movilidad.util.beans.ConsolidadoLiquidacion;
import com.movilidad.util.beans.ConsolidadoLiquidacionDetallado;
import com.movilidad.util.beans.ConsolidadoLiquidacionGMO;
import com.movilidad.util.beans.ConsolidadoNominaDetallado;
import com.movilidad.util.beans.InformeAmplitud;
import com.movilidad.util.beans.LiquidacionSercon;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface PrgSerconFacadeLocal {

    void create(PrgSercon prgSercon);

    void edit(PrgSercon prgSercon);

    void remove(PrgSercon prgSercon);

    PrgSercon find(Object id);

    List<PrgSercon> findAll();

    List<PrgSercon> findRange(int[] range);

    int count();

    public List<PrgSercon> findByFechaAndIdGopUnidadFunc(Date date, Integer idGopUnidadFuncional);

    public void update(LiquidacionSercon l);

    public void updateVistoInPrgSercon(int idPrgSercon);

    public void updateSercon(PrgSercon s);

    public void updateSerconFromList(List<PrgSercon> sercones, int opc);

    public long countByFechas(Date fromDate, Date toDate, int idGopUnidadFuncional);

    public void nominaBorrada(int idPrgSercon, int valor, String username);

    public void borradoMasivo(int idEmpleado, int idMotivo, Date desde, Date hasta, String observacion, String username, int valorBorrado);

    public List<PrgSercon> getPrgSerconByDateAndUnidadFunc(Date desde, Date hasta, int idGopUnidadFuncional);

    public List<InformeAmplitud> getAmplitudes(Date desde, Date hasta, String horas, int idGopUnidadFuncional);

    public void liquidarPorRangoFecha(Date fromDate, Date toDate, String userName, int idGopUnidadFuncional);

    public long validarPeriodoLiquidado(Date fromDate, Date toDate);

    public long validarPeriodoLiquidadoEmpleado(Date fromDate, Date toDate, int idEmpleado);

    public int liquidarPorId(int id, String userName);

    public PrgSercon validarEmplSinJornadaByUnindadFuncional(int idEmpleado, Date fecha, int idGopUnidadFuncional);

    public PrgSercon getPrgSerconByCodigoTMAndUnidadFuncional(String codigoTM, Date fecha, int idGopUnidadFuncional);

    public void cambioEmpleado(PrgSercon prgSercon1, PrgSercon prgSercon2);

    int removeByDate(Date d);

    int cambiarNomina(Integer idEmpleado, Integer idPrgSercon);

    public List<Date> validarDiasLiquidadosByFechasAndUnidadFuncional(Date desde, Date hasta, int idGopUnidadFuncional);

    public List<Date> validarDiasLiquidadosByFechasAndUnidadFuncionalAndOperador(Date desde, Date hasta, int idGopUnidadFuncional, int idEmpleado);

    public List<PrgSercon> obtenerSerconesPorRangoFechasYEmpleado(Date desde, Date hasta, int idGopUnidadFuncional, int idEmpleado);

    public List<PrgSercon> getByDate(Date desde, Date hasta);

    public List<PrgSercon> getByDateSinLiquidar(Date desde, Date hasta, int idGopUnidadFuncional);

    public List<PrgSerconDTO> findSerconSinPresentacionByUnidadFunc(Date fecha, int tiempoOrgura, int idGopUnidadFuncional);

    public List<PrgSercon> findAllSerconSinPresentacionByUnidadFuncional(Date fecha, int tiempoOrgura, int IdGopUnidadFuncional, int idNovedadTipo);

    public List<PrgSercon> findAllRangoFechasWithConfirmation(Date desde, Date hasta, int idGopUf);

    List<ConsolidadoLiquidacion> obtenerDatosConsolidado(Date desde, Date hasta, int IdGopUnidadFuncional);
    
    List<ConsolidadoLiquidacion> obtenerDatosCargados(Date desde, Date hasta, int IdGopUnidadFuncional);

    List<ConsolidadoLiquidacionDetallado> obtenerDatosConsolidadoDetallado(Date desde, Date hasta, int IdGopUnidadFuncional);
    
    List<ConsolidadoLiquidacionDetallado> obtenerDatosCargadosDetallado(Date desde, Date hasta, int IdGopUnidadFuncional);

    List<ConsolidadoNominaDetallado> obtenerDatosDetalladoNomina(Date desde, Date hasta, int idGopUnidadFuncional);

    /**
     * Permite obtener el PrgSercon por id Empleado and Fecha
     *
     * @param idEmpleado Identificador Empleado
     * @param fecha Fecha de consulta
     * @return PrgSercon
     */
    PrgSercon getByIdEmpleadoAndFecha(Integer idEmpleado, Date fecha);

    Date totalHorasExtrasByRangoFechaAndIdEmpleado(Integer idEmpleado, Date fecha, Date desde, Date hasta);

    public void ajustarJornadaCero(Integer idEmpleado, Integer idMotivo, Date desde, Date hasta, String observacion, String username);

    public void eliminarJornada(Integer idEmpleado, Date desde, Date hasta, String username);

    int updatePrgSerconMySerconConfirm(Integer idPrgSercon, Integer idMySerconConfirm, String username);

    List<HoraPrgEjecDTO> getHoraPrgEjec(Date desde, Date hasta, int idGopUnidadFuncional);

    List<ConsolidadoLiquidacionGMO> obtenerDatosConsolidadoQuincenal(Date desde, Date hasta,
            int idGopUnidadFuncional);

    List<ConsolidadoLiquidacionGMO> obtenerDatosConsolidadoDetalle(Date desde, Date hasta,
            int idGopUnidadFuncional);

    List<EntradaSalidaJornadaDTO> findEntradaSalidasJornadas(Date desde, Date hasta,
            int idGopUnidadFuncional);

    List<PrgSercon> findJornadasProductivasByFecha(Date fecha, int idGopUnidadFuncional);

    List<EmpleadoDescansoDTO> findDescansosByRangeDate(Date desde, Date hasta, int idGopUnidadFuncional);

    void updatePrgSerconFromList(List<PrgSerconLiqUtil> sercones, int opc);

    void updatePrgSerconFromListWithoutProductionTime(List<PrgSerconLiqUtil> sercones);

    public List<PrgSercon> obtenerRegistrosByFechasAndUnidadFuncional(Date desde, Date hasta, int idGopUnidadFuncional, int idEmpleado);

    List<Date> obtenerDiasLiquidadosByFechasAndUnidadFuncional(Date desde, Date hasta, int idGopUnidadFuncional);

    void eliminarJornadaAusentismo(Integer idEmpleado, Date desde, Date hasta, String username, Integer idPrgSerconMotivo, String observacion);
    
    List<PrgSerconInicial> findJornadasCargadas(int idGopUnidadFuncional, Date desde, Date hasta);
    
    List<PrgSercon> findByDateAndLiquidado(Date desde, Date hasta, int idGopUnidadFuncional, Integer liquidado, int id);
    
    PrgSercon findSerconProgramado(Date fecha, Integer id_empleado, String hiniPrgTurno1, String hfinPrgTurno1, 
            String hiniPrgTurno2, String hfinPrgTurno2, String hiniPrgTurno3, String hfinPrgTurno3);
}
