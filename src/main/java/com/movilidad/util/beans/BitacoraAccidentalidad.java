/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Ballestas
 *
 * Bitacora Accidentalidad
 *
 */
@XmlRootElement
public class BitacoraAccidentalidad implements Serializable {

    @Column(name = "fecha")
    private String fecha;
    @Column(name = "hora")
    private String hora;
    @Column (name = "hora_asistida")
    private String horaAsistida;
    @Column (name = "hora_cierre_asistida")
    private String horaCierreAsistida;
    @Column (name = "hora_cierre_caso")
    private String horaCierreCaso;
    @Column(name = "ruta")
    private String ruta;
    @Column (name = "direccion")
    private String direccion;
    @Column(name = "placa")
    private String placa;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "codigo_operador")
    private String codigoOperador;
    @Column (name = "caso_tm")
    private String casoTM;
    @Column (name = "juridica")
    private String juridica;
    @Column(name = "nombre_operador")
    private String nombreOperador;
    @Column(name = "identificacion")
    private String identificacion;
    @Column(name = "tipo_evento")
    private String tipoEvento;
    @Column(name = "clasificacion")
    private String clasificacion;
    @Column(name = "causalidad")
    private String causalidad;
    @Column(name = "hipotesis")
    private String hipotesis;
    @Column(name = "estado_conciliacion")
    private String estadoConciliacion;
    @Column (name = "valor_conciliado")
    private Integer valorConciliado;
    @Column(name = "dia_evento")
    private String diaEvento;
    @Column(name = "franja_horaria")
    private String franjaHoraria;
    @Column(name = "tipo_vehiculo_tercero")
    private String tipoVehiculoTercero;
    @Column (name = "empresa_operadora")
    private String empresaOperadora;
    @Column(name = "inmovilizado")
    private Integer inmovilizado;
    @Column(name = "ipat")
    private Integer ipat;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "victimas")
    private Long victimas;
    @Column(name = "costos_directos")
    private BigDecimal costosDirectos;
    @Column(name = "costos_indirectos")
    private BigDecimal costosIndirectos;
    @Column(name = "puntos_afectacion")
    private Long puntosAfectacion;
        
    public BitacoraAccidentalidad() {}
    
    public BitacoraAccidentalidad(String fecha, String hora, String horaAsistida, String horaCierreAsistida, String horaCierreCaso, String ruta, String direccion, String placa, String codigo, 
                                  String codigoTm, String casoTM, String juridica, String nombre, String identificacion, String tipoEvento, String clasificacion, String causalidad, 
                                  String hipotesis, String estadoConciliacion, Integer valorConciliado, String diaEvento, String franjaHoraria, String tipoVehiculoTercero, 
                                  String empresaOperadora, Integer inmovilizado, Integer ipat, String observacion, Long victimas, BigDecimal costosDirectos, BigDecimal costosIndirectos, 
                                  Long puntosAfectacion) {
        this.fecha = fecha;
        this.hora = hora;
        this.ruta = ruta;
        this.placa = placa;
        this.codigo = codigo;
        this.codigoOperador = codigoTm;
        this.nombreOperador = nombre;
        this.identificacion = identificacion;
        this.tipoEvento = tipoEvento;
        this.clasificacion = clasificacion;
        this.causalidad = causalidad;
        this.hipotesis = hipotesis;
        this.estadoConciliacion = estadoConciliacion;
        this.diaEvento = diaEvento;
        this.franjaHoraria = franjaHoraria;
        this.tipoVehiculoTercero = tipoVehiculoTercero;
        this.inmovilizado = inmovilizado;
        this.ipat = ipat;
        this.observacion = observacion;
        this.victimas = victimas;
        this.costosDirectos = costosDirectos;
        this.costosIndirectos = costosIndirectos;
        this.puntosAfectacion = puntosAfectacion;
        this.direccion = direccion; 
        this.empresaOperadora = empresaOperadora;
        this.casoTM = casoTM;
        this.juridica = juridica;
        this.horaAsistida = horaAsistida;
        this.horaCierreAsistida = horaCierreAsistida;
        this.horaCierreCaso = horaCierreCaso;
        this.valorConciliado = valorConciliado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoOperador() {
        return codigoOperador;
    }

    public void setCodigoOperador(String codigoOperador) {
        this.codigoOperador = codigoOperador;
    }

    public String getNombreOperador() {
        return nombreOperador;
    }

    public void setNombreOperador(String nombreOperador) {
        this.nombreOperador = nombreOperador;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public String getCausalidad() {
        return causalidad;
    }

    public void setCausalidad(String causalidad) {
        this.causalidad = causalidad;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getEstadoConciliacion() {
        return estadoConciliacion;
    }

    public void setEstadoConciliacion(String estadoConciliacion) {
        this.estadoConciliacion = estadoConciliacion;
    }

    public String getDiaEvento() {
        return diaEvento;
    }

    public void setDiaEvento(String diaEvento) {
        this.diaEvento = diaEvento;
    }

    public String getFranjaHoraria() {
        return franjaHoraria;
    }

    public void setFranjaHoraria(String franjaHoraria) {
        this.franjaHoraria = franjaHoraria;
    }

    public String getTipoVehiculoTercero() {
        return tipoVehiculoTercero;
    }

    public void setTipoVehiculoTercero(String tipoVehiculoTercero) {
        this.tipoVehiculoTercero = tipoVehiculoTercero;
    }

    public Integer getInmovilizado() {
        return inmovilizado;
    }

    public void setInmovilizado(Integer inmovilizado) {
        this.inmovilizado = inmovilizado;
    }

    public Integer getIpat() {
        return ipat;
    }

    public void setIpat(Integer ipat) {
        this.ipat = ipat;
    }

    public String getHipotesis() {
        return hipotesis;
    }

    public void setHipotesis(String hipotesis) {
        this.hipotesis = hipotesis;
    }

    public Long getVictimas() {
        return victimas;
    }

    public void setVictimas(Long victimas) {
        this.victimas = victimas;
    }

    public BigDecimal getCostosDirectos() {
        return costosDirectos;
    }

    public void setCostosDirectos(BigDecimal costosDirectos) {
        this.costosDirectos = costosDirectos;
    }

    public BigDecimal getCostosIndirectos() {
        return costosIndirectos;
    }

    public void setCostosIndirectos(BigDecimal costosIndirectos) {
        this.costosIndirectos = costosIndirectos;
    }

    public Long getPuntosAfectacion() {
        return puntosAfectacion;
    }

    public void setPuntosAfectacion(Long puntosAfectacion) {
        this.puntosAfectacion = puntosAfectacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmpresaOperadora() {
        return empresaOperadora;
    }

    public void setEmpresaOperadora(String empresaOperadora) {
        this.empresaOperadora = empresaOperadora;
    }

    public String getCasoTM() {
        return casoTM;
    }

    public void setCasoTM(String casoTM) {
        this.casoTM = casoTM;
    }

    public String getJuridica() {
        return juridica;
    }

    public void setJuridica(String juridica) {
        this.juridica = juridica;
    }

    public String getHoraAsistida() {
        return horaAsistida;
    }

    public void setHoraAsistida(String horaAsistida) {
        this.horaAsistida = horaAsistida;
    }

    public String getHoraCierreAsistida() {
        return horaCierreAsistida;
    }

    public void setHoraCierreAsistida(String horaCierreAsistida) {
        this.horaCierreAsistida = horaCierreAsistida;
    }

    public String getHoraCierreCaso() {
        return horaCierreCaso;
    }

    public void setHoraCierreCaso(String horaCierreCaso) {
        this.horaCierreCaso = horaCierreCaso;
    }

    public Integer getValorConciliado() {
        return valorConciliado;
    }

    public void setValorConciliado(Integer valorConciliado) {
        this.valorConciliado = valorConciliado;
    }
    
}
