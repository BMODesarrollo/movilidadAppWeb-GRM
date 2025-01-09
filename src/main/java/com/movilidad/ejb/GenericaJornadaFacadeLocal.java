/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.aja.jornada.model.GenericaJornadaLiqUtil;
import com.movilidad.model.GenericaJornada;
import com.movilidad.model.GenericaJornadaInicial;
import com.movilidad.util.beans.ConsolidadoDetalladoCAM;
import com.movilidad.util.beans.ConsolidadoLiquidacion;
import com.movilidad.util.beans.ConsolidadoLiquidacionDetallado;
import com.movilidad.util.beans.ConsolidadoLiquidacionCAM;
import com.movilidad.util.beans.ConsolidadoNominaDetallado;
import com.movilidad.util.beans.LiquidacionSercon;
import com.movilidad.util.beans.PrenominaCAM;
import com.movilidad.util.beans.ReporteHorasCM;
import com.movilidad.util.beans.ReporteInterventoria;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface GenericaJornadaFacadeLocal {

    void create(GenericaJornada genericaJornada);

    void edit(GenericaJornada genericaJornada);

    void remove(GenericaJornada genericaJornada);

    GenericaJornada find(Object id);

    GenericaJornada validarEmplSinJornada(int idEmpleado, Date fecha);

    long validarPeriodoLiquidadoEmpleado(Date fromDate, Date toDate, int idEmpleado);

    void borradoMasivo(int idEmpleado, int idMotivo, Date desde, Date hasta, String observacion, String username, int valorBorrado);

    List<GenericaJornada> findAll();

    List<ReporteInterventoria> obtenerDatosInfoInterventoriaConArea(Date desde, Date hasta, Integer[] areas);

    List<ReporteInterventoria> obtenerDatosInfoInterventoriaSinArea(Date desde, Date hasta);

    List<ReporteHorasCM> obtenerDatosNominaCMConArea(Date desde, Date hasta, Integer[] areas);

    List<ReporteHorasCM> obtenerDatosNominaCMSinArea(Date desde, Date hasta);

    List<ConsolidadoLiquidacion> obtenerDatosConsolidado(Date desde, Date hasta, Integer idArea);
    
    List<ConsolidadoLiquidacion> obtenerDatosCargados(Date desde, Date hasta, Integer idArea);

    List<ConsolidadoLiquidacionDetallado> obtenerDatosConsolidadoDetallado(Date desde, Date hasta, Integer idArea);
    
    List<ConsolidadoLiquidacionDetallado> obtenerDatosCargadosDetallado(Date desde, Date hasta, Integer idArea);

    List<ConsolidadoNominaDetallado> obtenerDatosDetalladoNomina(Date desde, Date hasta, Integer idArea);

    List<ConsolidadoLiquidacionCAM> obtenerDatosConsolidadoCAM(Date desde, Date hasta);

    List<ConsolidadoDetalladoCAM> obtenerDatosConsolidadoDetalladoCAM(Date desde, Date hasta);

    List<PrenominaCAM> obtenerDatosInformePrenominaCAMPorArea(Date desde, Date hasta, Integer[] areas);

    List<GenericaJornada> getByDate(Date desde, Date hasta, int idArea);

    List<GenericaJornada> findRange(int[] range);

    int count();

    public void update(LiquidacionSercon l);

    void nominaBorrada(int idGenericaJornada, int valor, String username);

    void liquidarPorRangoFecha(Date fromDate, Date toDate, String userName, int idArea);

    public int liquidarPorId(int id, String userName);

    void cambioEmpleado(GenericaJornada prgSercon1, GenericaJornada prgSercon2);

    GenericaJornada getPrgSerconByCodigoTM(String codigoTM, Date fecha);

    GenericaJornada getJornadaByIdentificacionAndArea(String codigoTM, Date fecha, Integer area);

    List<GenericaJornada> getJornadasByFechaAndArea(Date fecha, int idArea);

    List<GenericaJornada> getByDateAllArea(Date desde, Date hasta);

    int descansoEnSemana(Date lunes, Date domingo, int idEmpleado, int idArea);

    List<GenericaJornada> getJornadasByDateAndEmpleado(Date lunes, Date domingo, int idEmpleado);

    List<GenericaJornada> findByDateAndLiquidado(Date desde, Date hasta, Integer idArea, Integer liquidado, int id); //

    int eliminarJornada(Integer idArea, Date desde, Date hasta, int id);
    
    int eliminarJornadaInicial(Integer idArea, Date desde, Date hasta, int id);

    List<GenericaJornada> getJornadasPorRangoFechasIdAreaIdEmpleado(Date desde, Date hasta, Integer idArea, Integer idEmpleado);

    List<GenericaJornada> getUltimaJornadaMesByArea(Date desde, Date hasta, Integer idArea);

    List<Date> validarDiasLiquidadosByFechasAndIdArea(Date desde, Date hasta, int idParamArea);

    List<Date> validarDiasLiquidadosByFechasAndIdAreaIndividual(Date desde, Date hasta, int idParamArea, int idEmpleado);

    void updatePrgSerconFromList(List<GenericaJornadaLiqUtil> sercones, int opc);

    public GenericaJornada validarEmplSinJornadaByParamAreaFechaEmpleado(int idEmpleado, Date fecha, int idParamArea);
    
    List<GenericaJornada> findBiometricoNovedades(int idArea, Date desde, Date hasta, int idGopUnidadFuncional, int gestiona);
    
    List<GenericaJornada> findBiometricoNovedadesGestionadas(int idArea, Date desde, Date hasta, int idGopUnidadFuncional);
    
    List<GenericaJornada> findBiometricoNovedadesPorGestionar(int idArea, int idGopUnidadFuncional);
    
    List<GenericaJornada> findBiometricoOK(int idArea, Date desde, Date hasta, int idGopUnidadFuncional);
    
    List<GenericaJornada> findHistoricoMarcaciones(int idArea, Date desde, Date hasta, int idGopUnidadFuncional);
    
    List<GenericaJornadaInicial> findJornadasCargadas(int idArea, Date desde, Date hasta);
}
