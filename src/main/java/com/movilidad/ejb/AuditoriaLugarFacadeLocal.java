/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaLugar;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author solucionesit
 */
@Local
public interface AuditoriaLugarFacadeLocal {

    void create(AuditoriaLugar auditoriaLugar);

    void edit(AuditoriaLugar auditoriaLugar);

    void remove(AuditoriaLugar auditoriaLugar);

    AuditoriaLugar find(Object id);

    AuditoriaLugar findByIdAuditoriaLugar(int idAuditoriaLugar);

    List<AuditoriaLugar> findAll();

    List<AuditoriaLugar> findRange(int[] range);

    int count();

    List<AuditoriaLugar> findByArea(int idArea);
}
