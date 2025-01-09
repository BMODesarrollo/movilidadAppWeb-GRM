package com.movilidad.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "lavado_no")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LavadoNO.findAll", query = "SELECT l FROM LavadoNO l"),
    @NamedQuery(name = "LavadoNO.findByIdLavadoNO", query = "SELECT l FROM LavadoNO l WHERE l.idLavadoNO = :idLavadoNO"),
    @NamedQuery(name = "LavadoNO.findByFechaHora", query = "SELECT l FROM LavadoNO l WHERE l.fechaHora = :fechaHora"),
    @NamedQuery(name = "LavadoNO.findByObservacion", query = "SELECT l FROM LavadoNO l WHERE l.observacion = :observacion"),
    @NamedQuery(name = "LavadoNO.findByUsername", query = "SELECT l FROM LavadoNO l WHERE l.username = :username"),
    @NamedQuery(name = "LavadoNO.findByCreado", query = "SELECT l FROM LavadoNO l WHERE l.creado = :creado"),
    @NamedQuery(name = "LavadoNO.findByModificado", query = "SELECT l FROM LavadoNO l WHERE l.modificado = :modificado"),
    @NamedQuery(name = "LavadoNO.findByEstadoReg", query = "SELECT l FROM LavadoNO l WHERE l.estadoReg = :estadoReg")})

public class LavadoNO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_lavado_no")
    private Integer idLavadoNO;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Column(name = "id_lavado_motivo")
    private int idLavadoMotivo;
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    @ManyToOne(optional = false)
    private Vehiculo idVehiculo;
    @Size(max = 250)
    @Column (name = "observacion")
    private String observacion;
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

    public LavadoNO() {
        idLavadoMotivo = 0;
        idVehiculo = new Vehiculo();
        observacion = "";
    }

    public LavadoNO(Integer idLavadoNO) {
        this.idLavadoNO = idLavadoNO;
    }

    public Integer getIdLavadoNO() {
        return idLavadoNO;
    }

    public void setIdLavadoNO(Integer idLavadoNO) {
        this.idLavadoNO = idLavadoNO;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
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

    public int getIdLavadoMotivo() {
        return idLavadoMotivo;
    }

    public void setIdLavadoMotivo(int idLavadoMotivo) {
        this.idLavadoMotivo = idLavadoMotivo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Vehiculo getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Vehiculo idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLavadoNO != null ? idLavadoNO.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LavadoNO)) {
            return false;
        }
        LavadoNO other = (LavadoNO) object;
        if ((this.idLavadoNO == null && other.idLavadoNO != null) || (this.idLavadoNO != null && !this.idLavadoNO.equals(other.idLavadoNO))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.movilidad.model.LavadoNO[ idLavadoNO=" + idLavadoNO + " ]";
    }
    
}
