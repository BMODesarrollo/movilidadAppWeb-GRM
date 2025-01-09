/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgPattern;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author luis
 */
@Local
public interface PrgPatternFacadeLocal {

    void create(PrgPattern prgPattern);

    void edit(PrgPattern prgPattern);

    void remove(PrgPattern prgPattern);

    PrgPattern find(Object id);

    List<PrgPattern> findAll();

    List<PrgPattern> findAllOrderedByIdRoute(int idRoute);

    List<PrgPattern> findAllByidGopUnidadFuncOrdered(int idGopUnidadFuncional);

    List<PrgPattern> findRange(int[] range);

    int count();

}
