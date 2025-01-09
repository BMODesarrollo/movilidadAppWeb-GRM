/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Empleado;
import com.movilidad.model.PmGrupo;
import com.movilidad.util.beans.InformeInterventoria;
import com.movilidad.util.beans.InformeLocalidadEmpleado;
import com.movilidad.util.beans.InformeOperadores;
import com.movilidad.util.beans.PlantaObz;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Soluciones IT
 */
@Local
public interface EmpleadoFacadeLocal {

    void create(Empleado empleado);

    void edit(Empleado empleado);

    void remove(Empleado empleado);

    Empleado find(Object id);

    List<Empleado> findAll();

    List<Empleado> findAllByUnidadFuncacional(int idGopUnidadFuncional);
    
    List<Empleado> findAllConRetiradosByUnidadFuncional(int idGopUnidadFuncional);

    List<Empleado> findEmpleadosOperadores(int idGopUnidadFuncional);

    List<Empleado> findEmpleadosSinGrupo(int id, int id2);

    List<Empleado> findEmpleadosSinGrupoGenerica(int idCargo, int idArea);

    List<Empleado> findRange(int[] range);

    int count();

    Empleado findCampo(String campo, String value, int id);

    Empleado guardarEmpleado(Empleado e);

    Empleado getEmpleado(int codigo);

    Empleado getEmpleadoByUsername(String codigo);
    
    int getEmpleadoByFullname(String codigo);

    PmGrupo findbyCodigoOperador(int codigo);

    Empleado findbyCodigoTmAndIdGopUnidadFuncActivo(int codigo, int idGopUnidadFunc);

    Empleado getEmpleadoCodigoTM(int i);
    
    Empleado getEmpleadoActivoCodigoTM(int i);

    Empleado getEmpleadoCodigoTmAndUnidadFuncional(int i, int idGopUnidadFuncional);

    Empleado getEmpleadoCodigoInterno(int i);

    List<InformeInterventoria> getInformeInterventoria(Date fecha_ini, Date fecha_fin, int idGopUnidadFuncional);

    List<InformeOperadores> getInformeOperadoresEnCargo(Date fecha_ini, Date fecha_fin, int idGopUnidadFuncional);

    List<Empleado> getColaboradores(Integer idGopUnidadFuncional, boolean flag);

    int certificar(int idEmpleado, int valor, String userName);

    Long obtenerCantidadOperadoresByUfAndCargo(Integer idGopUnidadFuncional, Integer idEmpleadoTipoCargo, boolean flag);

    List<Empleado> getEmpledosByIdArea(int idArea, int idGopUnidadFuncional);

    Empleado findByIdentificacion(String cIdentificacion);

    List<Empleado> getEmpledosByIdCargoActivos(int idCargo, int op);

    List<Empleado> findAllEmpleadosActivos(int idGopUF);

    List<Empleado> findAllEmpleadosActivosByUnidadFunc(int idGopUnidadFuncional);

    Empleado findByCodigoTM(int codigoTM);

    int actualizarEstadoEmpleado(int idEmplado, int idEstadoEmpleado);

    List<Empleado> findAllEmpleadosByCargos(String idCargos);

    /**
     *
     * @param idCargos String con id de cargos separador por coma.
     * @param idGopUnidadFuncional empresa o unidad funcional a la que pertenece
     * un empleado.
     * @return
     */
    List<Empleado> findEmpleadosByIdGopUnidadFuncional(String idCargos, int idGopUnidadFuncional);

    List<InformeLocalidadEmpleado> findInformeLocalidadEmpleado(Integer idEmpleadoCargo, int idGopUF);

    List<Empleado> findEmpleadosSinGrupoByUnidadFuncional(int id, int id2, int idGopUnidadFuncional);
    
    List<PlantaObz> obtenerInformePlantaObz(int idGopUnidadFuncional, Date desde, Date hasta);
    
    List<Empleado> findEmpleadosRelacionadosPd(int idMaestro);
    
    List<Empleado> findEmpleadosCapacitadores();

}
