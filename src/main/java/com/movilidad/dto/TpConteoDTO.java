/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;

/**
 *
 * @author Julián Arévalo
 */
public class TpConteoDTO  implements Serializable {

	@Column(name = "id")
	private int id;
	@Column(name = "total")
	private int total;
	@Column(name = "confirmado")
	private int confirmado;
	@Column(name = "depot_type")
	private int depotType;
        @Column(name = "porcentaje")
	private BigDecimal porcentaje;

    public TpConteoDTO() {
    }
        
    public TpConteoDTO(int id, int total, int confirmado, int depotType, BigDecimal porcentaje) {
        this.id = id;
        this.total = total;
        this.confirmado = confirmado;
        this.depotType = depotType;
        this.porcentaje = porcentaje;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(int confirmado) {
        this.confirmado = confirmado;
    }

    public int getDepotType() {
        return depotType;
    }

    public void setDepotType(int depotType) {
        this.depotType = depotType;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }
    

}
