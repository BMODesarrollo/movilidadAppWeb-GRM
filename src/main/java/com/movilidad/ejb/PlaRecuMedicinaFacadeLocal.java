/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuMedicina;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author luis.lancheros
 */
@Local
public interface PlaRecuMedicinaFacadeLocal {
    
    void create(PlaRecuMedicina medicinaLaboral);

    void edit(PlaRecuMedicina medicinaLaboral);

    void remove(PlaRecuMedicina medicinaLaboral);

    PlaRecuMedicina find(Object id);

    int count();
    
    List<PlaRecuMedicina> findAll();
    
    List<PlaRecuMedicina> findAllByGropUnidadFun(int idGopUnidadFuncional);
    
    String findRestrictionByIdEmployee(int idEmpl);
    
    PlaRecuMedicina findMedicina(Date fechaIni, Date fechaFin, Integer idEmpleado);
    
    List<PlaRecuMedicina> findAllByFechaRange(Date desde, Date hasta);
    
}
