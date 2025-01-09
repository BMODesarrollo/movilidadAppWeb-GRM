/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author alexander
 */
@ManagedBean
@SessionScoped
public class NavigationPage implements  Serializable{
    
    private String page="home";

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
    

    /**
     * Creates a new instance of NavigationPage
     */
    public NavigationPage() {
    }
}
