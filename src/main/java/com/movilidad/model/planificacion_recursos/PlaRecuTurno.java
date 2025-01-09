package com.movilidad.model.planificacion_recursos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que permite la iteracción del sistema con la tabla pla_recu_motivo que corresponde 
 a 'PlaRecuTurno' del módulo 'Planificación de recursos'
 * @author Omar Beltrán
 */
@Entity
@Table(name = "pla_recu_turno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaRecuTurno.findAll", query = "SELECT i FROM PlaRecuTurno i"),
    @NamedQuery(name = "PlaRecuTurno.findByIdTurno", query = "SELECT i FROM PlaRecuTurno i WHERE i.idPlaRecuTurno = :idPlaRecuTurno"),
    @NamedQuery(name = "PlaRecuTurno.findByDescripcion", query = "SELECT i FROM PlaRecuTurno i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "PlaRecuTurno.findByTurno", query = "SELECT i FROM PlaRecuTurno i WHERE i.turno = :turno"),
    @NamedQuery(name = "PlaRecuTurno.findByCreado", query = "SELECT i FROM PlaRecuTurno i WHERE i.creado = :creado"),
    @NamedQuery(name = "PlaRecuTurno.findByModificado", query = "SELECT i FROM PlaRecuTurno i WHERE i.modificado = :modificado"),
    @NamedQuery(name = "PlaRecuTurno.findByEstadoReg", query = "SELECT i FROM PlaRecuTurno i WHERE i.estadoReg = :estadoReg")})

public class PlaRecuTurno implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pla_recu_turno")
    private Integer idPlaRecuTurno;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "turno")
    private String turno;
    @Size(max = 8)
    @Column(name = "hora_inicio")
    private String horaInicio;
    @Size(max = 8)
    @Column(name = "hora_fin")
    private String horaFin;
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 15)
    @Column(name = "username_create")
    private String usernameCreate;
    @Size(max = 15)
    @Column(name = "username_edit")
    private String usernameEdit;
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Column(name = "modificado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    @Column(name = "estado_reg")
    private Integer estadoReg;
    
    public PlaRecuTurno() {
    }

    public Integer getIdPlaRecuTurno() {
        return idPlaRecuTurno;
    }

    public void setIdPlaRecuTurno(Integer idPlaRecuTurno) {
        this.idPlaRecuTurno = idPlaRecuTurno;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getUsernameCreate() {
        return usernameCreate;
    }

    public void setUsernameCreate(String usernameCreate) {
        this.usernameCreate = usernameCreate;
    }

    public String getUsernameEdit() {
        return usernameEdit;
    }

    public void setUsernameEdit(String usernameEdit) {
        this.usernameEdit = usernameEdit;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlaRecuTurno != null ? idPlaRecuTurno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaRecuTurno)) {
            return false;
        }
        PlaRecuTurno other = (PlaRecuTurno) object;
        return !((this.idPlaRecuTurno == null && other.idPlaRecuTurno != null) || (this.idPlaRecuTurno != null && 
                !this.idPlaRecuTurno.equals(other.idPlaRecuTurno)));
    }

    @Override
    public String toString() {
        return "com.movilidad.model.planificacion_recursos.Turno[ idPlaRecuTurno=" + idPlaRecuTurno + " ]";
    }
    
}
