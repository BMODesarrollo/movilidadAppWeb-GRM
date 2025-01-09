package com.movilidad.dto;

/**
 *
 * @author Jeisson Junco
 */
public class VehiculoLavadoStatusDTO {

    private int vehiculoId;
    private String codigo;
    private int lavados;
    private boolean estaLavado;

    public VehiculoLavadoStatusDTO(int vehiculoId, String codigo, int lavados, boolean estaLavado) {
        this.vehiculoId = vehiculoId;
        this.codigo = codigo;
        this.lavados = lavados;
        this.estaLavado = estaLavado;
    }

    public int getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(int vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getLavados() {
        return lavados;
    }

    public void setLavados(int lavados) {
        this.lavados = lavados;
    }

    public boolean isEstaLavado() {
        return estaLavado;
    }

    public void setEstaLavado(boolean estaLavado) {
        this.estaLavado = estaLavado;
    }

}
