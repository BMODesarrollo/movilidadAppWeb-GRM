/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.service.impl;

import com.movilidad.dao.OpcionDao;
import com.movilidad.model.Opcion;
import com.movilidad.service.OpcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("opcionService")
public class OpcionServiceImpl implements OpcionService {
    @Autowired
    private OpcionDao OpcionDao;

    public OpcionDao getOpcionDao() {
        return OpcionDao;
    }

    public void setOpcionDao(OpcionDao OpcionDao) {
        this.OpcionDao = OpcionDao;
    }
    
   
    @Override
    public Opcion findOpcion(Integer id) {
     return this.getOpcionDao().findOpcion(id);
    }
    
}
