/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MyAppConfirmDepotEntry;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface MyAppConfirmDepotEntryFacadeLocal {

    void create(MyAppConfirmDepotEntry myAppConfirmDepotEntry);

    void edit(MyAppConfirmDepotEntry myAppConfirmDepotEntry);

    void remove(MyAppConfirmDepotEntry myAppConfirmDepotEntry);

    MyAppConfirmDepotEntry find(Object id);

    List<MyAppConfirmDepotEntry> findAll();

    List<MyAppConfirmDepotEntry> findByDateRange(Date fechaDesde, Date fechaHasta, int idGopUf);

    List<MyAppConfirmDepotEntry> findRange(int[] range);

    int count();
    
    MyAppConfirmDepotEntry findByIdPrgTc(Integer idPrgTc);
    
    List<MyAppConfirmDepotEntry> findTypeServiceConfirmateByVehiculoAndFecha(String codigoVehiculo, Integer idGopUf, Date fecha);
    
    MyAppConfirmDepotEntry findServiceConfirmate(Integer idPrgTc);

}
