package com.movilidad.ejb;

import com.movilidad.model.LavadoNoConforme;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Omar Beltrán
 */
@Local
public interface LavadoNoConformeFacadeLocal {

    void create(LavadoNoConforme lavadoNoConforme);

    void edit(LavadoNoConforme lavadoNoConforme);

    void remove(LavadoNoConforme lavadoNoConforme);

    LavadoNoConforme find(Object id);

    List<LavadoNoConforme> findAll();

    List<LavadoNoConforme> findRange(int[] range);
    
    List<LavadoNoConforme> findByDateRange(Date desde, Date hasta);

    int count();
    
}
