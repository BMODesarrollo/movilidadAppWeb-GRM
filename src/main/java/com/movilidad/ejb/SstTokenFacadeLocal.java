/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstToken;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cesar
 */
@Local
public interface SstTokenFacadeLocal {

    void create(SstToken sstToken);

    void edit(SstToken sstToken);

    void remove(SstToken sstToken);

    SstToken find(Object id);

    List<SstToken> findAll();

    List<SstToken> findRange(int[] range);

    int count();

    SstToken findTokenByIdSstEmpresa(Integer idSstEmp, Date d);

    SstToken findByToken(String cToken, Date d);

}
