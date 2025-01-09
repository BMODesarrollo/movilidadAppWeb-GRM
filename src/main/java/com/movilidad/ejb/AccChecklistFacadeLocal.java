/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccChecklist;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface AccChecklistFacadeLocal {

    void create(AccChecklist accChecklist);

    void edit(AccChecklist accChecklist);

    void remove(AccChecklist accChecklist);

    AccChecklist find(Object id);

    AccChecklist verificarRepetido(int detalle, int tipoDocumento);

    List<AccChecklist> findAll();

    List<AccChecklist> findRange(int[] range);

    int count();

}
