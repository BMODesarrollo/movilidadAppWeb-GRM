/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Accidente;
import com.movilidad.util.beans.BitacoraAccidentalidad;
import com.movilidad.util.beans.ReporteLucroCesante;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author HP
 */
@Local
public interface AccidenteFacadeLocal {

    void create(Accidente accidente);

    void edit(Accidente accidente);

    void remove(Accidente accidente);

    Accidente find(Object id);

    Accidente findByNovedad(int id);

    List<Accidente> findAll();

    List<Accidente> findRange(int[] range);

//    List<Accidente> findByArguments(int i_idVeh, int i_idEmp, int i_idNovDet, Date fechaIni, Date fechaFin, int idGopUF);
    List<Accidente> findByArguments(int i_idVeh, int i_idEmp, int i_idNovDet, Date fechaIni, Date fechaFin, int idGopUnidadFuncional);

    int count();

    List<Accidente> findAccidenteForInformeMaster(int i_idVeh, int i_idEmp, int i_idNovDet, Date fechaIni, Date fechaFin, int idGopUF);

    List<Accidente> findAccidenteForInformeOperador(int i_idVeh, int i_idEmp, int i_idNovDet, Date fechaIni, Date fechaFin, int idGopUF);

    List<Accidente> findAccidenteForInformeMasterEdit(int i_idVeh, int i_idEmp, int i_idNovDet, Date fechaIni, Date fechaFin, int idGopUF);

    List<Accidente> findAllByIdEmpleadoAndDates(Integer idAcc, Integer idEmpleado, String desde, String hasta, int idGopUF);
    
    List<Accidente> findAllByIdVehiculoAndDate(Integer idVehiculo, Date fecha);

    /**
     * Devuelve total de accidente en atención par ala fecha y unidad funcional
     *
     * @param fecha
     * @param idGopUnidadFunc
     * @param fechaUltimoCierre
     * @return
     */
    Long findTotalAccidentesEnAtencion(Date fecha, int idGopUnidadFunc, Date fechaUltimoCierre);

    /**
     * Permite valdiar si ya existe un accidente para el vehiculo, empleado y
     * tipo de novedad en un lapzo de 1 hora antes
     *
     * @param idEmpleado
     * @param idVehiculo
     * @param idNovDet
     * @param idGopUF
     * @return
     */
    Long existeAccidenteAbiertoByIdEmpleadoByIdVehiculoAndIdNovDetAndIdGopUF(Integer idEmpleado, Integer idVehiculo, Integer idNovDet, Integer idGopUF);

    /**
     * Permite consultar los accidentes que no han sido asistidos por el
     * recomoto
     *
     * @param idVehiculo
     * @param idEmpleado
     * @param idNovDet
     * @param fechaIni
     * @param fechaFin
     * @param idGopUF
     * @return
     */
    List<Accidente> findAccidenteAbiertosRecomoto(Integer idVehiculo, Integer idEmpleado, Integer idNovDet, Date fechaIni, Date fechaFin, int idGopUF);

    /**
     * Permite obtener los datos para el reporte de lucro cesante
     *
     * @param desde
     * @param hasta
     * @param idGopUnidadFuncional
     * @return
     */
    List<ReporteLucroCesante> findByRangoFechasAndUf(Date desde, Date hasta, int idGopUnidadFuncional);

    /**
     * Permite obtener los datos para la bitácora de accidentalidad
     *
     * @param desde
     * @param hasta
     * @param idGopUnidadFuncional
     * @return
     */
    List<BitacoraAccidentalidad> obtenerDatosBitacoraAcc(Date desde, Date hasta, int idGopUnidadFuncional);
}
