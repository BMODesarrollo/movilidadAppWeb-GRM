/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.TblHistoricoIquidacionEmpleado;
import com.movilidad.util.beans.TblHistoricoLiquidacionEmpleadoDTO;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface TblHistoricoIquidacionEmpleadoFacadeLocal {

    void create(TblHistoricoIquidacionEmpleado tblHistoricoIquidacionEmpleado);

    void edit(TblHistoricoIquidacionEmpleado tblHistoricoIquidacionEmpleado);

    void remove(TblHistoricoIquidacionEmpleado tblHistoricoIquidacionEmpleado);

    TblHistoricoIquidacionEmpleado find(Object id);

    List<TblHistoricoIquidacionEmpleado> findAll();

    List<TblHistoricoIquidacionEmpleado> findRange(int[] range);

    List<TblHistoricoIquidacionEmpleado> findAllByFechaMes(Date desde, Date hasta);

    int eliminarDatos(Date desde, Date hasta, String userBorra);

    int count();

    List<TblHistoricoLiquidacionEmpleadoDTO> findAllByFechaMesResumenPorGrupo(Date desde, Date hasta);

    List<TblHistoricoIquidacionEmpleado> findByRangoFechas(Date desde, Date hasta);

}
