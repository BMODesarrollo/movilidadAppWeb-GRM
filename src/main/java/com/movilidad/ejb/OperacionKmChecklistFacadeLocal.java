/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.OperacionKmChecklist;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Soluciones IT
 */
@Local
public interface OperacionKmChecklistFacadeLocal {

    void create(OperacionKmChecklist operacionKmChecklist);

    void edit(OperacionKmChecklist operacionKmChecklist);

    void remove(OperacionKmChecklist operacionKmChecklist);

    OperacionKmChecklist find(Object id);

    OperacionKmChecklist findChecklistByIdVehiculo(int idVehiculo, Date fecha, int id, int operador);

    OperacionKmChecklist findChecklistIgualByIdVehiculo(int idVehiculo, Date fecha, int id);

    List<OperacionKmChecklist> findAll();

    List<OperacionKmChecklist> findRange(int[] range);

    int count();

}
