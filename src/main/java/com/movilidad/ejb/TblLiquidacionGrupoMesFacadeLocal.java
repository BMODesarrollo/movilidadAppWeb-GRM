/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.TblLiquidacionGrupoMes;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface TblLiquidacionGrupoMesFacadeLocal {

    void create(TblLiquidacionGrupoMes tblLiquidacionGrupoMes);

    void edit(TblLiquidacionGrupoMes tblLiquidacionGrupoMes);

    void remove(TblLiquidacionGrupoMes tblLiquidacionGrupoMes);

    TblLiquidacionGrupoMes find(Object id);

    List<TblLiquidacionGrupoMes> findAll();

    List<TblLiquidacionGrupoMes> findRange(int[] range);

    int count();

    List<TblLiquidacionGrupoMes> findAllByRangoFechas(Date desde, Date hasta);

    int eliminarDatos(Date desde, Date hasta, String userBorra);
}
