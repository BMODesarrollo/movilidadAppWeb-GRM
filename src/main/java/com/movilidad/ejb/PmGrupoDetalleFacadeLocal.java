/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmGrupoDetalle;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Soluciones IT
 */
@Local
public interface PmGrupoDetalleFacadeLocal {

    void create(PmGrupoDetalle pmGrupoDetalle);

    void edit(PmGrupoDetalle pmGrupoDetalle);

    void remove(PmGrupoDetalle pmGrupoDetalle);

    PmGrupoDetalle find(Object id);

    PmGrupoDetalle findByIdEmpleadoAndIdGrupoPm(int idGrupo, int idEmpleado);

    List<PmGrupoDetalle> findAll();

    List<PmGrupoDetalle> findRange(int[] range);

    int count();

    List<PmGrupoDetalle> findByIdPmGrupo(int idPmGrupo);
    
    List<PmGrupoDetalle> findByActivosGrupo();

}
