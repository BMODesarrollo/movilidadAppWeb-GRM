package com.movilidad.dto;

import javax.persistence.Column;

/**
 *
 * @author Carlos Ballestas
 */
public class AccDesplazaADTO {

    @Column(name = "id_acc_desplaza_a")
    private Integer idAccDesplazaA;

    @Column(name = "desplaza_a")
    private String desplazaA;

    public AccDesplazaADTO() {
    }

    public AccDesplazaADTO(Integer idAccDesplazaA, String nombre) {
        this.idAccDesplazaA = idAccDesplazaA;
        this.desplazaA = nombre;
    }

    public Integer getIdAccDesplazaA() {
        return idAccDesplazaA;
    }

    public void setIdAccDesplazaA(Integer idAccDesplazaA) {
        this.idAccDesplazaA = idAccDesplazaA;
    }

    public String getDesplazaA() {
        return desplazaA;
    }

    public void setDesplazaA(String desplazaA) {
        this.desplazaA = desplazaA;
    }

}
