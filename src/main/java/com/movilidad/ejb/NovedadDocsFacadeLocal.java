/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadDocs;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface NovedadDocsFacadeLocal {

    void create(NovedadDocs novedadDocs);

    void edit(NovedadDocs novedadDocs);

    void remove(NovedadDocs novedadDocs);

    NovedadDocs find(Object id);

    List<NovedadDocs> findAll();

    List<NovedadDocs> findRange(int[] range);

    int count();

    List<NovedadDocs> findAllByIdNovedad(int idNovedad);
}
