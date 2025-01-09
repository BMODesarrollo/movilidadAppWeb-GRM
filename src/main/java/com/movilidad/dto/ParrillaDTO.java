/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 *
 * @author solucionesit
 */
public class ParrillaDTO {

    private Integer id;
    private Date fecha;
    private String codigo;
    private Integer estado;
    @Column(name = "tipo_vehiculo")
    private String tipologia;
    @Column(name = "tipo_novedad")
    private String tipoNovedad;
    private String ruta;
    @Column(name = "is_acc_asistido")
    private Integer isAccAsistido;
    @Column(name = "is_accidente")
    private Integer isAccidente;
    @Column(name = "is_uri")
    private Integer isUri;
    @Column(name = "is_varado")
    private Integer isVarado;
    @Column(name = "dias_inoperativo")
    private Integer diasInopetativos;
    @Column(name = "causa_falla")
    private String causaFalla;
    @Column(name = "nombre_empleado")
    private String nombreEmpleado;
    @Column(name = "fecha_hora_evento_accidente")
    private String fechaHoraEventoAccidente;
    @Column(name = "fecha_hora_evento_varado")
    private String fechaHoraEventoVarado;
    @Column(name = "fecha_hora_evento_uri")
    private String fechaHoraEventoUri;
    @Column(name = "id_acc_desplaza_a")
    private Integer idAccDesplazaA;
    @Column(name = "desplaza_a")
    private String desplazaA;
    @Column(name = "id_accidente")
    private Integer idAccidente;
    @Column(name = "codigo_tm")
    private String codigoTm;
    private String telefono;
    private Integer ubicacion;
    @Column(name = "is_mtto_patio")
    private Integer isMttoPatio;

    public ParrillaDTO(Integer id, Date fecha, String fechaHoraEventoAccidente,
            String fechaHoraEventoVarado, String fechaHoraEventoUri,
            Integer idAccDesplazaA, String desplazaA, Integer idAccidente,
            String codigo, Integer estado, String tipologia, String tipoNovedad,
            String ruta, Integer isAccAsistido, Integer isAccidente,
            Integer isUri, Integer isVarado, Integer diasInopetativos,
            String causaFalla, String nombreEmpleado, String codigoTm,
            String telefono, Integer ubicacion, Integer isMttoPatio) {
        this.id = id;
        this.fecha = fecha;
        this.fechaHoraEventoAccidente = fechaHoraEventoAccidente;
        this.fechaHoraEventoVarado = fechaHoraEventoVarado;
        this.fechaHoraEventoUri = fechaHoraEventoUri;
        this.idAccDesplazaA = idAccDesplazaA;
        this.desplazaA = desplazaA;
        this.idAccidente = idAccidente;
        this.codigo = codigo;
        this.estado = estado;
        this.tipologia = tipologia;
        this.tipoNovedad = tipoNovedad;
        this.ruta = ruta;
        this.isAccAsistido = isAccAsistido;
        this.isAccidente = isAccidente;
        this.isUri = isUri;
        this.isVarado = isVarado;
        this.diasInopetativos = diasInopetativos;
        this.causaFalla = causaFalla;
        this.nombreEmpleado = nombreEmpleado;
        this.codigoTm = codigoTm;
        this.telefono = telefono;
        this.ubicacion = ubicacion;
        this.isMttoPatio = isMttoPatio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getTipoNovedad() {
        return tipoNovedad;
    }

    public void setTipoNovedad(String tipoNovedad) {
        this.tipoNovedad = tipoNovedad;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Integer getIsAccAsistido() {
        return isAccAsistido;
    }

    public void setIsAccAsistido(Integer isAccAsistido) {
        this.isAccAsistido = isAccAsistido;
    }

    public Integer getIsAccidente() {
        return isAccidente;
    }

    public void setIsAccidente(Integer isAccidente) {
        this.isAccidente = isAccidente;
    }

    public Integer getIsVarado() {
        return isVarado;
    }

    public void setIsVarado(Integer isVarado) {
        this.isVarado = isVarado;
    }

    public Integer getDiasInopetativos() {
        return diasInopetativos;
    }

    public void setDiasInopetativos(Integer diasInopetativos) {
        this.diasInopetativos = diasInopetativos;
    }

    public String getCausaFalla() {
        return causaFalla;
    }

    public void setCausaFalla(String causaFalla) {
        this.causaFalla = causaFalla;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getCodigoTm() {
        return codigoTm;
    }

    public void setCodigoTm(String codigoTm) {
        this.codigoTm = codigoTm;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Integer ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getIsMttoPatio() {
        return isMttoPatio;
    }

    public void setIsMttoPatio(Integer isMttoPatio) {
        this.isMttoPatio = isMttoPatio;
    }

    public Integer getIdUri() {
        return isUri;
    }

    public void setIdUri(Integer idUri) {
        this.isUri = idUri;
    }

    public String getFechaHoraEventoAccidente() {
        return fechaHoraEventoAccidente;
    }

    public void setFechaHoraEventoAccidente(String fechaHoraEventoAccidente) {
        this.fechaHoraEventoAccidente = fechaHoraEventoAccidente;
    }

    public String getFechaHoraEventoVarado() {
        return fechaHoraEventoVarado;
    }

    public void setFechaHoraEventoVarado(String fechaHoraEventoVarado) {
        this.fechaHoraEventoVarado = fechaHoraEventoVarado;
    }

    public String getFechaHoraEventoUri() {
        return fechaHoraEventoUri;
    }

    public void setFechaHoraEventoUri(String fechaHoraEventoUri) {
        this.fechaHoraEventoUri = fechaHoraEventoUri;
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

    public Integer getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(Integer idAccidente) {
        this.idAccidente = idAccidente;
    }

}
