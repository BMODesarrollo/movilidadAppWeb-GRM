/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.dto;

import java.util.Date;
import javax.persistence.Column;

/**
 *
 * @author Julián Arévalo
 */
public class DocumentosPdDTO {

    @Column(name = "id_pd_maestro_seguimiento")
    private Integer idPdMaestroSeguimiento;
    @Column(name = "id_pd_maestro")
    private Integer idPdMaestro;
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "seguimiento")
    private String seguimiento;
    @Column(name = "path")
    private String path;
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    private Date creado;
    @Column(name = "modificado")
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    @Column(name = "documento_novedad")
    private String documentoNovedad;

    public DocumentosPdDTO(Integer idPdMaestroSeguimiento, Integer idPdMaestro, Date fecha, String seguimiento, String path, String username, Date creado, Date modificado, Integer estadoReg, String documentoNovedad) {
        this.idPdMaestroSeguimiento = idPdMaestroSeguimiento;
        this.idPdMaestro = idPdMaestro;
        this.fecha = fecha;
        this.seguimiento = seguimiento;
        this.path = path;
        this.username = username;
        this.creado = creado;
        this.modificado = modificado;
        this.estadoReg = estadoReg;
        this.documentoNovedad = documentoNovedad;
    }

    public Integer getIdPdMaestroSeguimiento() {
        return idPdMaestroSeguimiento;
    }

    public void setIdPdMaestroSeguimiento(Integer idPdMaestroSeguimiento) {
        this.idPdMaestroSeguimiento = idPdMaestroSeguimiento;
    }

    public Integer getIdPdMaestro() {
        return idPdMaestro;
    }

    public void setIdPdMaestro(Integer idPdMaestro) {
        this.idPdMaestro = idPdMaestro;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(String seguimiento) {
        this.seguimiento = seguimiento;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public Date getModificado() {
        return modificado;
    }

    public void setModificado(Date modificado) {
        this.modificado = modificado;
    }

    public Integer getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(Integer estadoReg) {
        this.estadoReg = estadoReg;
    }

    public String getDocumentoNovedad() {
        return documentoNovedad;
    }

    public void setDocumentoNovedad(String documentoNovedad) {
        this.documentoNovedad = documentoNovedad;
    }

}
