/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;


import com.movilidad.model.PqrFranjaHoraria;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlos Ballestas
 */
@Local
public interface PqrFranjaHorariaFacadeLocal {

    void create(PqrFranjaHoraria pqrClasificacion);

    void edit(PqrFranjaHoraria pqrClasificacion);

    void remove(PqrFranjaHoraria pqrClasificacion);

    PqrFranjaHoraria find(Object id);
    

    List<PqrFranjaHoraria> findAll();
    
    List<PqrFranjaHoraria> findAllByEstadoReg();

    List<PqrFranjaHoraria> findRange(int[] range);

    int count();
    
}
