/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgRoute;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PrgRouteFacadeLocal {

    void create(PrgRoute prgRoute);

    void edit(PrgRoute prgRoute);

    void remove(PrgRoute prgRoute);

    PrgRoute find(Object id);

    PrgRoute find(String field, String value, int idGopUnidadFuncional);

    List<PrgRoute> findAll();

    List<PrgRoute> getRutas();

    List<PrgRoute> findActivas();

    List<PrgRoute> findByUnidadFuncional(int idGopUnidadFuncional);

    List<PrgRoute> findActivasByUnidadFuncional(int idGopUnidadFuncional);

    List<PrgRoute> findRange(int[] range);

    int count();

    Long count(int idGopUnidadFuncional);

    int desactivarRoute(String codigo);

}
