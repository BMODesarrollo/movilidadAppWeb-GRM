/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class ResumenAsignadosPorPatio implements Serializable {

    private String name;
    private long asignados;

    public ResumenAsignadosPorPatio() {
    }

    public ResumenAsignadosPorPatio(String name, long asignados) {
        this.name = name;
        this.asignados = asignados;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAsignados() {
        return asignados;
    }

    public void setAsignados(long asignados) {
        this.asignados = asignados;
    }
    
    
}
