/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DanoFlotaComponenteGrupo;
import com.movilidad.model.DanoFlotaParamSeveridad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Julián Arévalo
 */
@Local
public interface DanoFlotaComponenteGrupoFacadeLocal {

    void create(DanoFlotaComponenteGrupo danoFlotaComponenteGrupo);

    void edit(DanoFlotaComponenteGrupo danoFlotaComponenteGrupo);

    void remove(DanoFlotaComponenteGrupo danoFlotaComponenteGrupo);

    DanoFlotaComponenteGrupo find(Object id);

    List<DanoFlotaComponenteGrupo> findAll();

    List<DanoFlotaComponenteGrupo> findRange(int[] range);

    int count();

    List<DanoFlotaComponenteGrupo> getAllActivo();  
    
}
