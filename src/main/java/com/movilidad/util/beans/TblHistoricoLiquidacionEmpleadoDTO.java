/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solucionesit
 */
@XmlRootElement
public class TblHistoricoLiquidacionEmpleadoDTO implements Serializable{

    private String desde;
    private String hasta;
    private String grupo;
    private BigDecimal diasLaborados;
    private BigDecimal diasNovedad;
    private BigDecimal puntosEmpresa;
    private BigDecimal puntosPmConciliados;
    private BigDecimal vrBonoSalarial;
    private BigDecimal vrBonoAlimentos;
    private BigDecimal puntosVrBonoSalarial;
    private BigDecimal diasVrBonoSalarial;
    private BigDecimal diasVrBonoAlimentos;
    private BigDecimal descuentoPuntos;
    private BigDecimal descuentoDiasSalarial;
    private BigDecimal descuentoDiasAlimentos;
    private BigDecimal subtotalBonoAlimento;
    private BigDecimal subtotalBonoSalarial;
    private BigDecimal totalBonoVehiculoTipo;
    private BigDecimal bonoAmplitud;
    private BigDecimal totalPagar;

    public TblHistoricoLiquidacionEmpleadoDTO() {
    }

    public TblHistoricoLiquidacionEmpleadoDTO(String desde, String hasta, String grupo, BigDecimal diasLaborados, BigDecimal diasNovedad, BigDecimal puntosEmpresa, BigDecimal puntosPmConciliados, BigDecimal vrBonoSalarial, BigDecimal vrBonoAlimentos, BigDecimal puntosVrBonoSalarial, BigDecimal diasVrBonoSalarial, BigDecimal diasVrBonoAlimentos, BigDecimal descuentoPuntos, BigDecimal descuentoDiasSalarial, BigDecimal descuentoDiasAlimentos, BigDecimal subtotalBonoAlimento, BigDecimal subtotalBonoSalarial, BigDecimal totalBonoVehiculoTipo, BigDecimal bonoAmplitud, BigDecimal totalPagar) {
        this.desde = desde;
        this.hasta = hasta;
        this.grupo = grupo;
        this.diasLaborados = diasLaborados;
        this.diasNovedad = diasNovedad;
        this.puntosEmpresa = puntosEmpresa;
        this.puntosPmConciliados = puntosPmConciliados;
        this.vrBonoSalarial = vrBonoSalarial;
        this.vrBonoAlimentos = vrBonoAlimentos;
        this.puntosVrBonoSalarial = puntosVrBonoSalarial;
        this.diasVrBonoSalarial = diasVrBonoSalarial;
        this.diasVrBonoAlimentos = diasVrBonoAlimentos;
        this.descuentoPuntos = descuentoPuntos;
        this.descuentoDiasSalarial = descuentoDiasSalarial;
        this.descuentoDiasAlimentos = descuentoDiasAlimentos;
        this.subtotalBonoAlimento = subtotalBonoAlimento;
        this.subtotalBonoSalarial = subtotalBonoSalarial;
        this.totalBonoVehiculoTipo = totalBonoVehiculoTipo;
        this.bonoAmplitud = bonoAmplitud;
        this.totalPagar = totalPagar;
    }
    

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public BigDecimal getDiasLaborados() {
        return diasLaborados;
    }

    public void setDiasLaborados(BigDecimal diasLaborados) {
        this.diasLaborados = diasLaborados;
    }

    public BigDecimal getDiasNovedad() {
        return diasNovedad;
    }

    public void setDiasNovedad(BigDecimal diasNovedad) {
        this.diasNovedad = diasNovedad;
    }

    public BigDecimal getPuntosEmpresa() {
        return puntosEmpresa;
    }

    public void setPuntosEmpresa(BigDecimal puntosEmpresa) {
        this.puntosEmpresa = puntosEmpresa;
    }

    public BigDecimal getPuntosPmConciliados() {
        return puntosPmConciliados;
    }

    public void setPuntosPmConciliados(BigDecimal puntosPmConciliados) {
        this.puntosPmConciliados = puntosPmConciliados;
    }

    public BigDecimal getVrBonoSalarial() {
        return vrBonoSalarial;
    }

    public void setVrBonoSalarial(BigDecimal vrBonoSalarial) {
        this.vrBonoSalarial = vrBonoSalarial;
    }

    public BigDecimal getVrBonoAlimentos() {
        return vrBonoAlimentos;
    }

    public void setVrBonoAlimentos(BigDecimal vrBonoAlimentos) {
        this.vrBonoAlimentos = vrBonoAlimentos;
    }

    public BigDecimal getPuntosVrBonoSalarial() {
        return puntosVrBonoSalarial;
    }

    public void setPuntosVrBonoSalarial(BigDecimal puntosVrBonoSalarial) {
        this.puntosVrBonoSalarial = puntosVrBonoSalarial;
    }

    public BigDecimal getDiasVrBonoSalarial() {
        return diasVrBonoSalarial;
    }

    public void setDiasVrBonoSalarial(BigDecimal diasVrBonoSalarial) {
        this.diasVrBonoSalarial = diasVrBonoSalarial;
    }

    public BigDecimal getDiasVrBonoAlimentos() {
        return diasVrBonoAlimentos;
    }

    public void setDiasVrBonoAlimentos(BigDecimal diasVrBonoAlimentos) {
        this.diasVrBonoAlimentos = diasVrBonoAlimentos;
    }

    public BigDecimal getDescuentoPuntos() {
        return descuentoPuntos;
    }

    public void setDescuentoPuntos(BigDecimal descuentoPuntos) {
        this.descuentoPuntos = descuentoPuntos;
    }

    public BigDecimal getDescuentoDiasSalarial() {
        return descuentoDiasSalarial;
    }

    public void setDescuentoDiasSalarial(BigDecimal descuentoDiasSalarial) {
        this.descuentoDiasSalarial = descuentoDiasSalarial;
    }

    public BigDecimal getDescuentoDiasAlimentos() {
        return descuentoDiasAlimentos;
    }

    public void setDescuentoDiasAlimentos(BigDecimal descuentoDiasAlimentos) {
        this.descuentoDiasAlimentos = descuentoDiasAlimentos;
    }

    public BigDecimal getSubtotalBonoAlimento() {
        return subtotalBonoAlimento;
    }

    public void setSubtotalBonoAlimento(BigDecimal subtotalBonoAlimento) {
        this.subtotalBonoAlimento = subtotalBonoAlimento;
    }

    public BigDecimal getSubtotalBonoSalarial() {
        return subtotalBonoSalarial;
    }

    public void setSubtotalBonoSalarial(BigDecimal subtotalBonoSalarial) {
        this.subtotalBonoSalarial = subtotalBonoSalarial;
    }

    public BigDecimal getTotalBonoVehiculoTipo() {
        return totalBonoVehiculoTipo;
    }

    public void setTotalBonoVehiculoTipo(BigDecimal totalBonoVehiculoTipo) {
        this.totalBonoVehiculoTipo = totalBonoVehiculoTipo;
    }

    public BigDecimal getBonoAmplitud() {
        return bonoAmplitud;
    }

    public void setBonoAmplitud(BigDecimal bonoAmplitud) {
        this.bonoAmplitud = bonoAmplitud;
    }

    public BigDecimal getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }
    
    
    

}
