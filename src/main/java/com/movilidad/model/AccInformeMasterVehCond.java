/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "acc_informe_master_veh_cond")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccInformeMasterVehCond.findAll", query = "SELECT a FROM AccInformeMasterVehCond a")
    , @NamedQuery(name = "AccInformeMasterVehCond.findByIdAccInformeMasterVehCond", query = "SELECT a FROM AccInformeMasterVehCond a WHERE a.idAccInformeMasterVehCond = :idAccInformeMasterVehCond")
    , @NamedQuery(name = "AccInformeMasterVehCond.findByInmovilizado", query = "SELECT a FROM AccInformeMasterVehCond a WHERE a.inmovilizado = :inmovilizado")
    , @NamedQuery(name = "AccInformeMasterVehCond.findByPlaca", query = "SELECT a FROM AccInformeMasterVehCond a WHERE a.placa = :placa")
    , @NamedQuery(name = "AccInformeMasterVehCond.findByModelo", query = "SELECT a FROM AccInformeMasterVehCond a WHERE a.modelo = :modelo")
    , @NamedQuery(name = "AccInformeMasterVehCond.findByColor", query = "SELECT a FROM AccInformeMasterVehCond a WHERE a.color = :color")
    , @NamedQuery(name = "AccInformeMasterVehCond.findByCodigo", query = "SELECT a FROM AccInformeMasterVehCond a WHERE a.codigo = :codigo")
    , @NamedQuery(name = "AccInformeMasterVehCond.findByNombres", query = "SELECT a FROM AccInformeMasterVehCond a WHERE a.nombres = :nombres")
    , @NamedQuery(name = "AccInformeMasterVehCond.findByCodigoTm", query = "SELECT a FROM AccInformeMasterVehCond a WHERE a.codigoTm = :codigoTm")
    , @NamedQuery(name = "AccInformeMasterVehCond.findByIdentificacion", query = "SELECT a FROM AccInformeMasterVehCond a WHERE a.identificacion = :identificacion")
    , @NamedQuery(name = "AccInformeMasterVehCond.findByTelefono", query = "SELECT a FROM AccInformeMasterVehCond a WHERE a.telefono = :telefono")
    , @NamedQuery(name = "AccInformeMasterVehCond.findBySexo", query = "SELECT a FROM AccInformeMasterVehCond a WHERE a.sexo = :sexo")
    , @NamedQuery(name = "AccInformeMasterVehCond.findByCodigoHipotesis", query = "SELECT a FROM AccInformeMasterVehCond a WHERE a.codigoHipotesis = :codigoHipotesis")})
public class AccInformeMasterVehCond implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_acc_informe_master_veh_cond")
    private Integer idAccInformeMasterVehCond;
    @Column(name = "inmovilizado")
    private Integer inmovilizado;
    @Size(max = 45)
    @Column(name = "placa")
    private String placa;
    @Size(max = 45)
    @Column(name = "modelo")
    private String modelo;
    @Size(max = 45)
    @Column(name = "color")
    private String color;
    @Size(max = 45)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 45)
    @Column(name = "nombres")
    private String nombres;
    @Size(max = 15)
    @Column(name = "codigo_tm")
    private String codigoTm;
    @Size(max = 45)
    @Column(name = "identificacion")
    private String identificacion;
    @Size(max = 45)
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "sexo")
    private Character sexo;
    @Size(max = 45)
    @Column(name = "codigo_hipotesis")
    private String codigoHipotesis;
    @Lob
    @Size(max = 65535)
    @Column(name = "hipotesis")
    private String hipotesis;
    @JoinColumn(name = "id_acc_informe_master", referencedColumnName = "id_acc_informe_master")
    @ManyToOne(fetch = FetchType.LAZY)
    private AccInformeMaster idAccInformeMaster;

    public AccInformeMasterVehCond() {
    }

    public AccInformeMasterVehCond(Integer idAccInformeMasterVehCond) {
        this.idAccInformeMasterVehCond = idAccInformeMasterVehCond;
    }

    public Integer getIdAccInformeMasterVehCond() {
        return idAccInformeMasterVehCond;
    }

    public void setIdAccInformeMasterVehCond(Integer idAccInformeMasterVehCond) {
        this.idAccInformeMasterVehCond = idAccInformeMasterVehCond;
    }

    public Integer getInmovilizado() {
        return inmovilizado;
    }

    public void setInmovilizado(Integer inmovilizado) {
        this.inmovilizado = inmovilizado;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCodigoTm() {
        return codigoTm;
    }

    public void setCodigoTm(String codigoTm) {
        this.codigoTm = codigoTm;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public String getCodigoHipotesis() {
        return codigoHipotesis;
    }

    public void setCodigoHipotesis(String codigoHipotesis) {
        this.codigoHipotesis = codigoHipotesis;
    }

    public String getHipotesis() {
        return hipotesis;
    }

    public void setHipotesis(String hipotesis) {
        this.hipotesis = hipotesis;
    }

    public AccInformeMaster getIdAccInformeMaster() {
        return idAccInformeMaster;
    }

    public void setIdAccInformeMaster(AccInformeMaster idAccInformeMaster) {
        this.idAccInformeMaster = idAccInformeMaster;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccInformeMasterVehCond != null ? idAccInformeMasterVehCond.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccInformeMasterVehCond)) {
            return false;
        }
        AccInformeMasterVehCond other = (AccInformeMasterVehCond) object;
        if ((this.idAccInformeMasterVehCond == null && other.idAccInformeMasterVehCond != null) || (this.idAccInformeMasterVehCond != null && !this.idAccInformeMasterVehCond.equals(other.idAccInformeMasterVehCond))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.AccInformeMasterVehCond[ idAccInformeMasterVehCond=" + idAccInformeMasterVehCond + " ]";
    }
    
}
