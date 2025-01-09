/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.utils;

import java.io.Serializable;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author alexander
 */

public class UtilityDataSource implements Serializable{
    
    private transient javax.sql.DataSource dataSource;
    
   
    
    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    
    
}
