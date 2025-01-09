package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Omar Beltran
 */
@Entity
@Table(name = "lavado_no_conforme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LavadoNoConforme.findAll", query = "SELECT l FROM LavadoNoConforme l"),
    @NamedQuery(name = "LavadoNoConforme.findByIdLavadoNoConforme", query = "SELECT l FROM LavadoNoConforme l WHERE l.idLavadoNoConforme = :idLavadoNoConforme"),
    @NamedQuery(name = "LavadoNoConforme.findByObservacion", query = "SELECT l FROM LavadoNoConforme l WHERE l.observacion = :observacion"),
    @NamedQuery(name = "LavadoNoConforme.findByUsername", query = "SELECT l FROM LavadoNoConforme l WHERE l.username = :username"),
    @NamedQuery(name = "LavadoNoConforme.findByCreado", query = "SELECT l FROM LavadoNoConforme l WHERE l.creado = :creado"),
    @NamedQuery(name = "LavadoNoConforme.findByModificado", query = "SELECT l FROM LavadoNoConforme l WHERE l.modificado = :modificado"),
    @NamedQuery(name = "LavadoNoConforme.findByEstadoReg", query = "SELECT l FROM LavadoNoConforme l WHERE l.estadoReg = :estadoReg")})

public class LavadoNoConforme implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_lavado_no_conforme")
    private Integer idLavadoNoConforme;
    @Column(name = "id_vehiculo_multi")
    private String idVehiculoMulti;
    @Column(name = "id_actividad_multi")
    private String idActividadMulti;
    @Column(name = "id_responsable_multi")
    private String idResponsableMulti;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Size(max = 250)
    @Column (name = "observacion")
    private String observacion;
    @Column(name = "solucionado")
    private boolean solucionado;
    @Size(max = 15)
    @Column(name = "username")
    private String username;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;

    public LavadoNoConforme() {
    }

    public LavadoNoConforme(Integer idLavadoNoConforme) {
        this.idLavadoNoConforme = idLavadoNoConforme;
    }

    public Integer getIdLavadoNoConforme() {
        return idLavadoNoConforme;
    }

    public void setIdLavadoNoConforme(Integer idLavadoNoConforme) {
        this.idLavadoNoConforme = idLavadoNoConforme;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public boolean getSolucionado() {
        return solucionado;
    }

    public void setSolucionado(boolean solucionado) {
        this.solucionado = solucionado;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLavadoNoConforme != null ? idLavadoNoConforme.hashCode() : 0);
        return hash;
    }

    public String getIdVehiculoMulti() {
        return idVehiculoMulti;
    }

    public void setIdVehiculoMulti(String idVehiculoMulti) {
        this.idVehiculoMulti = idVehiculoMulti;
    }

    public String getIdActividadMulti() {
        return idActividadMulti;
    }

    public void setIdActividadMulti(String idActividadMulti) {
        this.idActividadMulti = idActividadMulti;
    }

    public String getIdResponsableMulti() {
        return idResponsableMulti;
    }

    public void setIdResponsableMulti(String idResponsableMulti) {
        this.idResponsableMulti = idResponsableMulti;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LavadoNoConforme)) {
            return false;
        }
        LavadoNoConforme other = (LavadoNoConforme) object;
        if ((this.idLavadoNoConforme == null && other.idLavadoNoConforme != null) || (this.idLavadoNoConforme != null && !this.idLavadoNoConforme.equals(other.idLavadoNoConforme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.LavadoNoConforme[ idLavadoNoConforme=" + idLavadoNoConforme + " ]";
    }
    
}

