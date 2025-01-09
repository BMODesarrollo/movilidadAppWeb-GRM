/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoComponenteGrupo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USUARIO
 */
@Local
public interface VehiculoComponenteGrupoFacadeLocal {

    void create(VehiculoComponenteGrupo vehiculoComponenteGrupo);

    void edit(VehiculoComponenteGrupo vehiculoComponenteGrupo);

    void remove(VehiculoComponenteGrupo vehiculoComponenteGrupo);

    VehiculoComponenteGrupo find(Object id);

    List<VehiculoComponenteGrupo> findAll();

    List<VehiculoComponenteGrupo> findRange(int[] range);

    int count();
    
}
