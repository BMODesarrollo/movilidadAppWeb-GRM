/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MyAppConfirmDepotExit;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface MyAppConfirmDepotExitFacadeLocal {

    void create(MyAppConfirmDepotExit myAppConfirmDepotExit);

    void edit(MyAppConfirmDepotExit myAppConfirmDepotExit);

    void remove(MyAppConfirmDepotExit myAppConfirmDepotExit);

    MyAppConfirmDepotExit find(Object id);

    List<MyAppConfirmDepotExit> findAll();
    
    List<MyAppConfirmDepotExit> findByDateRange(Date fechaDesde, Date fechaHasta, int idGopUf);

    List<MyAppConfirmDepotExit> findRange(int[] range);

    int count();
    
    MyAppConfirmDepotExit findByIdPrgTc(Integer idPrgTc);
    
    MyAppConfirmDepotExit findServiceConfirmate(Integer idPrgTc);
    
    List<MyAppConfirmDepotExit> findTypeServiceConfirmateByVehiculoAndFecha(String codigoVehiculo, Integer idGopUf, Date fecha);
    
}
